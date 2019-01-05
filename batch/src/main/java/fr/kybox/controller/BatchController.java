package fr.kybox.controller;

import fr.kybox.batch.UnreturnedScheduler;
import fr.kybox.batch.result.BatchResult;
import fr.kybox.entities.Email;
import fr.kybox.entities.LoginForm;
import fr.kybox.service.AuthService;
import fr.kybox.service.MailBuilder;
import fr.kybox.service.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static fr.kybox.utils.ValueTypes.UNAUTHORIZED_MSG;

@Controller
@SessionAttributes("loginForm")
@PropertySource("classpath:application.properties")
public class BatchController {

    private static Logger logger = LogManager.getLogger(BatchController.class);

    private final MailService mailService;
    private final AuthService authService;

    private final UnreturnedScheduler unreturnedScheduler;

    @Value("${file.directory}")
    private String fileDirectory;

    @Autowired
    public BatchController(MailService mailService, AuthService authService, UnreturnedScheduler unreturnedScheduler) {
        this.mailService = mailService;
        this.authService = authService;
        this.unreturnedScheduler = unreturnedScheduler;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/Unreturned")
    public ModelAndView unreturned(){
        ModelAndView modelAndView = new ModelAndView("unreturned");
        modelAndView.addObject("loginForm", new LoginForm());
        return modelAndView;
    }

    @PostMapping("/Unreturned")
    public ModelAndView unreturnedResult(@ModelAttribute("loginForm") LoginForm form)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        ModelAndView modelAndView = new ModelAndView("unreturned");

        if(!authService.login(form.getEmail(), form.getPassword())){
            modelAndView.addObject("error", UNAUTHORIZED_MSG);
            return modelAndView;
        }

        unreturnedScheduler.unreturnedScheduler();

        Map<String, Object> batchResult = new LinkedHashMap<>(BatchResult.getMap());
        modelAndView.addObject("batchResult", batchResult);
        BatchResult.clear();

        return modelAndView;
    }

    @GetMapping("/ReservationAlert")
    public String reservationAlert(){
        return "reservation_alert";
    }

    @PostMapping(path = "/ReservationAlert", consumes = "application/json; charset=UTF-8")
    public ResponseEntity ReservationAlert(@RequestBody String data) {

        if(data != null && !data.isEmpty()){

            JSONParser jsonParser = new JSONParser();

            try{
                JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

                logger.info("TOKEN = " + jsonObject.get("token"));
                // TODO Vérifier la validité du token

                JSONArray jsonArray = (JSONArray) jsonObject.get("email");

                if(jsonArray != null) {

                    Email email = new Email();

                    for (Object aJsonArray : jsonArray) {
                        JSONObject object = (JSONObject) aJsonArray;
                        if (object.containsKey("from")) email.setFrom((String) object.get("from"));
                        if (object.containsKey("to")) email.setTo((String) object.get("to"));
                        if (object.containsKey("subject")) email.setSubject((String) object.get("subject"));
                        if (object.containsKey("message")) email.setMessage((String) object.get("message"));
                    }

                    if(email.checkAll()){
                        MailBuilder mailBuilder = new MailBuilder();
                        mailBuilder.setEmail(email);
                        mailService.send(mailBuilder);

                        return new ResponseEntity(HttpStatus.OK);
                    }
                    else return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                else return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            catch (ParseException | MessagingException e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/uploadForm")
    public String uploadForm(){
        return "upload";
    }
}

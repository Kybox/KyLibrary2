package fr.kybox.controller;

import fr.kybox.entities.Email;
import fr.kybox.service.MailBuilder;
import fr.kybox.service.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;

@Controller
@PropertySource("classpath:application.properties")
public class AppController {

    private static Logger logger = LogManager.getLogger(AppController.class);

    @Autowired
    private MailService mailService;

    @Value("${file.directory}")
    private String fileDirectory;

    @GetMapping("/")
    public String index(){
        return "index";
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

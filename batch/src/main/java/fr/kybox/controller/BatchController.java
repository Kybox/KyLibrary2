package fr.kybox.controller;

import fr.kybox.batch.ReservationScheduler;
import fr.kybox.batch.UnreturnedScheduler;
import fr.kybox.batch.result.BatchResult;
import fr.kybox.controller.view.DefaultModelAndView;
import fr.kybox.entities.LoginForm;
import fr.kybox.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;

@Controller
@SessionAttributes("loginForm")
@PropertySource("classpath:application.properties")
public class BatchController {

    private static Logger logger = LogManager.getLogger(BatchController.class);

    private ModelAndView modelAndView;

    private final AuthService authService;
    private final UnreturnedScheduler batchUnreturned;
    private final ReservationScheduler batchReservation;

    @Autowired
    public BatchController(AuthService authService,
                           UnreturnedScheduler batchUnreturned,
                           ReservationScheduler batchReservation) {
        this.authService = authService;
        this.batchUnreturned = batchUnreturned;
        this.batchReservation = batchReservation;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/Unreturned/**")
    public String unreturnedRedirect(){
        return "redirect";
    }

    /**
     * Reach this url to login for trigger the {@link UnreturnedScheduler} batch
     * @return The <a href="./webapp/WEB-INF/views/batch.jsp">unreturned JSP</a> with the login form
     */
    @GetMapping("/Unreturned")
    public ModelAndView unreturned(){
        modelAndView = DefaultModelAndView.get();
        modelAndView.addObject(LOGIN_FORM, new LoginForm());
        modelAndView.addObject(JSP_TITLE, JSP_UNRETURNED_TITLE);
        return modelAndView;
    }

    /**
     * Reach this url to trigger the Unreturned scheduled batch
     * @return The unreturned JSP with the login form
     */
    @PostMapping("/Unreturned")
    public ModelAndView unreturnedResult(@ModelAttribute(LOGIN_FORM) LoginForm form)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        modelAndView = DefaultModelAndView.get();
        if(authService.connection(form.getEmail(), form.getPassword()) == UNAUTHORIZED){
            modelAndView.addObject(ERROR, UNAUTHORIZED_MSG);
            return modelAndView;
        }

        batchUnreturned.unreturnedScheduler();

        Map<String, Object> batchResult = new LinkedHashMap<>(BatchResult.getMap());
        modelAndView.addObject(BATCH_RESULT, batchResult);
        BatchResult.clear();

        return modelAndView;
    }

    /**
     * Reach this url to login for trigger the {@link ReservationScheduler} batch
     * @return The <a href="./webapp/WEB-INF/views/batch.jsp">batch JSP</a> with the login form
     */
    @GetMapping("/Reservation")
    public ModelAndView reservation(){
        modelAndView = DefaultModelAndView.get();
        modelAndView.addObject(LOGIN_FORM, new LoginForm());
        modelAndView.addObject(JSP_TITLE, JSP_RESERVATION_TITLE);
        return modelAndView;
    }

    @PostMapping("/Reservation")
    public ModelAndView reservationResult(@ModelAttribute(LOGIN_FORM) LoginForm form)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        modelAndView = DefaultModelAndView.get();
        if(authService.connection(form.getEmail(), form.getPassword()) == UNAUTHORIZED){
            modelAndView.addObject(ERROR, UNAUTHORIZED_MSG);
            return modelAndView;
        }

        batchReservation.reservationScheduler();

        Map<String, Object> batchResult = new LinkedHashMap<>(BatchResult.getMap());
        modelAndView.addObject(BATCH_RESULT, batchResult);
        BatchResult.clear();

        return modelAndView;
    }

    /**
     * Reach this url to login for trigger the {@link ReservationScheduler} batch
     * @return The <a href="./webapp/WEB-INF/views/batch.jsp">batch JSP</a> with the login form
     */
    @GetMapping("/Expiration")
    public ModelAndView expiration(){
        modelAndView = DefaultModelAndView.get();
        modelAndView.addObject(LOGIN_FORM, new LoginForm());
        modelAndView.addObject(JSP_TITLE, JSP_EXPIRATION_TITLE);
        return modelAndView;
    }
}

package fr.kybox.controller;

import fr.kybox.batch.ExpirationScheduler;
import fr.kybox.batch.ReservationScheduler;
import fr.kybox.batch.UnreturnedScheduler;
import fr.kybox.batch.result.BatchResult;
import fr.kybox.controller.view.BatchModelAndView;
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

    private final AuthService authService;
    private final UnreturnedScheduler batchUnreturned;
    private final ReservationScheduler batchReservation;
    private final ExpirationScheduler batchExpiration;

    @Autowired
    public BatchController(AuthService authService,
                           UnreturnedScheduler batchUnreturned,
                           ReservationScheduler batchReservation,
                           ExpirationScheduler batchExpiration) {

        this.authService = authService;
        this.batchUnreturned = batchUnreturned;
        this.batchReservation = batchReservation;
        this.batchExpiration = batchExpiration;
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
        return BatchModelAndView.get(UNRETURNED);
    }

    /**
     * Reach this url with POST method to trigger the {@link UnreturnedScheduler} batch
     * @return The unreturned JSP with the {@link BatchResult} otherwise an error message
     */
    @PostMapping("/Unreturned")
    public ModelAndView unreturnedResult(@ModelAttribute(LOGIN_FORM) LoginForm form)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        if(authService.connection(form.getEmail(), form.getPassword()) == UNAUTHORIZED)
            return BatchModelAndView.get(ERROR);

        batchUnreturned.unreturnedScheduler();

        Map<String, Object> batchResult = new LinkedHashMap<>(BatchResult.getMap());

        ModelAndView modelAndView = BatchModelAndView.get(DEFAULT);
        modelAndView.addObject(BATCH_RESULT, batchResult);
        modelAndView.addObject(JSP_RESULT_INFO, JSP_UNRETURNED_RESULT_INFO);
        BatchResult.clear();

        return modelAndView;
    }

    /**
     * Reach this url to login for trigger the {@link ReservationScheduler} batch
     * @return The <a href="./webapp/WEB-INF/views/batch.jsp">batch JSP</a> with the login form
     */
    @GetMapping("/Reservation")
    public ModelAndView reservation(){
        return BatchModelAndView.get(RESERVATION);
    }

    /**
     * Reach this url with POST method to trigger the {@link ReservationScheduler} batch
     * @return The unreturned JSP with the {@link BatchResult} otherwise an error message
     */
    @PostMapping("/Reservation")
    public ModelAndView reservationResult(@ModelAttribute(LOGIN_FORM) LoginForm form)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        if(authService.connection(form.getEmail(), form.getPassword()) == UNAUTHORIZED)
            return BatchModelAndView.get(ERROR);

        batchReservation.reservationScheduler();

        Map<String, Object> batchResult = new LinkedHashMap<>(BatchResult.getMap());

        ModelAndView modelAndView = BatchModelAndView.get(DEFAULT);
        modelAndView.addObject(BATCH_RESULT, batchResult);
        modelAndView.addObject(JSP_RESULT_INFO, JSP_RESERVATION_RESULT_INFO);
        BatchResult.clear();

        return modelAndView;
    }

    /**
     * Reach this url to login for trigger the {@link ReservationScheduler} batch
     * @return The <a href="./webapp/WEB-INF/views/batch.jsp">batch JSP</a> with the login form
     */
    @GetMapping("/Expiration")
    public ModelAndView expiration(){
        return BatchModelAndView.get(EXPIRATION);
    }

    @PostMapping("/Expiration")
    public ModelAndView expirationResult(@ModelAttribute(LOGIN_FORM) LoginForm form)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        if(authService.connection(form.getEmail(), form.getPassword()) == UNAUTHORIZED)
            return BatchModelAndView.get(ERROR);

        batchExpiration.expirationScheduler();

        Map<String, Object> batchResult = new LinkedHashMap<>(BatchResult.getMap());

        ModelAndView modelAndView = BatchModelAndView.get(DEFAULT);
        modelAndView.addObject(BATCH_RESULT, batchResult);
        modelAndView.addObject(JSP_RESULT_INFO, JSP_EXPIRATION_RESULT_INFO);
        BatchResult.clear();

        return modelAndView;
    }
}

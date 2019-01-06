package fr.kybox.controller.view;

import fr.kybox.entities.LoginForm;
import org.springframework.web.servlet.ModelAndView;

import static fr.kybox.utils.ValueTypes.*;

public class BatchModelAndView {

    public static ModelAndView get(String name){

        ModelAndView modelAndView = new ModelAndView(BATCH);

        if(name.equals(DEFAULT)) return modelAndView;

        if(name.equals(ERROR)){
            modelAndView.addObject(ERROR, UNAUTHORIZED_MSG);
            return modelAndView;
        }

        modelAndView.addObject(LOGIN_FORM, new LoginForm());

        switch (name){
            case UNRETURNED:
                modelAndView.addObject(JSP_TITLE, JSP_UNRETURNED_TITLE);
                modelAndView.addObject(JSP_MODAL_DESC, JSP_UNRETURNED__MODAL_DESC);
                break;
            case RESERVATION:
                modelAndView.addObject(JSP_TITLE, JSP_RESERVATION_TITLE);
                modelAndView.addObject(JSP_MODAL_DESC, JSP_RESERVATION_MODAL_DESC);
                break;
            case EXPIRATION :
                modelAndView.addObject(JSP_TITLE, JSP_EXPIRATION_TITLE);
                modelAndView.addObject(JSP_MODAL_DESC, JSP_EXPIRATION_MODAL_DESC);
                break;
        }

        return modelAndView;
    }
}

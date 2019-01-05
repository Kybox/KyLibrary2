package fr.kybox.controller.view;

import org.springframework.web.servlet.ModelAndView;

public class DefaultModelAndView {

    public static ModelAndView get(){
        return new ModelAndView("batch");
    }
}

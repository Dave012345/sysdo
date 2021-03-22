package com.sysdo.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * It implements the ErrorController interface, and replaces the default white label error page of spring.
 * redirects the error messages to the detailedError template.
 */

@Controller
public class ErrorPageController implements ErrorController {

    private static final String ERR_PATH = "/error";
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private ErrorAttributes errorAttributes;

    @Autowired
    public void setErrorAttributes(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }


    @RequestMapping(ERR_PATH)
    public String error(Model model, HttpServletRequest request) {
        RequestAttributes rA = new ServletRequestAttributes(request);
        Map<String,Object> error = this.errorAttributes.getErrorAttributes(rA, true);

        model.addAttribute("timestamp",error.get("timestamp"));
        model.addAttribute("error",error.get("error"));
        model.addAttribute("message",error.get("message"));
        model.addAttribute("path",error.get("path"));
        model.addAttribute("status",error.get("status"));

        log.error("USER INTERFACE ERROR | error: "+error.get("error")+" | status: "+error.get("status")+" | location: "+error.get("path")+" | message: "+error.get("message"));

        return "detailedError";
    }

    @Override
    public String getErrorPath() {
        return ERR_PATH;
    }

}

package com.sysdo.controller;

import com.sysdo.service.GlobalThrowableExcaption;
import com.sysdo.model.User;
import com.sysdo.service.EmailService;
import com.sysdo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegistrationController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }



    @RequestMapping("/registration")
    public String regist(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute User user, RedirectAttributes redirectAttributes){

        try{
            String whatsUp = userService.registration(user);
            if (whatsUp.compareTo("messages.registrationSuccess") == 0){
                emailService.sendMessage(user.getEmail(), user.getUsername(), user.getActivation());
                redirectAttributes.addAttribute("message", whatsUp);
            }
        } catch(GlobalThrowableExcaption e){
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
            return "redirect:/registration";
        }
        return "redirect:/";
    }


    @RequestMapping("/activation/{code}")
    public String activation(@PathVariable("code") String code, RedirectAttributes redirectAttributes){

        String result = userService.userActivation(code);
        redirectAttributes.addAttribute("message",result);

        return "redirect:/";
    }
}

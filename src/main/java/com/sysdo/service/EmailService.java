package com.sysdo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * when a new user registers, it sends a profile activation link to the email from the registration form
 */

@Service
public class EmailService {
    private final Log log = LogFactory.getLog(this.getClass());
    private JavaMailSender javaMailSender;
    private MessageSource messageSource;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Value("${spring.mail.username}")
    private String ownEmail;
    @Value("${email.link}")
    private String link;

    /**
     * It compiles the contents of the email with the external configuration of the i18n.
     * "messages.properties"
     *
     * @param key is a reference name of the value.
     * @return returns the value of the key in the appropriate language.
     */
    private String getPropertiesMessage(String key){
        return messageSource.getMessage(key,null, LocaleContextHolder.getLocale());
    }

    public void sendMessage(String email, String username, String activation){
        SimpleMailMessage mailMessage = null;
        try {
            mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(ownEmail);
            mailMessage.setTo(email);
            mailMessage.setSubject(getPropertiesMessage("email.subject"));
            mailMessage.setText(getPropertiesMessage("email.hi") + " " + username + "!\n\n" +
                    getPropertiesMessage("email.thanks") +"!\n" + getPropertiesMessage("email.click") +
                    "\n\n" + link + "/"+activation);

            javaMailSender.send(mailMessage);
            log.info("sent a message to this email: "+email);
        }catch (Exception e){
            log.error("failed to send message to this email: "+email+" | error: "+e.getMessage());
        }
    }
}


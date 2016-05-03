package ru.android_studio.mail;

import ru.android_studio.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.Locale;

@Controller
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping(method = RequestMethod.POST, value = "/callback")
    public void sendCallbackMail(@RequestParam(value = "name") String name, @RequestParam(value = "phoneNumber") String phoneNumber, Locale locale) throws MessagingException {
        mailService.sendCallbackMail(name, phoneNumber, locale);
    }
}
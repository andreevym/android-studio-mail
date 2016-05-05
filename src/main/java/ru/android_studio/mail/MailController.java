package ru.android_studio.mail;

import ru.android_studio.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping(method = RequestMethod.POST, value = "/callback")
    public void sendCallbackMail(HttpServletResponse httpServletResponse,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "phoneNumber") String phoneNumber,
                                 Locale locale) throws MessagingException, IOException {
        mailService.sendCallbackMail(name, phoneNumber, locale);
        httpServletResponse.sendRedirect("http://android-studio.ru/thanks.html");
    }
}
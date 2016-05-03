/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package ru.android_studio.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class MailService {

    private static final String BACKGROUND_IMAGE = "mails/images/background.png";
    private static final String THYMELEAF_LOGO_IMAGE = "mails/images/logo.png";
    private static final String PNG_MIME = "image/png";
    private static final String ANDREEVYM_GMAIL_COM = "andreevym@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private MessageSource messageSource;

    public void sendCallbackMail(
            final String name,
            final String phoneNumber, final Locale locale)
            throws MessagingException {
        final Context context = new Context(locale);
        context.setVariable("name", name);
        context.setVariable("phoneNumber", phoneNumber);
        context.setVariable("nowLocalDateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:MM:s")));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        boolean isMultipart = true;
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");

        message.setSubject(messageSource.getMessage("callback.subject", null, locale));
        message.setFrom(ANDREEVYM_GMAIL_COM);
        message.setTo(ANDREEVYM_GMAIL_COM);

        final String htmlContent = templateEngine.process("email-callback", context);
        boolean isHTML = true;
        message.setText(htmlContent, isHTML);

        message.addInline("background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("logo", new ClassPathResource(THYMELEAF_LOGO_IMAGE), PNG_MIME);

        javaMailSender.send(mimeMessage);

    }
}

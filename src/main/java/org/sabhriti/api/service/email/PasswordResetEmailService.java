package org.sabhriti.api.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserToken;
import org.sabhriti.api.exception.FailedSendingMailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetEmailService {
    private static final String SUBJECT = "[%s]: Instructions for changing your %s password.";
    private static final String FORGOT_PASSWORD_TEMPLATE_NAME = "mail/password-forget";

    private final TemplateEngine htmlTemplateEngine;

    private final JavaMailSender emailSender;

    @Value("${company.name}")
    private String company;

    @Value("${company.noReplyEmail}")
    private String noReplyEmail;

    public Mono<User> sendMail(User user, UserToken userToken, String newPassword) {
        try {
            final Context context = this.createMailContext(user, userToken, newPassword);
            this.emailSender.send(this.createMessage(user, context));
            return Mono.just(user);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return Mono.error(
                    new FailedSendingMailException(
                            "Failed sending password reset mail to provided email address."
                    )
            );
        }
    }

    private Context createMailContext(User user, UserToken userToken, String newPassword) throws UnknownHostException {
        final var context = new Context(Locale.ENGLISH);
        context.setVariable("name", user.getName());
        context.setVariable("email", user.getEmail());
        context.setVariable("company", this.company);
        context.setVariable("newPassword", newPassword);
        context.setVariable("url", this.createCreatePasswordUrl(userToken.getToken()));

        return context;
    }

    private String createCreatePasswordUrl(String token) throws UnknownHostException {
        return "http://%s:%s/?#/security/create-password/token=%s"
                .formatted(
                        InetAddress.getLocalHost().getHostAddress(),
                        8080,
                        token
                );
    }

    private SimpleMailMessage createMessage(User user, Context context) throws Exception {
        try {
            var message = new SimpleMailMessage();
            message.setFrom(this.noReplyEmail);
            message.setTo(user.getEmail());
            message.setSubject(SUBJECT.formatted(this.company, this.company));

            final var htmlContent = this.htmlTemplateEngine.process(FORGOT_PASSWORD_TEMPLATE_NAME, context);
            message.setText(htmlContent);

            return message;
        } catch (TemplateInputException exception) {
            throw new Exception(exception.getMessage());
        }
    }

}

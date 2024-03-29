package org.sabhriti.api.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserToken;
import org.sabhriti.api.dal.model.user.UserTokenUsage;
import org.sabhriti.api.exception.FailedSendingMailException;
import org.sabhriti.api.service.user.token.UserTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordCreationEmailService {

    private static final String CREATE_NEW_PASSWORD_TEMPLATE_NAME = "mail/new-password";

    private static final String WELCOME_TO_SABHRITI = "Welcome to sabhriti.";

    private final UserTokenService userTokenService;

    private final TemplateEngine htmlTemplateEngine;

    private final JavaMailSender emailSender;

    @Value("${company.emails.noReplyAddress}")
    private String from;

    @Value("${company.emails.callbackUrl}")
    private String callbackUrl;

    public Mono<Object> sendMail(User user, String rawPassword) {
        var now = LocalDateTime.now();
        var expiresOn = now.plusHours(24);

        return this
                .userTokenService
                .createFor(user, UserTokenUsage.CREATE_NEW_PASSWORD, expiresOn)
                .flatMap(userToken -> {
                    try {
                        final Context context = this.createMailContext(user, userToken, rawPassword);
                        this.emailSender.send(this.createMessage(user, context));
                        return Mono.just(user);
                    } catch (Exception exception) {
                        log.error(exception.getMessage());
                        return this.userTokenService
                                .deleteById(userToken.getId())
                                .thenReturn(Mono.error(new FailedSendingMailException()));
                    }
                });
    }

    private Context createMailContext(User user, UserToken userToken, String rawPassword) {
        final var context = new Context(Locale.ENGLISH);
        context.setVariable("name", user.getName());
        context.setVariable("temporary_password", rawPassword);
        context.setVariable("url", this.createCreatePasswordUrl(userToken.getToken()));

        return context;
    }

    private String createCreatePasswordUrl(String token) {
        return "%s/security/create-password/token=%s"
                .formatted(this.callbackUrl, token);
    }

    private SimpleMailMessage createMessage(User user, Context context) throws Exception {
        try {
            var message = new SimpleMailMessage();
            message.setFrom(this.from);
            message.setTo(user.getEmail());
            message.setSubject(WELCOME_TO_SABHRITI);

            final var htmlContent = this.htmlTemplateEngine.process(CREATE_NEW_PASSWORD_TEMPLATE_NAME, context);
            message.setText(htmlContent);

            return message;
        } catch (TemplateInputException exception) {
            throw new Exception(exception.getMessage());
        }
    }
}

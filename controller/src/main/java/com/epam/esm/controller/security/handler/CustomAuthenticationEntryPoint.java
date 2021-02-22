package com.epam.esm.controller.security.handler;

import com.epam.esm.controller.security.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Locale;

@ControllerAdvice
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //401
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        Locale locale = request.getLocale();
        if (locale == null) {
            locale = new Locale("en", "US");
        }

        // TODO: 2/20/21 nullpointer during getting message
        String message = messageSource.getMessage("jwt_no_key_given", null, locale);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         JwtAuthenticationException e) throws IOException, ServletException {
        Locale locale = request.getLocale();
        if (locale == null) {
            locale = new Locale("en", "US");
        }

        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                messageSource.getMessage("jwt_no_key_given", null, locale));
    }

    //403
    @ExceptionHandler(value = AccessDeniedException.class)
    public void commence(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN,
                messageSource.getMessage("jwt_invalid", null, request.getLocale()));
    }

    //500
    @ExceptionHandler(value = Exception.class)
    public void commence(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                messageSource.getMessage("jwt_authentication_error", null, request.getLocale()));
    }
}
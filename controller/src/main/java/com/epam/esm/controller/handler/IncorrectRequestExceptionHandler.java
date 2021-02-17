package com.epam.esm.controller.handler;

import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.controller.json.entity.JsonError;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandle;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

@RestControllerAdvice
public class IncorrectRequestExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    public IncorrectRequestExceptionHandler() {
        super();
    }
  
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request, Locale locale) {
        return handleAnyException( ex, messageSource.getMessage("exception_requestMethodNotSupported",null, locale), request);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception, WebRequest request, Locale locale) {
        return handleAnyException(exception, messageSource.getMessage("exception_mediaTypeNotSupported", null, locale)
                , request);
    }

    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_mediaTypeNotAcceptableException", null, locale),
                request);
    }

    @ExceptionHandler(value = MissingPathVariableException.class)
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_missingPathVariable", null, locale), request);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_missingServletRequestParameter", null, locale),
                request);
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_servletRequestBindingException", null, locale),
                request);
    }

    @ExceptionHandler(value = ConversionNotSupportedException.class)
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_conversionNotSupportedException", null, locale),
                request);
    }

    @ExceptionHandler(value = TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_typeMismatchException", null, locale), request);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_httpMessageNotReadableException", null, locale),
                request);
    }

    @ExceptionHandler(value = HttpMessageNotWritableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_httpMessageNotWritableException", null, locale),
                request);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_methodArgumentNotValidException", null, locale),
                request);
    }

    @ExceptionHandler(value = MissingServletRequestPartException.class)
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_missingServletRequestException", null, locale),
                request);
    }

    @ExceptionHandler(value = BindException.class)
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_bindException", null, locale), request);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request,
                                                                   Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_noHandlerFoundException", null, locale), request);
    }

    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        HttpHeaders headers, HttpStatus status,
                                                                        WebRequest webRequest, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_asyncRequestTimeoutException", null, locale),
                webRequest);
    }

    @ExceptionHandler(value = {ControllerException.class})
    protected ResponseEntity<Object> handleControllerException(Exception ex, WebRequest request, Locale locale) {
        return handleAnyException(ex, messageSource.getMessage("exception_any", null, locale), request);
    }

    private ResponseEntity<Object> handleAnyException(Exception exception, String message, WebRequest request) {
        JsonError error = new JsonError(HttpStatus.BAD_REQUEST, message + " : " + exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST.value());
    }
}

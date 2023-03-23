package com.daniel.AppTareas.excepciones;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
 public class TareaControllerAdvice extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {TareaNoValidaException.class})
    protected ResponseEntity<Object> handleConflict(TareaNoValidaException tareaNoValidaException, WebRequest request) {
        String bodyOfResponse = tareaNoValidaException.getMensaje();
        ErrorResponse errorResponse = new ErrorResponse(tareaNoValidaException.getMensaje(), tareaNoValidaException.getHttpStatus());
        return handleExceptionInternal(tareaNoValidaException, errorResponse, new HttpHeaders(), tareaNoValidaException.getHttpStatus(), request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {GrupoNoValidoException.class})
    protected ResponseEntity<Object> handleConflict(GrupoNoValidoException grupoNoValidoException, WebRequest request) {
        String bodyOfResponse = grupoNoValidoException.getMensaje();
        ErrorResponse errorResponse = new ErrorResponse(grupoNoValidoException.getMensaje(), grupoNoValidoException.getHttpStatus());
        return handleExceptionInternal(grupoNoValidoException, errorResponse, new HttpHeaders(), grupoNoValidoException.getHttpStatus(), request);
    }
}

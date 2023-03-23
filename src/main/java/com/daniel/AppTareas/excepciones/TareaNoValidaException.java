package com.daniel.AppTareas.excepciones;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TareaNoValidaException extends RuntimeException {
    private String mensaje;
    private HttpStatus httpStatus;
    public TareaNoValidaException(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.mensaje = mensaje;
        this.httpStatus = httpStatus;
    }
}

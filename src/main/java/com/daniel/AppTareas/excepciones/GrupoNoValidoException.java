package com.daniel.AppTareas.excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class GrupoNoValidoException extends RuntimeException {
    private String mensaje;
    private HttpStatus httpStatus;

    public GrupoNoValidoException(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.mensaje = mensaje;
        this.httpStatus = httpStatus;
    }
}

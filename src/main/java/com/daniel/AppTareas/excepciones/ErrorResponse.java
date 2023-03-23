package com.daniel.AppTareas.excepciones;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
public class ErrorResponse {

    private String mensaje;
    private HttpStatus error;
    private Integer estado;
    private String tiempo;

    public ErrorResponse(String mensaje, HttpStatus httpStatus) {
        this.mensaje = mensaje;
        this.estado = httpStatus.value();
        this.error = httpStatus;
        this.tiempo = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS z").format(Calendar.getInstance().getTime());
    }
}

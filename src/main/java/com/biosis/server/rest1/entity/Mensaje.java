package com.biosis.server.rest1.entity;

/**
 * Created by Francis on 17/12/2015.
 */
public class Mensaje {
    private final long id;
    private final String contenido;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje(long id, String contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    public long getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }
}

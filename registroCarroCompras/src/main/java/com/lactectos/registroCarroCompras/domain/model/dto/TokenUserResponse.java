package com.lactectos.registroCarroCompras.domain.model.dto;

public class TokenUserResponse {
    private String nombre;
    private String correo;
    private boolean isCreated;

    public TokenUserResponse() {
    }

    public TokenUserResponse(String nombre, String correo, boolean isCreated) {
        this.nombre = nombre;
        this.correo = correo;
        this.isCreated = isCreated;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean getIsCreated() {
        return isCreated;
    }

    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }
}
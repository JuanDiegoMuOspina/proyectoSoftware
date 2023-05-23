package com.lactectos.registroEmpleado.domain.model.dto;

public class TokenUserResponse {
    private String nombre;
    private String correo;
    private boolean isCreated;

    public TokenUserResponse() {
        //solo instancia el objeto nada mas
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
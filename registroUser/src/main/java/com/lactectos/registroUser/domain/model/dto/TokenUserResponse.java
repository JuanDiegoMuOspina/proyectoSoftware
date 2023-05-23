package com.lactectos.registroUser.domain.model.dto;

public class TokenUserResponse {
    private String nombre;
    private String correo;
    private boolean isCreated;

    public TokenUserResponse() {
        //este metodo solo permite iniciar el objeto, mas se  niega interaciones
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
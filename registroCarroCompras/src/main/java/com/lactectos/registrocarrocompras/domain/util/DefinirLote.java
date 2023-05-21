package com.lactectos.registrocarrocompras.domain.util;


import java.sql.Date;
import java.time.LocalDate;

public class DefinirLote {
    protected Date fechaActual = null;
    protected Date fechaVence = null;

    public DefinirLote() {
        LocalDate actual = LocalDate.now();
        LocalDate vence = actual.plusDays(10);

        this.fechaActual = Date.valueOf(actual);
        this.fechaVence = Date.valueOf(vence);
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public Date getFechaVence() {
        return fechaVence;
    }
}

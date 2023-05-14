package com.lactectos.registroEmpleado.domain.model.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
@PrimaryKeyJoinColumn(name = "id")  // This is used to join the primary key with the parent class primary key
public class Empleado extends Persona {

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @ManyToOne  // This assumes that many employees can have the same role
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    @JsonBackReference
    private Rol rol;


}
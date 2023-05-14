package com.lactectos.registroCarroCompras.domain.model.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "usuarios")
@PrimaryKeyJoinColumn(name = "id")
public class Usuario extends Persona {

    @Column(nullable = false, unique = true)
    private String codigo;//ojo, generar codigo unico

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private CarroCompras carroCompras;
}
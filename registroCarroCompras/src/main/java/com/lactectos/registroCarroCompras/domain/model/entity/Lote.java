package com.lactectos.registroCarroCompras.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_expedido")
    private Date fechaExpedido;

    @Column(name = "fecha_vencido")
    private Date fechaVencido;
    @JsonManagedReference
    @OneToOne(mappedBy = "lote")
    private Producto producto;
}
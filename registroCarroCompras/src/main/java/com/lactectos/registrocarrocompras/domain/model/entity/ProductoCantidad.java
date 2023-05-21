package com.lactectos.registrocarrocompras.domain.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "producto_cantidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCantidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "carro_compras_id", referencedColumnName = "id")
    @JsonIgnore
    private CarroCompras carroCompras;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "estado")
    private boolean estado;
}

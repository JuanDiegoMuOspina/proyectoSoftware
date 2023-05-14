package com.lactectos.registroCarroCompras.domain.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carro_compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarroCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "carroCompras", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductoCantidad> listaProductos;

    @Column(name = "total_pago")
    private double totalPago;
}

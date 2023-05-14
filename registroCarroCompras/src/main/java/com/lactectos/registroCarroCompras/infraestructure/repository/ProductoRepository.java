package com.lactectos.registroCarroCompras.infraestructure.repository;


import com.lactectos.registroCarroCompras.domain.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
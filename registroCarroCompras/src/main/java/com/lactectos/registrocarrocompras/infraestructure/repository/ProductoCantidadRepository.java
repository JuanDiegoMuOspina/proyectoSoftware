package com.lactectos.registrocarrocompras.infraestructure.repository;

import com.lactectos.registrocarrocompras.domain.model.entity.ProductoCantidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoCantidadRepository extends JpaRepository<ProductoCantidad, Long> {
    List<ProductoCantidad> findByCarroComprasId(Long carroComprasId);
}
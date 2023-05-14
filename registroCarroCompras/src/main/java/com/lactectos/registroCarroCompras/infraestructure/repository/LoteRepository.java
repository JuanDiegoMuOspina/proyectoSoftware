package com.lactectos.registroCarroCompras.infraestructure.repository;

import com.lactectos.registroCarroCompras.domain.model.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
package com.lactectos.registroCarroCompras.infraestructure.repository;

import com.lactectos.registroCarroCompras.domain.model.entity.CarroCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarroComprasRepository extends JpaRepository<CarroCompras, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
    Optional<CarroCompras> findByUsuarioId(Long usuarioId);
}
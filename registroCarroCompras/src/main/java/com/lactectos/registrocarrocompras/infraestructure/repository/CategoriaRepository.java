package com.lactectos.registrocarrocompras.infraestructure.repository;

import com.lactectos.registrocarrocompras.domain.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
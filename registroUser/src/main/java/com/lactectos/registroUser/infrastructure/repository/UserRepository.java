package com.lactectos.registroUser.infrastructure.repository;


import com.lactectos.registroUser.domain.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
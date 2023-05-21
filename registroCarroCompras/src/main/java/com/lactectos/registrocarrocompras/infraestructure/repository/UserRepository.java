package com.lactectos.registrocarrocompras.infraestructure.repository;



import com.lactectos.registrocarrocompras.domain.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
package com.lactectos.registrocarrocompras.infraestructure.repository;




import com.lactectos.registrocarrocompras.domain.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
    Rol findByNombreRol(String nombreRol);
}
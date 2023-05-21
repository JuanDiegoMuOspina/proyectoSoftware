package com.lactectos.registrocarrocompras.infraestructure.repository;




import com.lactectos.registrocarrocompras.domain.model.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployRepository extends JpaRepository<Empleado, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
package com.lactectos.registroEmpleado.domain.util;

import com.lactectos.registroEmpleado.domain.model.entity.Rol;
import com.lactectos.registroEmpleado.infraestructure.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(3)
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    protected RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        // Aquí es donde creas e insertas los roles
        Rol adminRol = new Rol();
        adminRol.setNombreRol("admin");
        Rol userRol = new Rol();
        userRol.setNombreRol("vendedor");
        Rol guestRol = new Rol();
        guestRol.setNombreRol("creador");
        Rol noRol=new Rol();
        noRol.setNombreRol("No defined");

        // Solo inserta los roles si no existen ya
        if(rolRepository.findByNombreRol(adminRol.getNombreRol()) == null) {
            rolRepository.save(adminRol);
        }
        if(rolRepository.findByNombreRol(userRol.getNombreRol()) == null) {
            rolRepository.save(userRol);
        }
        if(rolRepository.findByNombreRol(guestRol.getNombreRol()) == null) {
            rolRepository.save(guestRol);
        }
        if(rolRepository.findByNombreRol(noRol.getNombreRol()) == null) {
            rolRepository.save(noRol);
        }
    }
}
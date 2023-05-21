package com.lactectos.registrocarrocompras.domain.service;




import com.lactectos.registrocarrocompras.domain.model.entity.Empleado;
import com.lactectos.registrocarrocompras.infraestructure.repository.EmployRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployService {

    @Autowired
    private EmployRepository employRepository;

    public Empleado saveUser(Empleado empleado) {

        return employRepository.save(empleado);
    }

    public String deleteUser(long id)
    {

         employRepository.deleteById(id);
        return "Usuario Eliminado Correctamente";
    }
    public Empleado findByEmail(String email)
    {

         List<Empleado> user=employRepository.findAll();
        for (Empleado empleado : user) {
            if (empleado.getEmail().equals(email)){
                return empleado;
            }
        }
        return null;

    }
    public Optional<Empleado> obtenerUsuario(long id)
    {

        return employRepository.findById(id);
    }


}
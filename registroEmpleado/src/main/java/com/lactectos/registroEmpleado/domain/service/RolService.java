package com.lactectos.registroEmpleado.domain.service;
import com.lactectos.registroEmpleado.domain.model.entity.Rol;
import com.lactectos.registroEmpleado.infraestructure.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    @Autowired
    protected RolRepository rolRepository;

    public Rol findByNombreRol(String nombreRolAsignable){
        return rolRepository.findByNombreRol(nombreRolAsignable);
    }
    public List<Rol> findByAll(){
        List<Rol> listaDeRoles=rolRepository.findAll();
        return listaDeRoles;
    }

}

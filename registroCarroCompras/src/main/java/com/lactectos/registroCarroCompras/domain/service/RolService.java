package com.lactectos.registroCarroCompras.domain.service;

import com.lactectos.registroCarroCompras.domain.model.entity.Rol;
import com.lactectos.registroCarroCompras.infraestructure.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

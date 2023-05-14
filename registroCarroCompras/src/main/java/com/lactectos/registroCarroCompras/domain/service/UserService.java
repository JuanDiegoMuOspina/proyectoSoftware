package com.lactectos.registroCarroCompras.domain.service;



import com.lactectos.registroCarroCompras.domain.model.entity.Usuario;
import com.lactectos.registroCarroCompras.infraestructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Usuario saveUser(Usuario user) {

        return userRepository.save(user);
    }

    public String deleteUser(long id)
    {

         userRepository.deleteById(id);
        return "Usuario Eliminado Correctamente";
    }
    public Usuario findByEmail(String email)
    {

         List<Usuario> user=userRepository.findAll();
        for (Usuario users : user) {
            if (users.getEmail().equals(email)){
                return users;
            }
        }
        return null;

    }
    public Optional<Usuario> obtenerUsuario(long id)
    {

        return userRepository.findById(id);
    }


}
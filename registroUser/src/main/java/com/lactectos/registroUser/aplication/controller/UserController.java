package com.lactectos.registroUser.aplication.controller;


import com.lactectos.registroUser.domain.model.entity.Usuario;
import com.lactectos.registroUser.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.annotation.Order;

@Order(2)
@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
        Usuario user = userService.saveUser(usuario);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{email}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String email) {
        Usuario user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/editar/{email}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable String  email, @RequestBody Usuario usuario) {
        Usuario actualizarUsuario = userService.findByEmail(email);
        if (actualizarUsuario != null) {
            actualizarUsuario.setDireccion(usuario.getDireccion());
            actualizarUsuario.setTelefono(usuario.getTelefono());
            userService.saveUser(actualizarUsuario);
            return ResponseEntity.ok(actualizarUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
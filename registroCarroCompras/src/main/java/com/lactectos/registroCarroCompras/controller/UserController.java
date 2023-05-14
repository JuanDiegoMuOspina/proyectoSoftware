package com.lactectos.registroCarroCompras.controller;


import com.lactectos.registroCarroCompras.domain.model.entity.Empleado;
import com.lactectos.registroCarroCompras.domain.model.entity.Rol;
import com.lactectos.registroCarroCompras.domain.service.EmployService;
import com.lactectos.registroCarroCompras.domain.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Order(2)
@RestController
@RequestMapping("/api/empleado")
public class UserController {
    @Autowired
    private EmployService employService;
    @Autowired
    private RolService rolService;
    @PostMapping("/registrar")
    public ResponseEntity<Empleado> addUsuario(@RequestBody Empleado usuario) {
        Empleado user = employService.saveUser(usuario);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{email}")
    public ResponseEntity<Empleado> getUsuario(@PathVariable String email) {
        Empleado user = employService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/editar/{email}/{name}")//Solo para administrador cambiar codigo y Rol
    public ResponseEntity<Empleado> updateUsuario(@PathVariable String  email, @PathVariable String name, @RequestBody Empleado usuario) {
        Empleado actualizarUsuario = employService.findByEmail(email);
        //aca obtener el rol que se le va a asignar al empeleado
        Rol nuevoRol= rolService.findByNombreRol(name);
        if (actualizarUsuario != null) {
            actualizarUsuario.setCodigo(usuario.getCodigo());
            actualizarUsuario.setRol(nuevoRol);
            employService.saveUser(actualizarUsuario);
            return ResponseEntity.ok(actualizarUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.findByAll();
        return ResponseEntity.ok(roles);
    }


}
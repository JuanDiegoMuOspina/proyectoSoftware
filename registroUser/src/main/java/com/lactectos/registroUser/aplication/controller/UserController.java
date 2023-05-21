package com.lactectos.registroUser.aplication.controller;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.lactectos.registroUser.domain.model.dto.TokenRequestFront;
import com.lactectos.registroUser.domain.model.dto.TokenUserResponse;
import com.lactectos.registroUser.domain.model.entity.Usuario;
import com.lactectos.registroUser.domain.service.UserService;
import com.lactectos.registroUser.domain.util.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.annotation.Order;


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


    @Autowired
    public TokenVerifier tokenVerifier;

    @PostMapping("/login")
    public ResponseEntity<TokenUserResponse> authenticate(@RequestBody TokenRequestFront tokenRequest) throws Exception {
        String token = tokenRequest.getToken();//se comprueba que el token sea valido, acà front me envia el token para saber si puede autenticar.
        GoogleIdToken.Payload  dataUser=tokenVerifier.verify(token);
        Usuario user=userService.findByEmail(dataUser.getEmail());
        if (dataUser!=null && user!=null ){//aca le digo al front que usuario esta ok

            TokenUserResponse valido=new TokenUserResponse();
            valido.setCorreo(dataUser.getEmail());
            valido.setNombre(dataUser.getEmail());
            valido.setIsCreated(true);

            return ResponseEntity.status(HttpStatus.OK).body(valido);

        }else {//se contruye respuesta para saber si front-react debe registrar usuario con formulario

            TokenUserResponse valido=new TokenUserResponse();
            valido.setCorreo(dataUser.getEmail());
            valido.setNombre(dataUser.getEmail()+"token puede ser invalidado");
            valido.setIsCreated(false);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valido);
        }

    }

}
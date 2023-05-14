package com.lactectos.registroEmpleado.aplication.controller;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.lactectos.registroEmpleado.domain.model.dto.TokenRequestFront;
import com.lactectos.registroEmpleado.domain.model.dto.TokenUserResponse;
import com.lactectos.registroEmpleado.domain.model.entity.Empleado;
import com.lactectos.registroEmpleado.domain.service.EmployService;
import com.lactectos.registroEmpleado.domain.util.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Order(3)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    public TokenVerifier tokenVerifier;
    @Autowired
    public EmployService employService;
    @PostMapping("/login")
    public ResponseEntity<TokenUserResponse> authenticate(@RequestBody TokenRequestFront tokenRequest) throws Exception {
        String token = tokenRequest.getToken();//se comprueba que el token sea valido, acà front me envia el token para saber si puede autenticar.
        GoogleIdToken.Payload  dataUser=tokenVerifier.verify(token);
        Empleado user=employService.findByEmail(dataUser.getEmail());
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
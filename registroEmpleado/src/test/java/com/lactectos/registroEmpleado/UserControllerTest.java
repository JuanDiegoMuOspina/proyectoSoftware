package com.lactectos.registroEmpleado;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.lactectos.registroEmpleado.aplication.controller.AuthenticationController;
import com.lactectos.registroEmpleado.aplication.controller.UserController;
import com.lactectos.registroEmpleado.domain.model.dto.TokenRequestFront;
import com.lactectos.registroEmpleado.domain.model.dto.TokenUserResponse;
import com.lactectos.registroEmpleado.domain.model.entity.Empleado;
import com.lactectos.registroEmpleado.domain.model.entity.Rol;
import com.lactectos.registroEmpleado.domain.service.EmployService;
import com.lactectos.registroEmpleado.domain.service.RolService;
import com.lactectos.registroEmpleado.domain.util.TokenVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private EmployService employService;

    @Mock
    private RolService rolService;
    @Mock
    private TokenVerifier tokenVerifier;

    private String testEmail = "jdmunozo_1@uqvirtual.edu.co";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testAddUsuario() {
        Empleado testUser = new Empleado();
        when(employService.saveUser(testUser)).thenReturn(testUser);
        ResponseEntity<Empleado> responseEntity = userController.addUsuario(testUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testUser, responseEntity.getBody());

        verify(employService, times(1)).saveUser(testUser);
    }

    @Test
    void testGetUsuario() {
        String emailMock = "jdmunozo_1@uqvirtual.edu.co";
        Empleado user = new Empleado();
        when(employService.findByEmail(emailMock)).thenReturn(user);

        ResponseEntity<Empleado> responseEntity = userController.getUsuario(emailMock);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

        verify(employService, times(1)).findByEmail(emailMock);
    }

    @Test
    void testGetUsuarioNotFound() {
        when(employService.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Empleado> responseEntity = userController.getUsuario("notfound@example.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(employService, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    void testUpdateUsuario() {

        Empleado usuer = new Empleado();
        String nombreMockEmpleado="juan diego";
        Rol nuevoRol=new Rol();
        usuer.setRol(nuevoRol);


        when(rolService.findByNombreRol(nombreMockEmpleado)).thenReturn(nuevoRol);
        when(employService.findByEmail(testEmail)).thenReturn(usuer);
        when(employService.saveUser(any(Empleado.class))).thenReturn(usuer);

        ResponseEntity<Empleado> responseEntity = userController.updateUsuario(testEmail, nombreMockEmpleado,usuer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuer, responseEntity.getBody());

        verify(employService, times(1)).findByEmail(testEmail);
        verify(employService, times(1)).saveUser(any(Empleado.class));
    }

    @Test
    void testUpdateUsuarioNotFound() {

        Empleado newUser = new Empleado();
        String nombreMockEmpleado="juan diego";

        when(employService.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Empleado> responseEntity = userController.updateUsuario(testEmail,nombreMockEmpleado, newUser);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(employService, times(1)).findByEmail(testEmail);
    }
    @Test
    void testAuthenticateUserExists() throws Exception {

        String testToken = "tokeejemplo";


        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail(testEmail);

        Empleado user = new Empleado();

        when(tokenVerifier.verify(testToken)).thenReturn(payload);
        when(employService.findByEmail(testEmail)).thenReturn(user);

        TokenRequestFront tokenRequest = new TokenRequestFront();
        tokenRequest.setToken(testToken);
        ResponseEntity<TokenUserResponse> responseEntity = authenticationController.authenticate(tokenRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getIsCreated());
        assertEquals(testEmail, responseEntity.getBody().getCorreo());
    }

    @Test
    void testAuthenticateUserNotExists() throws Exception {

        String testToken = "tokeejemplo";


        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail(testEmail);

        when(tokenVerifier.verify(testToken)).thenReturn(payload);
        when(employService.findByEmail(anyString())).thenReturn(null);

        TokenRequestFront tokenRequest = new TokenRequestFront();
        tokenRequest.setToken(testToken);
        ResponseEntity<TokenUserResponse> responseEntity = authenticationController.authenticate(tokenRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getIsCreated());
        assertEquals(testEmail, responseEntity.getBody().getCorreo());
    }

}





package com.lactectos.registroUser;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.lactectos.registroUser.aplication.controller.UserController;
import com.lactectos.registroUser.domain.model.dto.TokenRequestFront;
import com.lactectos.registroUser.domain.model.dto.TokenUserResponse;
import com.lactectos.registroUser.domain.model.entity.Usuario;
import com.lactectos.registroUser.domain.service.UserService;
import com.lactectos.registroUser.domain.util.TokenVerifier;
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

    @Mock
    private UserService userService;
    @Mock
    private TokenVerifier tokenVerifier;

    private String testEmail = "jdmunozo_1@uqvirtual.edu.co";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testAddUsuario() {
        Usuario testUser = new Usuario();
        when(userService.saveUser(testUser)).thenReturn(testUser);
        ResponseEntity<Usuario> responseEntity = userController.addUsuario(testUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testUser, responseEntity.getBody());

        verify(userService, times(1)).saveUser(testUser);
    }

    @Test
    void testGetUsuario() {
        String emailMock = "jdmunozo_1@uqvirtual.edu.co";
        Usuario user = new Usuario();
        when(userService.findByEmail(emailMock)).thenReturn(user);

        ResponseEntity<Usuario> responseEntity = userController.getUsuario(emailMock);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

        verify(userService, times(1)).findByEmail(emailMock);
    }

    @Test
    void testGetUsuarioNotFound() {
        when(userService.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Usuario> responseEntity = userController.getUsuario("notfound@example.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(userService, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    void testUpdateUsuario() {

        Usuario usuer = new Usuario();


        when(userService.findByEmail(testEmail)).thenReturn(usuer);
        when(userService.saveUser(any(Usuario.class))).thenReturn(usuer);

        ResponseEntity<Usuario> responseEntity = userController.updateUsuario(testEmail, usuer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuer, responseEntity.getBody());

        verify(userService, times(1)).findByEmail(testEmail);
        verify(userService, times(1)).saveUser(any(Usuario.class));
    }

    @Test
    void testUpdateUsuarioNotFound() {

        Usuario newUser = new Usuario();

        when(userService.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Usuario> responseEntity = userController.updateUsuario(testEmail, newUser);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(userService, times(1)).findByEmail(testEmail);
    }
    @Test
    void testAuthenticateUserExists() throws Exception {

        String testToken = "tokeejemplo";


        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail(testEmail);

        Usuario user = new Usuario();

        when(tokenVerifier.verify(testToken)).thenReturn(payload);
        when(userService.findByEmail(testEmail)).thenReturn(user);

        TokenRequestFront tokenRequest = new TokenRequestFront();
        tokenRequest.setToken(testToken);
        ResponseEntity<TokenUserResponse> responseEntity = userController.authenticate(tokenRequest);

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
        when(userService.findByEmail(anyString())).thenReturn(null);

        TokenRequestFront tokenRequest = new TokenRequestFront();
        tokenRequest.setToken(testToken);
        ResponseEntity<TokenUserResponse> responseEntity = userController.authenticate(tokenRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getIsCreated());
        assertEquals(testEmail, responseEntity.getBody().getCorreo());
    }

}





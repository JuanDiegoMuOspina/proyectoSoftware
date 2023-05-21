package com.lactectos.registrocarrocompras;

import com.lactectos.registrocarrocompras.controller.CarroComprasController;
import com.lactectos.registrocarrocompras.domain.model.entity.CarroCompras;
import com.lactectos.registrocarrocompras.domain.model.entity.Producto;
import com.lactectos.registrocarrocompras.domain.model.entity.ProductoCantidad;
import com.lactectos.registrocarrocompras.domain.model.entity.Usuario;
import com.lactectos.registrocarrocompras.domain.service.CarroComprasService;
import com.lactectos.registrocarrocompras.domain.service.ProductoCantidadService;
import com.lactectos.registrocarrocompras.domain.service.ProductoService;
import com.lactectos.registrocarrocompras.domain.service.UserService;
import com.lactectos.registrocarrocompras.domain.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarroComprasControllerTest {
    @InjectMocks
    private CarroComprasController carroComprasController;
    @Mock
    private CarroComprasService carroComprasService;
    @Mock
    private UserService userService;
    @Mock
    private ProductoCantidadService productoCantidadService;
    @Mock
    private ProductoService productoService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObtenerCarroCompras_usuarioExistente() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        CarroCompras carroCompras = new CarroCompras();
        carroCompras.setId(usuarioId);
        when(userService.obtenerUsuario(usuarioId)).thenReturn(Optional.of(usuario));
        when(carroComprasService.obtenerCarroCompras(usuarioId)).thenReturn(Optional.of(carroCompras));


        // Act
        ResponseEntity<CarroCompras> respuesta = carroComprasController.obtenerCarroCompras(usuarioId);

        // Assert
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(carroCompras, respuesta.getBody());

        verify(carroComprasService, times(2)).obtenerCarroCompras(usuarioId);
        verify(userService, times(1)).obtenerUsuario(usuarioId);
        verify(userService, times(1)).saveUser(any(Usuario.class));
    }

    @Test
    public void testObtenerCarroCompras_usuarioNoExistente() {
        // Arrange
        Long usuarioId = 2L;

        // Act and Assert
        try {
            ResponseEntity<CarroCompras> respuesta = carroComprasController.obtenerCarroCompras(usuarioId);

        } catch (ResourceNotFoundException ex) {

            assertEquals("Usuario No existe", ex.getMessage());
        }

    }

    @Test
    public void testCrearProductoCantidad_productoExistente_carroExistente() {
        // Arrange
        Long idProducto = 1L;
        Long carroComprasId = 1L;
        Long productoCantidad = 5L;
        Producto producto = new Producto();
        CarroCompras carroCompras = new CarroCompras();

        when(productoService.obtenerProducto(idProducto)).thenReturn(producto);
        when(carroComprasService.obtenerCarroCompras(carroComprasId)).thenReturn(Optional.of(carroCompras));

        // Act
        ResponseEntity<ProductoCantidad> respuesta = carroComprasController.crearProductoCantidad(carroComprasId, idProducto, productoCantidad);

        // Assert
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(producto, respuesta.getBody().getProducto());
        assertEquals(productoCantidad, respuesta.getBody().getCantidad());
        assertEquals(carroCompras, respuesta.getBody().getCarroCompras());

        verify(productoService, times(1)).obtenerProducto(idProducto);
        verify(carroComprasService, times(1)).obtenerCarroCompras(carroComprasId);
        verify(productoCantidadService, times(1)).agregarProductoCantidad(any(ProductoCantidad.class));
    }

    @Test
    public void testCrearProductoCantidad_productoNoExistente() {
        // Arrange
        Long idProducto = 2L;
        Long carroComprasId = 1L;
        Long productoCantidad = 5L;

        when(productoService.obtenerProducto(idProducto)).thenReturn(null);

        // Act
        ResponseEntity<ProductoCantidad> respuesta = carroComprasController.crearProductoCantidad(carroComprasId, idProducto, productoCantidad);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());

        verify(productoService, times(1)).obtenerProducto(idProducto);
        verify(carroComprasService, times(0)).obtenerCarroCompras(anyLong());
        verify(productoCantidadService, times(0)).agregarProductoCantidad(any(ProductoCantidad.class));
    }
    @Test
    public void testEditarProductoCantidad() {
        // Arrange
        Long productoCantidadId = 1L;
        Long cantidad = 5L;
        ProductoCantidad productoCantidad = new ProductoCantidad();
        productoCantidad.setCantidad(3L);

        when(productoCantidadService.obtenerProductoCantidadPorId(productoCantidadId)).thenReturn(Optional.of(productoCantidad));

        // Act
        ResponseEntity<ProductoCantidad> response = carroComprasController.editarProductoCantidad(productoCantidadId, cantidad);

        // Assert
        assertEquals(cantidad, response.getBody().getCantidad());
        verify(productoCantidadService, times(1)).obtenerProductoCantidadPorId(productoCantidadId);
        verify(productoCantidadService, times(1)).editarProductoCantidad(productoCantidad);
    }

    @Test
    public void testEliminarProductoCantidad() {
        // Arrange
        Long productoCantidadId = 1L;
        doNothing().when(productoCantidadService).eliminarProductoCantidad(productoCantidadId);

        // Act
        ResponseEntity<Void> response = carroComprasController.eliminarProductoCantidad(productoCantidadId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(productoCantidadService, times(1)).eliminarProductoCantidad(productoCantidadId);
    }
    @Test
    public void testObtenerProductosCantidad_noContent() {
        // Arrange
        Long carroComprasId = 1L;
        List<ProductoCantidad> mockProductosCantidad = new ArrayList<>();

        when(productoCantidadService.consultarProductosCantidadPorCarroCompras(carroComprasId)).thenReturn(mockProductosCantidad);

        // Act
        ResponseEntity<List<ProductoCantidad>> response = carroComprasController.obtenerProductosCantidad(carroComprasId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());


    }

    @Test
    public void testObtenerProductosCantidad_content() {
        // Arrange
        Long carroComprasId = 1L;
        List<ProductoCantidad> mockProductosCantidad = new ArrayList<>();
        mockProductosCantidad.add(new ProductoCantidad());
        when(productoCantidadService.consultarProductosCantidadPorCarroCompras(carroComprasId)).thenReturn(mockProductosCantidad);

        // Act
        ResponseEntity<List<ProductoCantidad>> response = carroComprasController.obtenerProductosCantidad(carroComprasId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(productoCantidadService, times(1)).consultarProductosCantidadPorCarroCompras(carroComprasId);
    }


}


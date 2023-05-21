package com.lactectos.registrocarrocompras;

import com.lactectos.registrocarrocompras.controller.ProductoController;
import com.lactectos.registrocarrocompras.domain.model.entity.Categoria;
import com.lactectos.registrocarrocompras.domain.model.entity.Lote;
import com.lactectos.registrocarrocompras.domain.model.entity.Producto;
import com.lactectos.registrocarrocompras.domain.service.CategoriaService;
import com.lactectos.registrocarrocompras.domain.service.LoteService;
import com.lactectos.registrocarrocompras.domain.service.ProductoService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {
    @InjectMocks
    private ProductoController productoController;
    @Mock
    private ProductoService productoService;
    @Mock
    private CategoriaService categoriaService;
    @Mock
    private LoteService loteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testObtenerProductoExistente() {
        // Arrange
        Long id = 2L;
        Producto productoMock = new Producto();
        productoMock.setId(id);
        when(productoService.obtenerProducto(id)).thenReturn(productoMock);
        // Act
        ResponseEntity<Producto> response = productoController.obtenerProducto(id);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productoMock, response.getBody());
        verify(productoService, times(1)).obtenerProducto(id);
    }

    @Test
    public void testObtenerProductoNoExistente() {
        // Arrange
        Long id = 1L;
        when(productoService.obtenerProducto(id)).thenReturn(null);

        // Act
        ResponseEntity<Producto> response = productoController.obtenerProducto(id);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(productoService, times(1)).obtenerProducto(id);
    }

    @Test
    public void testObtenerProductosExistente() {
        Producto producto=new Producto();
        producto.setId(2l);
        Producto producto2=new Producto();
        producto.setId(3l);
        // Arrange
        List<Producto> productosMock = new ArrayList<>();
        productosMock.add(producto);
        productosMock.add(producto2);
        when(productoService.obtenerTodosLosProductos()).thenReturn(productosMock);

        // Act
        ResponseEntity<List<Producto>> response = productoController.obtenerProductos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productosMock, response.getBody());
        verify(productoService, times(1)).obtenerTodosLosProductos();
    }

    @Test
    public void testObtenerProductosNoExistente() {
        // Arrange
        List<Producto> productosMock = new ArrayList<>();
        when(productoService.obtenerTodosLosProductos()).thenReturn(productosMock);

        // Act
        ResponseEntity<List<Producto>> response = productoController.obtenerProductos();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(productoService, times(1)).obtenerTodosLosProductos();
    }

    @Test
    public void testGuardarProductoCategoriaExistente() {
        // Arrange
        Long id = 1L;
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(id);
        Producto productoMock = new Producto();
        productoMock.setId(id);
        productoMock.setCategoria(categoriaMock);

        Lote loteMock = new Lote();
        loteMock.setId(id);
        when(loteService.guardarLote(any(Lote.class))).thenReturn(loteMock);
        when(categoriaService.obtenerCategoria(id)).thenReturn(categoriaMock);
        when(productoService.guardarProducto(productoMock)).thenReturn(productoMock);

        // Act
        ResponseEntity<Producto> response = productoController.guardarProducto(productoMock, id);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productoMock, response.getBody());
        verify(categoriaService, times(1)).obtenerCategoria(id);
        verify(loteService, times(1)).guardarLote(any(Lote.class));
        verify(productoService, times(1)).guardarProducto(productoMock);
    }

    @Test
    public void testGuardarProductoCategoriaNoExistente() {
        // Arrange
        Long id = 1L;
        Producto productoMock = new Producto();

        when(categoriaService.obtenerCategoria(id)).thenReturn(null);

        // Act
        ResponseEntity<Producto> response = productoController.guardarProducto(productoMock, id);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(productoMock, response.getBody());
        verify(categoriaService, times(1)).obtenerCategoria(id);

    }
    @Test
    public void testEliminarProducto() {
        // Arrange
        Long id = 1L;

        // Act
        productoController.eliminarProducto(id);

        // Assert
        verify(productoService, times(1)).eliminarProducto(id);
    }


    @Test
    public void testObtenerCategoriasExistente() {
        // Arrange
        List<Categoria> categoriasMock = new ArrayList<>();
        Categoria categoriaMock=new Categoria();
        categoriaMock.setId(1L);
        Categoria categoriaMock2=new Categoria();
        categoriaMock2.setId(2L);
        categoriasMock.add(categoriaMock2);
        categoriasMock.add(categoriaMock);
        when(categoriaService.obtenerTodasLasCategorias()).thenReturn(categoriasMock);

        // Act
        ResponseEntity<List<Categoria>> response = productoController.obtenerCategorias();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoriasMock, response.getBody());
        verify(categoriaService, times(1)).obtenerTodasLasCategorias();
    }

    @Test
    public void testObtenerCategoriasNoExistente() {
        // Arrange
        List<Categoria> categoriasMock = new ArrayList<>();
        when(categoriaService.obtenerTodasLasCategorias()).thenReturn(categoriasMock);

        // Act
        ResponseEntity<List<Categoria>> response = productoController.obtenerCategorias();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(categoriaService, times(1)).obtenerTodasLasCategorias();
    }

    @Test
    public void testGuardarCategoria_exitoso() {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setCategoria("Lacteos");

        when(categoriaService.guardarCategoria(categoria)).thenReturn(categoria);

        // Act
        ResponseEntity<Categoria> respuesta = productoController.guardarCategoria(categoria);

        // Assert
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(categoria, respuesta.getBody());
    }

    @Test
    public void testGuardarCategoria_fallido() {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setCategoria("Carnes");

        when(categoriaService.guardarCategoria(categoria)).thenReturn(null);

        // Act
        ResponseEntity<Categoria> respuesta = productoController.guardarCategoria(categoria);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
    }
}


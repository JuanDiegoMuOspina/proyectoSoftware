package com.lactectos.registrocarrocompras;

import com.lactectos.registrocarrocompras.domain.model.entity.Producto;
import com.lactectos.registrocarrocompras.domain.service.ProductoService;
import com.lactectos.registrocarrocompras.infraestructure.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void guardarProductoTest() {
        // Arrange
        Producto producto = new Producto();
        when(productoRepository.save(producto)).thenReturn(producto);

        // Act
        Producto resultado = productoService.guardarProducto(producto);

        // Assert
        assertEquals(producto, resultado);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void obtenerProductoExistenteTest() {
        // Arrange
        Long id = 1L;
        Producto producto = new Producto();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        // Act
        Producto resultado = productoService.obtenerProducto(id);

        // Assert
        assertEquals(producto, resultado);
        verify(productoRepository, times(1)).findById(id);
    }

    @Test
    public void obtenerProductoNoExistenteTest() {
        // Arrange
        Long id = 1L;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Producto resultado = productoService.obtenerProducto(id);

        // Assert
        assertNull(resultado);
        verify(productoRepository, times(1)).findById(id);
    }

    @Test
    public void eliminarProductoTest() {
        // Arrange
        Long id = 1L;

        // Act
        productoService.eliminarProducto(id);

        // Assert
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    public void obtenerTodosLosProductosTest() {
        // Arrange
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto());
        productos.add(new Producto());
        when(productoRepository.findAll()).thenReturn(productos);

        // Act
        List<Producto> resultado = productoService.obtenerTodosLosProductos();

        // Assert
        assertEquals(productos, resultado);
        verify(productoRepository, times(1)).findAll();
    }
}

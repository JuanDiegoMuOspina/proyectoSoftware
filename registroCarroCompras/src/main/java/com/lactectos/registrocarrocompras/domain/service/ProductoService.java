package com.lactectos.registrocarrocompras.domain.service;

import com.lactectos.registrocarrocompras.domain.model.entity.Producto;
import com.lactectos.registrocarrocompras.infraestructure.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
}
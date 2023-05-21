package com.lactectos.registrocarrocompras.domain.service;

import com.lactectos.registrocarrocompras.domain.model.entity.ProductoCantidad;
import com.lactectos.registrocarrocompras.infraestructure.repository.ProductoCantidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoCantidadService {
    private final ProductoCantidadRepository productoCantidadRepository;

    @Autowired
    public ProductoCantidadService(ProductoCantidadRepository productoCantidadRepository) {
        this.productoCantidadRepository = productoCantidadRepository;
    }

    public ProductoCantidad agregarProductoCantidad(ProductoCantidad productoCantidad) {
        return productoCantidadRepository.save(productoCantidad);
    }

    public ProductoCantidad editarProductoCantidad(ProductoCantidad productoCantidad) {
        return productoCantidadRepository.save(productoCantidad);
    }

    public void eliminarProductoCantidad(Long id) {
        productoCantidadRepository.deleteById(id);
    }

    public List<ProductoCantidad> consultarProductosCantidadPorCarroCompras(Long carroComprasId) {
        return productoCantidadRepository.findByCarroComprasId(carroComprasId);
    }

    public Optional<ProductoCantidad> obtenerProductoCantidadPorId(Long id) {
        return productoCantidadRepository.findById(id);
    }
}
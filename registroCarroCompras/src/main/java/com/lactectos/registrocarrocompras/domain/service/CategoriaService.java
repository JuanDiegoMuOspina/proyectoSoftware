package com.lactectos.registrocarrocompras.domain.service;
import com.lactectos.registrocarrocompras.domain.model.entity.Categoria;
import com.lactectos.registrocarrocompras.infraestructure.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria obtenerCategoria(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null && !categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene productos asociados.");
        }
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }
}
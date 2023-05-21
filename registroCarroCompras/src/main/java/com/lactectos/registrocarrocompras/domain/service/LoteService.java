package com.lactectos.registrocarrocompras.domain.service;

import com.lactectos.registrocarrocompras.domain.model.entity.Lote;
import com.lactectos.registrocarrocompras.infraestructure.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    public Lote guardarLote(Lote lote) {
        return loteRepository.save(lote);
    }

    public Lote obtenerLote(Long id) {
        return loteRepository.findById(id).orElse(null);
    }

    public void eliminarLote(Long id) {
        Lote lote = loteRepository.findById(id).orElse(null);
        if (lote != null && lote.getProducto() != null) {
            throw new RuntimeException("No se puede eliminar el lote porque está asociado a un producto.");
        }
        loteRepository.deleteById(id);
    }
}

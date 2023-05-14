package com.lactectos.registroCarroCompras.domain.service;

import com.lactectos.registroCarroCompras.domain.model.entity.CarroCompras;
import com.lactectos.registroCarroCompras.infraestructure.repository.CarroComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarroComprasService {
    @Autowired
    protected  CarroComprasRepository carroComprasRepository;
    public CarroCompras agregarCarroCompras(CarroCompras carroCompras) {
        return carroComprasRepository.save(carroCompras);
    }
    public Optional<CarroCompras> obtenerCarroCompras(Long id) {
        return  carroComprasRepository.findByUsuarioId(id);
    }
}

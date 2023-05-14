package com.lactectos.registroCarroCompras.controller;

import com.lactectos.registroCarroCompras.domain.model.entity.CarroCompras;
import com.lactectos.registroCarroCompras.domain.model.entity.Producto;
import com.lactectos.registroCarroCompras.domain.model.entity.ProductoCantidad;
import com.lactectos.registroCarroCompras.domain.model.entity.Usuario;
import com.lactectos.registroCarroCompras.domain.service.CarroComprasService;
import com.lactectos.registroCarroCompras.domain.service.ProductoCantidadService;
import com.lactectos.registroCarroCompras.domain.service.ProductoService;
import com.lactectos.registroCarroCompras.domain.service.UserService;
import com.lactectos.registroCarroCompras.domain.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carroCompras")
public class CarroComprasController {

    @Autowired
    private CarroComprasService carroComprasService;

    @Autowired
    private ProductoCantidadService productoCantidadService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarroCompras> obtenerCarroCompras(@PathVariable Long usuarioId) {
        CarroCompras carro=carroComprasService.obtenerCarroCompras(usuarioId).orElse(new CarroCompras());

        Usuario usuario=userService.obtenerUsuario(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario No existe"));
        carro.setUsuario(usuario);
        carroComprasService.agregarCarroCompras(carro);

        usuario.setCarroCompras(carro);
        userService.saveUser(usuario);
        return carroComprasService.obtenerCarroCompras(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{carroComprasId}/{idProducto}/{productoCantidad}")
    public ResponseEntity<ProductoCantidad> crearProductoCantidad(@PathVariable Long carroComprasId, @PathVariable Long idProducto,@PathVariable Long productoCantidad) {
        ProductoCantidad nuevoProductoCantidad = new ProductoCantidad();
        Producto  producto=productoService.obtenerProducto(idProducto);
        if (producto!=null){
            nuevoProductoCantidad.setProducto(producto);
            nuevoProductoCantidad.setCantidad(productoCantidad);
            nuevoProductoCantidad.setEstado(false);

        }else{
            return ResponseEntity.notFound().build();
        }
        CarroCompras carro=carroComprasService.obtenerCarroCompras(carroComprasId).orElseThrow(() -> new ResourceNotFoundException("No existe el carro de compras"));
        nuevoProductoCantidad.setCarroCompras(carro);
        productoCantidadService.agregarProductoCantidad(nuevoProductoCantidad);
        return ResponseEntity.ok(nuevoProductoCantidad);
    }

    @PutMapping("/{productoCantidadId}/{cantidad}")
    public ResponseEntity<ProductoCantidad> editarProductoCantidad(@PathVariable Long productoCantidadId, @PathVariable Long cantidad) {
        ProductoCantidad productoCantidadExistente = productoCantidadService.obtenerProductoCantidadPorId(productoCantidadId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductoCantidad no encontrado"));
        productoCantidadExistente.setCantidad(cantidad);
        productoCantidadService.editarProductoCantidad(productoCantidadExistente);
        return ResponseEntity.ok(productoCantidadExistente);
    }

    @DeleteMapping("/productoCantidad/{productoCantidadId}")
    public ResponseEntity<Void> eliminarProductoCantidad(@PathVariable Long productoCantidadId) {
        productoCantidadService.eliminarProductoCantidad(productoCantidadId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{carroComprasId}/productoCantidad")
    public ResponseEntity<List<ProductoCantidad>> obtenerProductosCantidad(@PathVariable Long carroComprasId) {
        List<ProductoCantidad> productosCantidad = productoCantidadService.consultarProductosCantidadPorCarroCompras(carroComprasId);
        if (productosCantidad.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosCantidad);
    }
}

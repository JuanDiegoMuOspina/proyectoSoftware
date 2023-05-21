package com.lactectos.registrocarrocompras.controller;

import com.lactectos.registrocarrocompras.domain.model.entity.Categoria;
import com.lactectos.registrocarrocompras.domain.model.entity.Lote;
import com.lactectos.registrocarrocompras.domain.model.entity.Producto;
import com.lactectos.registrocarrocompras.domain.service.CategoriaService;
import com.lactectos.registrocarrocompras.domain.service.LoteService;
import com.lactectos.registrocarrocompras.domain.service.ProductoService;
import com.lactectos.registrocarrocompras.domain.util.DefinirLote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private LoteService loteService;

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProducto(id);
        if (producto == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }


    @PostMapping("/{id}")
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto, @PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerCategoria(id);
        //definiciòn de manejo de lotes.
        DefinirLote loteFechas=new DefinirLote();
        Lote lote=new Lote();
        lote.setFechaExpedido(loteFechas.getFechaActual());
        lote.setFechaVencido(loteFechas.getFechaVence());
        loteService.guardarLote(lote);
        //fin de definicòn del lote

        if (categoria == null) {
            return new ResponseEntity<>(producto, HttpStatus.BAD_REQUEST);
        }else {
            producto.setCategoria(categoria);
            producto.setLote(lote);
            Producto productoGuardado = productoService.guardarProducto(producto);
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        }
    }


    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }


    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }



    @PostMapping("/categorias")
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoria);
        if (categoriaGuardada == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(categoriaGuardada, HttpStatus.CREATED);
    }

}

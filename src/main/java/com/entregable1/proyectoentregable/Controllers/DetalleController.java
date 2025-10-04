package com.entregable1.proyectoentregable.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entregable1.proyectoentregable.Models.DAO.IDetalledao;
import com.entregable1.proyectoentregable.Models.DAO.IEncabezadodao;
import com.entregable1.proyectoentregable.Models.DAO.IProductodao;
import com.entregable1.proyectoentregable.Models.Entity.Detalle;
import com.entregable1.proyectoentregable.Models.Entity.Encabezado;
import com.entregable1.proyectoentregable.Models.Entity.Producto;

@Controller
@RequestMapping("/Detalle")
public class DetalleController {

    @Autowired
    private IDetalledao detalleDao;

    @Autowired
    private IEncabezadodao encabezadoDao;

    @Autowired
    private IProductodao productoDao;

    @PostMapping("/crear")
    public String crearDetalle(@ModelAttribute Detalle detalle, RedirectAttributes redirectAttrs) {
    Encabezado encabezado = encabezadoDao.findById(detalle.getIdEncabezado());

    if (encabezado == null) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: El encabezado no existe");
        return "redirect:/Encabezado/Factura";
    }

    Producto producto = productoDao.findById(detalle.getIdProducto());

    if (producto == null) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: Producto no encontrado");
        return "redirect:/Encabezado/Factura/" + encabezado.getId();
    }

    if (producto.getStock() < detalle.getCantidad()) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: No hay stock suficiente del producto " + producto.getNombre());
        return "redirect:/Encabezado/Factura/" + encabezado.getId();
    }

    if (producto.getValorUnitario() < detalle.getDescuento()) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: No se puede aplicar un descuento mayor al precio unitario de " + producto.getNombre());
        return "redirect:/Encabezado/Factura/" + encabezado.getId();
    }
    

    // Calcular valores
    detalle.setValor(producto.getValorUnitario());
    float subtotal = producto.getValorUnitario() * detalle.getCantidad();
    float descuento = detalle.getDescuento();
    float total = subtotal - descuento;

    detalle.setSubtotal(subtotal);
    detalle.setTotal(total);

    detalleDao.save(detalle);

    // Actualizar stock
    producto.setStock(producto.getStock() - detalle.getCantidad());
    productoDao.update(producto);

    // Actualizar encabezado
    encabezado.setSubTotal(encabezado.getSubTotal() + subtotal);
    encabezado.setDescuentottl(encabezado.getDescuentottl() + descuento);
    encabezado.setTotal(encabezado.getTotal() + total);
    encabezadoDao.save(encabezado);

    redirectAttrs.addFlashAttribute("Mensaje", "Producto agregado correctamente");

    return "redirect:/Encabezado/Factura/" + encabezado.getId();
}

@PostMapping("/crear2")
    public String crearDetalle2(@ModelAttribute Detalle detalle, RedirectAttributes redirectAttrs) {
    Encabezado encabezado = encabezadoDao.findById(detalle.getIdEncabezado());

    if (encabezado == null) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: El encabezado no existe");
        return "redirect:/Encabezado/Factura";
    }

    Producto producto = productoDao.findById(detalle.getIdProducto());

    if (producto == null) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: Producto no encontrado");
        return "redirect:/Encabezado/Factura/" + encabezado.getId();
    }

    if (producto.getStock() < detalle.getCantidad()) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: No hay stock suficiente del producto " + producto.getNombre());
        return "redirect:/Encabezado/Factura/" + encabezado.getId();
    }

    if (producto.getValorUnitario() < detalle.getDescuento()) {
        redirectAttrs.addFlashAttribute("Mensaje", "Error: No se puede aplicar un descuento mayor al precio unitario de " + producto.getNombre());
        return "redirect:/Encabezado/Factura/" + encabezado.getId();
    }
    

    // Calcular valores
    detalle.setValor(producto.getValorUnitario());
    float subtotal = producto.getValorUnitario() * detalle.getCantidad();
    float descuento = detalle.getDescuento();
    float total = subtotal - descuento;

    detalle.setSubtotal(subtotal);
    detalle.setTotal(total);

    detalleDao.save(detalle);

    // Actualizar stock
    producto.setStock(producto.getStock() - detalle.getCantidad());
    productoDao.update(producto);

    // Actualizar encabezado
    encabezado.setSubTotal(encabezado.getSubTotal() + subtotal);
    encabezado.setDescuentottl(encabezado.getDescuentottl() + descuento);
    encabezado.setTotal(encabezado.getTotal() + total);
    encabezadoDao.save(encabezado);

    redirectAttrs.addFlashAttribute("Mensaje", "Producto agregado correctamente");

    return "redirect:/Encabezado/actualizar/" + encabezado.getId();
}

@GetMapping("/finalizar/{idEncabezado}")
public String finalizarFactura(@PathVariable Long idEncabezado, RedirectAttributes redirectAttrs) {
    
    List<Detalle> detalles = detalleDao.findAll();

    Detalle encontrado = null;
    for (Detalle d : detalles) {
        if (d.getIdEncabezado().equals(idEncabezado)) {
            encontrado = d;
            break;
        }
    }

    if (encontrado == null || detalles.isEmpty()) {
        redirectAttrs.addFlashAttribute("Mensaje", "La factura debe tener al menos un producto.");
        return "redirect:/Encabezado/Factura/" + idEncabezado;
    }

    redirectAttrs.addFlashAttribute("Mensaje", "Factura finalizada correctamente.");
    return "redirect:/Encabezado/Factura";
}

@GetMapping("/finalizar2/{idEncabezado}")
public String finalizarFactura2(@PathVariable Long idEncabezado, RedirectAttributes redirectAttrs) {
    
    List<Detalle> detalles = detalleDao.findAll();

    Detalle encontrado = null;
    for (Detalle d : detalles) {
        if (d.getIdEncabezado().equals(idEncabezado)) {
            encontrado = d;
            break;
        }
    }

    if (encontrado == null || detalles.isEmpty()) {
        redirectAttrs.addFlashAttribute("Mensaje", "La factura debe tener al menos un producto.");
        return "redirect:/Encabezado/actualizar/" + idEncabezado;
    }

    redirectAttrs.addFlashAttribute("Mensaje", "Factura finalizada correctamente.");
    return "redirect:/Encabezado/Listar";
}

@PostMapping("/actualizar")
public String actualizar(@ModelAttribute Detalle detalle, RedirectAttributes redirectAttrs) {
    Detalle existente = detalleDao.findById(detalle.getId());
    Encabezado encabezado = encabezadoDao.findById(detalle.getIdEncabezado());
    
    if (existente != null && encabezado != null) {
        // Valores originales
        float subtotalAnterior = existente.getSubtotal();
        float descuentoAnterior = existente.getDescuento();
        float totalAnterior = existente.getTotal();

        Producto producto = productoDao.findById(detalle.getIdProducto());
        if (producto != null) {
            float nuevoSubtotal = producto.getValorUnitario() * detalle.getCantidad();
            float nuevoDescuento = detalle.getDescuento();
            float nuevoTotal = nuevoSubtotal - nuevoDescuento;

            encabezado.setSubTotal(encabezado.getSubTotal() - subtotalAnterior + nuevoSubtotal);
            encabezado.setDescuentottl(encabezado.getDescuentottl() - descuentoAnterior + nuevoDescuento);
            encabezado.setTotal(encabezado.getTotal() - totalAnterior + nuevoTotal);

            existente.setCantidad(detalle.getCantidad());
            existente.setDescuento(nuevoDescuento);
            existente.setSubtotal(nuevoSubtotal);
            existente.setTotal(nuevoTotal);
            existente.setValor(producto.getValorUnitario());

            encabezadoDao.save(encabezado);
            detalleDao.save(existente);
        }
    }

    redirectAttrs.addFlashAttribute("Mensaje", "Factura actualizada correctamente.");
    return "redirect:/Encabezado/actualizar/" + detalle.getIdEncabezado();
}


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable long id, RedirectAttributes redirectAttrs) {
    Detalle detalle = detalleDao.findById(id);

    if (detalle != null) {
        long idEncabezado = detalle.getIdEncabezado();
        Encabezado encabezado = encabezadoDao.findById(idEncabezado);

        if (encabezado != null) {
            encabezado.setSubTotal(encabezado.getSubTotal() - detalle.getSubtotal());
            encabezado.setDescuentottl(encabezado.getDescuentottl() - detalle.getDescuento());
            encabezado.setTotal(encabezado.getTotal() - detalle.getTotal());

            encabezadoDao.save(encabezado);
        }

        detalleDao.delete(id);

        redirectAttrs.addFlashAttribute("Mensaje", "Producto eliminado de la factura correctamente.");
        return "redirect:/Encabezado/actualizar/" + idEncabezado;
    }

    return "redirect:/Encabezado/listar";
}


}

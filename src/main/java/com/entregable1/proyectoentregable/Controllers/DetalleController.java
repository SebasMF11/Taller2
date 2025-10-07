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

    @PostMapping("/crear/{direccion}")
    public String crearDetalle(@ModelAttribute Detalle detalle,@PathVariable int direccion, RedirectAttributes redirectAttrs) {
        Encabezado encabezado = encabezadoDao.findById(detalle.getIdEncabezado());
        
        if (encabezado == null) {
            redirectAttrs.addFlashAttribute("Mensaje", "Error: El encabezado no existe");
            return "redirect:/Encabezado/Factura";
        }

        Producto producto = productoDao.findById(detalle.getIdProducto());
        
        if (producto == null) {
            redirectAttrs.addFlashAttribute("Mensaje", "Error: Producto no encontrado"); 
            if(direccion == 1){
                return "redirect:/Encabezado/Factura/" + encabezado.getId();
            }else{
                return "redirect:/Encabezado/actualizar/" + encabezado.getId();
            }
        }

        if (producto.getStock() < detalle.getCantidad()) {
            redirectAttrs.addFlashAttribute("Mensaje", "Error: No hay stock suficiente del producto " + producto.getNombre());
             if(direccion == 1){
                return "redirect:/Encabezado/Factura/" + encabezado.getId();
            }else{
                return "redirect:/Encabezado/actualizar/" + encabezado.getId();
            }
        }

        if (producto.getValorUnitario() < detalle.getDescuento()) {
            redirectAttrs.addFlashAttribute("Mensaje", "Error: No se puede aplicar un descuento mayor al precio unitario de " + producto.getNombre());
             if(direccion == 1){
                return "redirect:/Encabezado/Factura/" + encabezado.getId();
            }else{
                return "redirect:/Encabezado/actualizar/" + encabezado.getId();
            }
        }

        Detalle yaexiste = verificarDetalle(detalle, encabezado);

        float subtotal = producto.getValorUnitario() * detalle.getCantidad();
        float descuento = detalle.getDescuento();
        float total = subtotal - descuento;

        if (yaexiste == null){
            detalle.setValor(producto.getValorUnitario());
            detalle.setSubtotal(subtotal);
            detalle.setTotal(total);

            detalleDao.save(detalle);

            encabezado.setSubTotal(encabezado.getSubTotal() + detalle.getSubtotal());
            encabezado.setDescuentottl(encabezado.getDescuentottl() + detalle.getDescuento());
            encabezado.setTotal(encabezado.getTotal() + detalle.getTotal());
            encabezadoDao.update(encabezado);

            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoDao.update(producto);   
        }


        redirectAttrs.addFlashAttribute("Mensaje", "Producto agregado correctamente");
        
        if(direccion == 1){
            return "redirect:/Encabezado/Factura/" + encabezado.getId();
        }else{
            return "redirect:/Encabezado/actualizar/" + encabezado.getId();
        }
    }

    @GetMapping("/finalizar/{direccion}/{idEncabezado}")
    public String finalizarFactura(@PathVariable Long idEncabezado, @PathVariable int direccion, RedirectAttributes redirectAttrs) {
        
        List<Detalle> detalles = detalleDao.findAll();

        Detalle encontrado = null;
        for (Detalle d : detalles) {
            if (d.getIdEncabezado().equals(idEncabezado)) {
                encontrado = d;
                break;
            }
        }

        if (encontrado == null || detalles.isEmpty()) {
            if(direccion == 1){
                redirectAttrs.addFlashAttribute("Mensaje", "La factura debe tener al menos un producto.");
                return "redirect:/Encabezado/Factura/" + idEncabezado;
            }else{
                redirectAttrs.addFlashAttribute("Mensaje", "La factura debe tener al menos un producto.");
                return "redirect:/Encabezado/actualizar/" + idEncabezado;
            }
        }

        if (direccion == 1){
            redirectAttrs.addFlashAttribute("Mensaje", "Factura finalizada correctamente.");
            return "redirect:/Encabezado/Factura";
        }else{
            redirectAttrs.addFlashAttribute("Mensaje", "Factura finalizada correctamente.");
            return "redirect:/Encabezado/Listar";
        }
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Detalle detalle, RedirectAttributes redirectAttrs) {
        Detalle DetExistente = detalleDao.findById(detalle.getId());
        Encabezado encabezado = encabezadoDao.findById(detalle.getIdEncabezado());
        
        if (DetExistente != null && encabezado != null) {
            // Valores originales
            float subtotalAnterior = DetExistente.getSubtotal();
            float descuentoAnterior = DetExistente.getDescuento();
            float totalAnterior = DetExistente.getTotal();

            Producto producto = productoDao.findById(detalle.getIdProducto());
            if (producto != null) {
                float nuevoSubtotal = producto.getValorUnitario() * detalle.getCantidad();
                float nuevoDescuento = detalle.getDescuento();
                float nuevoTotal = nuevoSubtotal - nuevoDescuento;

                encabezado.setSubTotal(encabezado.getSubTotal() - subtotalAnterior + nuevoSubtotal);
                encabezado.setDescuentottl(encabezado.getDescuentottl() - descuentoAnterior + nuevoDescuento);
                encabezado.setTotal(encabezado.getTotal() - totalAnterior + nuevoTotal);

                DetExistente.setCantidad(detalle.getCantidad());
                DetExistente.setDescuento(nuevoDescuento);
                DetExistente.setSubtotal(nuevoSubtotal);
                DetExistente.setTotal(nuevoTotal);
                DetExistente.setValor(producto.getValorUnitario());

                encabezadoDao.save(encabezado);
                detalleDao.update(DetExistente);
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

    public Detalle verificarDetalle(Detalle detalle, Encabezado encabezado){
        Detalle yaexiste = null;

        Producto producto = productoDao.findById(detalle.getIdProducto());

        List<Detalle> Detalles = detalleDao.findAll();

        for(Detalle d : Detalles){
            if(d.getIdProducto().equals(detalle.getIdProducto()) 
            && d.getIdEncabezado().equals(detalle.getIdEncabezado())){
                    yaexiste = d;

                    detalle.setCantidad(detalle.getCantidad() + d.getCantidad());

                    detalle.setValor(producto.getValorUnitario());

                    float subtotal = producto.getValorUnitario() * detalle.getCantidad();

                    detalle.setSubtotal(subtotal);

                    if(detalle.getDescuento() > subtotal){
                        detalle.setTotal(0);  
                    }else{
                        detalle.setTotal(subtotal - detalle.getDescuento());  
                    }
                    detalle.setDescuento(d.getDescuento() + detalle.getDescuento());
                    detalle.setId(d.getId());

                    encabezado.setSubTotal(encabezado.getSubTotal() - d.getSubtotal() + detalle.getSubtotal());
                    encabezado.setDescuentottl(encabezado.getDescuentottl() - d.getDescuento() + detalle.getDescuento());
                    encabezado.setTotal(encabezado.getTotal() - d.getTotal() + detalle.getTotal());
                    
                    encabezadoDao.save(encabezado);
                    detalleDao.update(detalle);
                    
                    producto.setStock(producto.getStock() + detalle.getCantidad() - d.getCantidad());
                    productoDao.update(producto);  
                    
                    break;
            }
        }

        return yaexiste;
    }
}

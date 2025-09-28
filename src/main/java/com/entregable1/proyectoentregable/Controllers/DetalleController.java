package com.entregable1.proyectoentregable.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.entregable1.proyectoentregable.Models.DAO.IDetalledao;
import com.entregable1.proyectoentregable.Models.DAO.IEncabezadodao;
import com.entregable1.proyectoentregable.Models.DAO.IProductodao;
import com.entregable1.proyectoentregable.Models.Entity.Detalle;
import com.entregable1.proyectoentregable.Models.Entity.Encabezado;
import com.entregable1.proyectoentregable.Models.Entity.Producto;

@Controller
public class DetalleController {

    @Autowired
    private IDetalledao detalleDao;

    @Autowired
    private IEncabezadodao encabezadoDao;

    @Autowired
    private IProductodao productoDao;

    @PostMapping("/Detalle/crear")
    public String crearDetalle(Detalle detalle, Model model) {
        // Validar que el encabezado exista
        Encabezado encabezado = encabezadoDao.findById(detalle.getIdEncabezado());
        if (encabezado == null) {
            model.addAttribute("Mensaje", "Error: El encabezado no existe");
            return "Factura/index";
        }

        // Validar producto
        Producto producto = productoDao.findById(detalle.getIdProducto());
        if (producto == null) {
            model.addAttribute("Mensaje", "Error: Producto no encontrado");
            return "Factura/index";
        }

        // Validar stock
        if (producto.getStock() < detalle.getCantidad()) {
            model.addAttribute("Mensaje", "Error: No hay stock suficiente del producto " + producto.getNombre());
            return "Factura/index";
        }

        // Actualizar valores en el detalle
        detalle.setValor(producto.getValorUnitario());
        float subtotal = producto.getValorUnitario() * detalle.getCantidad();
        float descuento = detalle.getDescuento();
        float total = subtotal - descuento;

        detalle.setSubtotal(subtotal);
        detalle.setTotal(total);

        // Asociar encabezado
        detalle.setIdEncabezado(encabezado.getId());

        // Guardar detalle
        detalleDao.save(detalle);

        // Actualizar stock
        producto.setStock(producto.getStock() - detalle.getCantidad());
        productoDao.save(producto);

        // Recalcular totales del encabezado
        encabezado.setSubTotal(encabezado.getSubTotal() + subtotal);
        encabezado.setDescuentottl(encabezado.getDescuentottl() + descuento);
        encabezado.setTotal(encabezado.getTotal() + total);
        encabezadoDao.save(encabezado);

        // Redirigir a index con mensaje
        model.addAttribute("Mensaje", "Producto agregado correctamente");
        model.addAttribute("Encabezado", encabezado);
        model.addAttribute("detalles", detalleDao.findAll());

        return "Factura/index";
    }
}

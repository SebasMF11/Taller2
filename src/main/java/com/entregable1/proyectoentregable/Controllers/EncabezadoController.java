package com.entregable1.proyectoentregable.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entregable1.proyectoentregable.Models.DAO.Clientedao;
import com.entregable1.proyectoentregable.Models.DAO.Encabezadodao;
import com.entregable1.proyectoentregable.Models.Entity.Cliente;
import com.entregable1.proyectoentregable.Models.Entity.Detalle;
import com.entregable1.proyectoentregable.Models.Entity.Encabezado;

@Controller
@RequestMapping("/Encabezado")
public class EncabezadoController {
    
    @Autowired
    private Encabezadodao Encabezadodao;

    @Autowired
    private Clientedao Clientedao;

    @GetMapping({"/Listar","/"})
    public String listar(Model model) {
        List<Encabezado> encabezados = Encabezadodao.findAll();

        model.addAttribute("encabezados", encabezados);
        model.addAttribute("titulo", "Factura");
        model.addAttribute("Mensaje", "Ingresa el Id del cliente:");
        model.addAttribute("Encabezado", new Encabezado());
        model.addAttribute("detalle", new Detalle());
        return "Factura/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(Model model, @PathVariable Long id) {
        Encabezado encabezado = Encabezadodao.findById(id);

        model.addAttribute("encabezado", encabezado);
        model.addAttribute("titulo", "Ver de Encabezado");
        return "Factura/ver";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute Encabezado encabezadonuevo, RedirectAttributes redirectAttrs) {
        Cliente cliente = Clientedao.findById(encabezadonuevo.getIdCliente());

        if(cliente == null){
            redirectAttrs.addFlashAttribute("Mensaje","El cliente no existe");
        } else {
            encabezadonuevo.setFecha(new Date()); // asigna fecha actual
            Encabezadodao.save(encabezadonuevo);
            redirectAttrs.addFlashAttribute("Mensaje","Encabezado creado correctamente");
        }
        return "redirect:/Encabezado/Listar";
    }
    
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Encabezado encabezadonuevo, RedirectAttributes redirectAttrs){
        Encabezado encabezadoexistente = Encabezadodao.findById(encabezadonuevo.getId());

        if(encabezadoexistente != null){
            encabezadoexistente.setDescuentottl(encabezadonuevo.getDescuentottl());
            encabezadoexistente.setSubTotal(encabezadonuevo.getSubTotal());
            encabezadoexistente.setTotal(encabezadonuevo.getTotal());
            Encabezadodao.save(encabezadoexistente);
            redirectAttrs.addFlashAttribute("Mensaje","Encabezado actualizado correctamente");
        } else {
            redirectAttrs.addFlashAttribute("Mensaje","El encabezado no existe");
        }

        return "redirect:/Encabezado/Listar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar (@PathVariable Long id, RedirectAttributes redirectAttrs){
        Encabezadodao.delete(id);
        redirectAttrs.addFlashAttribute("Mensaje","Encabezado eliminado correctamente");
        return "redirect:/Encabezado/Listar";
    }
}

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
import com.entregable1.proyectoentregable.Models.DAO.Detalledao;
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

    @Autowired
    private Detalledao Detalledao;

    @GetMapping("/Listar")
    public String Listar(Model model) {
        List<Encabezado> Enca = Encabezadodao.findAll();
        model.addAttribute("Encabezados", new Encabezado());
        model.addAttribute("Encabezado", Enca);
        return "Factura/lista";
    }
    

    @GetMapping({"/Factura","/"})
    public String facturacliente(Model model) {
        model.addAttribute("Encabezado", new Encabezado());

        model.addAttribute("titulo", "Factura");
        return "Factura/PedirCliente";
    }
    
    @GetMapping({"/Factura/{id}"})
    public String facturaproducto(Model model, @PathVariable Long id){
        Detalle Detalle = new Detalle();
        Encabezado encabezado = Encabezadodao.findById(id);
        List<Detalle> detalles = Detalledao.findAll();
        Cliente cliente = Clientedao.findById(encabezado.getIdCliente());
      
        Detalle.setIdEncabezado(encabezado.getId());
        
        model.addAttribute("titulo", "Factura");
        model.addAttribute("Encabezado", encabezado);
        model.addAttribute("detalles", detalles);
        model.addAttribute("cliente", cliente);
        model.addAttribute("detalle",  Detalle);

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
        redirectAttrs.addFlashAttribute("clienteValido", false);
        
        if(cliente == null){
            redirectAttrs.addFlashAttribute("Mensaje","El cliente no existe");
        } else {
           // if(null!=Encabezadodao.findById(cliente.getId())){
             //   redirectAttrs.addFlashAttribute("Mensaje","Este cliente tiene una factura.");
           // }else{
                encabezadonuevo.setFecha(new Date());
                Encabezadodao.save(encabezadonuevo); 
                redirectAttrs.addFlashAttribute("Mensaje","Encabezado creado correctamente");
                redirectAttrs.addFlashAttribute("clienteValido", true);
                return "redirect:/Encabezado/Factura/" + encabezadonuevo.getId();
            }
        //}
        return "redirect:/Encabezado/Factura";
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
            
            return "redirect:/Encabezado/Factura" + encabezadoexistente.getId();
        } else {
            redirectAttrs.addFlashAttribute("Mensaje","El encabezado no existe");
        }

        return "redirect:/Encabezado/Factura";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar (@PathVariable Long id, RedirectAttributes redirectAttrs){
        Encabezadodao.delete(id);
        redirectAttrs.addFlashAttribute("Mensaje","Factura eliminada correctamente");
        return "redirect:/Encabezado/Factura";
    }
}

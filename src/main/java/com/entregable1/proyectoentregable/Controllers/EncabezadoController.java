package com.entregable1.proyectoentregable.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entregable1.proyectoentregable.Models.DAO.Clientedao;
import com.entregable1.proyectoentregable.Models.DAO.Encabezadodao;
import com.entregable1.proyectoentregable.Models.Entity.Cliente;
import com.entregable1.proyectoentregable.Models.Entity.Encabezado;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/Encabezado")
public class EncabezadoController {
    
    @Autowired
    private Encabezadodao Encabezadodao;
    private Clientedao Clientedao;

    @GetMapping({"/Listar","/"})
    public String listar(Model model) {
        List<Encabezado> encabezados = Encabezadodao.findAll();

        model.addAttribute("encabezados", encabezados);
        model.addAttribute("titulo", "Lista de Encabezados");
        return "Factura/index";
    }

    @GetMapping("/ver/{id}")
    public String ver(Model model, @PathVariable Long id) {
        Encabezado encabezado = Encabezadodao.findById(id);

        model.addAttribute("encabezado", encabezado);
        model.addAttribute("titulo", "Ver de Encabezado");
        return "Factura/ver";
    }



    @GetMapping("/crear")
    public String crear(@ModelAttribute Encabezado encabezadonuevo, RedirectAttributes redirectAttrs) {
        Cliente cliente = new Cliente();
        cliente = Clientedao.findById(encabezadonuevo.getId());

        if(cliente == null){
            redirectAttrs.addFlashAttribute("Mensaje","El cliente no existe");

        }else{
            Encabezadodao.save(encabezadonuevo);
            
            redirectAttrs.addFlashAttribute("Mensaje","Bien");
        }

        
        return "redirect:Listar";
    }
    
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Encabezado encabezadonuevo, RedirectAttributes redirectAttrs){
        Encabezado encabezadoexistente = Encabezadodao.findById(encabezadonuevo.getId());

        encabezadoexistente.setDescuento(encabezadonuevo.getDescuento());

        return "redirect:Listar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar (@PathVariable Long id, RedirectAttributes redirectAttrs){
        Encabezadodao.delete(id);

        return "redirect:/Encabezado/Listar";
    }
}

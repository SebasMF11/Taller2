package com.entregable1.proyectoentregable.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entregable1.proyectoentregable.Models.DAO.Encabezadodao;
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

    @GetMapping({"/Listar","/"})
    public String listar(Model model) {
        List<Encabezado> encabezados = Encabezadodao.findAll();
        model.addAttribute("encabezado", new Encabezado());

        model.addAttribute("encabezados", encabezados);
        model.addAttribute("titulo", "Lista de Enzabezados");
        return "Encabezado/index";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute Encabezado encabezadonuevo, RedirectAttributes redirectAttrs) {
        Encabezadodao.save(encabezadonuevo);

        redirectAttrs.addFlashAttribute("Mensaje","Mensaje aca");
        
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

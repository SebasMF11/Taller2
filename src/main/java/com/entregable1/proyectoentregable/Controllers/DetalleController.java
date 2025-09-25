package com.entregable1.proyectoentregable.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entregable1.proyectoentregable.Models.DAO.Detalledao;
import com.entregable1.proyectoentregable.Models.Entity.Detalle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/Detalle")
public class DetalleController {
    
    @Autowired
    private Detalledao Detalledao;

    @GetMapping({"/Listar","/"})
    public String listar(Model model){
        List<Detalle> detalles = Detalledao.findAll();

        model.addAttribute("detalles", detalles);
        model.addAttribute("detalle", new Detalle());
        model.addAttribute("titulo", "Lista de detalles");
        return "Detalle/index";
    }

    @PostMapping("/crear")
    public String crear() {
        
        return "redirect:Listar";
    }

    @PostMapping("/actualizar")
    public String actualizar() {
        //TODO: process POST request
        
        return "redirect:Listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar() {
        return "redirrect: /Detalle/Listar";
    }
    
    
    
}

package com.entregable1.proyectoentregable.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entregable1.proyectoentregable.Models.DAO.Clientedao;

import com.entregable1.proyectoentregable.Models.Entity.Cliente;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/Cliente")
public class ClienteController {
    @Autowired
    private Clientedao Clientedao;

 
    
    @GetMapping({"/Listar", "/"})
    public String listar(Model model){

       
        List<Cliente> clientes = Clientedao.findAll();
        model.addAttribute("cliente", new Cliente()); 
    
        model.addAttribute("clientes", clientes);
        model.addAttribute("titulo", "Lista de clientes");

        return "Cliente/index";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute Cliente clientenuevo, RedirectAttributes redirectAttrs){
        
        Clientedao.save(clientenuevo);
       
        redirectAttrs.addFlashAttribute("mensaje", "mensaje aca");
        return "redirect:Listar";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttrs){
            
        Cliente clienteExistente = Clientedao.findById(cliente.getId());
        
        clienteExistente.setNombre(cliente.getNombre().trim());
        clienteExistente.setApellido(cliente.getApellido().trim());
        clienteExistente.setCorreo(cliente.getCorreo().trim());
        
        Clientedao.update(clienteExistente);
        
        redirectAttrs.addFlashAttribute("mensaje", "mensaje aca");

        return "redirect:Listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs){
        Clientedao.delete(id);
        
        redirectAttrs.addFlashAttribute("mensaje", "mensaje aca");

        return "redirect:/Cliente/Listar";
    }
    
}

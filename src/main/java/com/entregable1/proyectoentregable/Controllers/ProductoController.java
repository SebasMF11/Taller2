package com.entregable1.proyectoentregable.Controllers;

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

import com.entregable1.proyectoentregable.Models.DAO.Productodao;
import com.entregable1.proyectoentregable.Models.Entity.Producto;

@Controller
@RequestMapping("/Producto")
public class ProductoController {
    @Autowired
    private Productodao productoDao;

   @GetMapping({"/Listar", "/"})
    public String listar(Model model){

        List<Producto> productos = productoDao.findAll();
        model.addAttribute("producto", new Producto()); 
        model.addAttribute("productos", productos);
        
        model.addAttribute("titulo", "Lista de productos");

        return "Producto/index";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute Producto productonuevo, RedirectAttributes redirectAttrs){
        
        productoDao.save(productonuevo);
       
        return "redirect:Listar";
    }
    
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Producto Producto, RedirectAttributes redirectAttrs){
            
        Producto productoExistente = productoDao.findById(Producto.getId());
        
        productoExistente.setNombre(Producto.getNombre().trim());
        productoExistente.setValorUnitario(Producto.getValorUnitario());
        productoExistente.setDescripcion(Producto.getDescripcion().trim());
        productoExistente.setStock(Producto.getStock());
        
        productoDao.update(productoExistente);
        
        return "redirect:Listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model){
        productoDao.delete(id);
        
        return "redirect:/Producto/Listar";
    }
    
    
}

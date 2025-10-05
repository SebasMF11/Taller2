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
import com.entregable1.proyectoentregable.Models.DAO.Productodao;
import com.entregable1.proyectoentregable.Models.Entity.Cliente;
import com.entregable1.proyectoentregable.Models.Entity.Detalle;
import com.entregable1.proyectoentregable.Models.Entity.Encabezado;
import com.entregable1.proyectoentregable.Models.Entity.Producto;

@Controller
@RequestMapping("/Encabezado")
public class EncabezadoController {
    
    @Autowired
    private Encabezadodao Encabezadodao;

    @Autowired
    private Clientedao Clientedao;

    @Autowired
    private Detalledao Detalledao;

    @Autowired
    private Productodao Productodao;

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
        List<Producto> productos = Productodao.findAll();
      
        Detalle.setIdEncabezado(encabezado.getId());
        
        model.addAttribute("titulo", "Factura");
        model.addAttribute("Encabezado", encabezado);
        model.addAttribute("detalles", detalles);
        model.addAttribute("cliente", cliente);
        model.addAttribute("detalle",  Detalle);
        model.addAttribute("productos",  productos);

        return "Factura/index";
    }

    @GetMapping({"/ver/{id}"})
    public String factura(Model model, @PathVariable Long id){
        Detalle Detalle = new Detalle();
        Encabezado encabezado = Encabezadodao.findById(id);
        List<Detalle> detalles = Detalledao.findAll();
        List<Producto> productos = Productodao.findAll();
        Cliente cliente = Clientedao.findById(encabezado.getIdCliente());
      
        Detalle.setIdEncabezado(encabezado.getId());
        
        model.addAttribute("titulo", "Factura");
        model.addAttribute("Encabezado", encabezado);
        model.addAttribute("detalles", detalles);
        model.addAttribute("cliente", cliente);
        model.addAttribute("detalle",  Detalle);
        model.addAttribute("productos",  productos);

        return "Factura/ver";
    }

    @GetMapping({"/actualizar/{id}"})
    public String actualizar(Model model, @PathVariable Long id){
        Detalle Detalle = new Detalle();
        Encabezado encabezado = Encabezadodao.findById(id);
        List<Detalle> detalles = Detalledao.findAll();
        Cliente cliente = Clientedao.findById(encabezado.getIdCliente());
        List<Producto> productos = Productodao.findAll();
      
        Detalle.setIdEncabezado(encabezado.getId());
        
        model.addAttribute("titulo", "Factura");
        model.addAttribute("Encabezado", encabezado);
        model.addAttribute("detalles", detalles);
        model.addAttribute("cliente", cliente);
        model.addAttribute("detalle",  Detalle);
        model.addAttribute("productos",  productos);

        return "Factura/actu";
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
            //}
        }
        return "redirect:/Encabezado/Factura";
    }
    
   
    @GetMapping("/eliminar/{direccion}/{id}")
    public String eliminar (@PathVariable Long id,@PathVariable int direccion, RedirectAttributes redirectAttrs){
        Encabezadodao.delete(id);
        redirectAttrs.addFlashAttribute("Mensaje","Factura eliminada correctamente");
        if (direccion == 1){
            return "redirect:/";
        }else{
            return "redirect:/Encabezado/Listar";
        }
    }
}

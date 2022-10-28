package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registro")
    public String registroAutor(ModelMap modelo) {

        return "autor-registro.html";

    }

    @PostMapping("/registro")
    public String crearAutor(@RequestParam String nombre, ModelMap model) {
        System.out.println("nombre = " + nombre);
        try {
            autorServicio.crear(nombre, true);
            model.addAttribute("exito", "Autor cargado correctamente");
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("nombre", nombre);
        }
        return "autor-registro.html";
    }

    @PostMapping("/save")
    public String save(Autor autor) {
        autorServicio.save(autor);
        return "redirect:/autor/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws ErrorServicio {

        Autor autor = autorServicio.buscarPorId(id);
        modelo.put("autor", autor);
        
        return "modificar-autor.html";
    }
  
    @PostMapping("/editar")
    public String editar(@RequestParam String id, @RequestParam String nombre, ModelMap modelo) throws ErrorServicio {

       autorServicio.modificar(id, nombre);
       
       return "redirect:/autor/lista";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autores = autorServicio.listar();
        modelo.put("autores", autores);

        return "/lista-autores.html";

    }
    @GetMapping("/eliminar/{id}")
    public String eliminar( @PathVariable String id, ModelMap modelo){
     autorServicio.eliminar(id);
        System.out.println("Registro eliminado");
     return "redirect:/autor/lista";
    }
}

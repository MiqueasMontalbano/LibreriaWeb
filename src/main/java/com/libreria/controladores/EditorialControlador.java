
package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditorialControlador {
        
        
 @Autowired
 private EditorialServicio editorialServicio;
        
        
 @GetMapping("/editorial/lista")
 public String lista(ModelMap modelo){
     List <Editorial> editoriales = editorialServicio.listar();
     modelo.put("editoriales",editoriales);
     
     return "lista-editoriales.html";   
  
 }   
   @GetMapping("/editorial/registro")
    public String registroEditorial(ModelMap modelo) {

        return "editorial-registro.html";

    }
  @PostMapping("/editorial/registro")
 public String crearEditorial(@RequestParam String nombre, ModelMap modelo){
      System.out.println("nombre = " + nombre);
      try {
        editorialServicio.crear(nombre, true);
      } catch (Exception e) {
        modelo.put(nombre,"nombre");
        return "redirect:/editorial/registro";
      }
 return "redirect:/editorial/lista";    
 } 
  @GetMapping("/editorial/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws ErrorServicio {

        Editorial editorial = editorialServicio.buscarPorId(id);
        modelo.put("editorial",editorial);
        
        return "modificar-editorial.html";
    }
  
    @PostMapping("/editorial/editar")
    public String editar(@RequestParam String id, @RequestParam String nombre, ModelMap modelo) throws ErrorServicio {

       editorialServicio.modificar(id, nombre);
       
       return "redirect:/editorial/lista";
    }
  @PostMapping("/editorial/save")
    public String save(Editorial editorial) {
        editorialServicio.save(editorial);
        return "redirect:/autor/lista";
    }
  @GetMapping("editorial/eliminar/{id}")
    public String eliminar( @PathVariable String id, ModelMap modelo){
     editorialServicio.eliminar(id);
        System.out.println("Registro eliminado");
     return "redirect:/editorial/lista";
    }
 
}

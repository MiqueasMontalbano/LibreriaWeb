/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import com.libreria.servicios.EditorialServicio;
import com.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registro/libro")
    public String registrarLibro(ModelMap modelo) {
        List<Autor> autores = autorServicio.listar();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.put("editoriales", editoriales);

        return "libro-registro.html";
    }

    /*@GetMapping("/registro/libro")
public String registrar(ModelMap modelo){
 List<Editorial> editoriales=editorialServicio.listar();
    modelo.put("editoriales",editoriales);  
     return "libro-registro.html"; 
    
}*/
    @PostMapping("/registro/libro")
    public String crearLibro(ModelMap modelo, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer isbn, @RequestParam Integer ejemplares, @RequestParam(required = false) Integer ejemplaresPrestados, @RequestParam String autorId, @RequestParam String editorialId,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/registro/libro";
        }
        try {
            libroServicio.crear(titulo, anio, isbn, ejemplares,true, autorId, editorialId);
             modelo.put("exito","'El libro " + titulo + " se cargo exitosamente'");
        } catch (Exception e) {
            
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("isbn", isbn);
            modelo.put("ejemplares", ejemplares);
            
            return "redirect:/registro/libro";
        }

        List<Autor> autores = autorServicio.listar();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.put("editoriales", editoriales);
        return "redirect:/lista/libros";

    }

    @GetMapping("libro/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        try {
            Libro libro = libroServicio.buscarPorId(id);
            modelo.put("libro", libro);

        } catch (ErrorServicio ex) {

        }
        List<Autor> autores = autorServicio.listar();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.listar();
        modelo.put("editoriales", editoriales);
        return "modificar-libro.html";

    }

    @PostMapping("/libro/editar")
    public String editar(@RequestParam String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer isbn, @RequestParam Integer ejemplares,@RequestParam String autorId, @RequestParam String editorialId) throws ErrorServicio {

        libroServicio.modificar(id, titulo, anio, isbn, ejemplares,autorId, editorialId);
        return "redirect:/lista/libros";

    }

    @GetMapping("/libro/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        libroServicio.eliminar(id);
        return "redirect:/lista/libros";
    }

    @GetMapping("/lista/libros")
    public String lista(ModelMap modelo) {
        List<Libro> libros = libroServicio.listar();
        modelo.put("libros", libros);

        return "/lista-libros.html";

    }

}

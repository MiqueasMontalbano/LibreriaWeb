
package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.EditorialRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
            
   @Autowired
   private EditorialRepositorio editorialRepositorio;
   
   @Transactional(rollbackFor = {Exception.class})
 public Libro crear(String titulo,Integer anio,Integer isbn,Integer ejemplares,boolean alta,String autorId,String editorialId) throws ErrorServicio{
     
       if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede ser nulo/vacio");
        }
        if (isbn == null || isbn == 0) {
            throw new ErrorServicio("El tamaño no puede ser nulo/vacio");
        }
        if (ejemplares == null || ejemplares == 0) {
            throw new ErrorServicio("El artista no puede ser nulo/vacio");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo");
        }

     Libro libro= new Libro();
     libro.setTitulo(titulo);
     libro.setAnio(anio);
     libro.setIsbn(isbn);
     libro.setEjemplares(ejemplares);
   /*  libro.setEjemplaresPrestados(ejemplaresPrestados);
     libro.setEjemplaresRestantes(ejemplaresPrestados-ejemplares); */
     libro.setAlta(true);
    Autor autor= autorRepositorio.getOne(autorId);
     libro.setAutor(autor);
Editorial editorial= editorialRepositorio.getOne(editorialId);
    libro.setEditorial(editorial);
     
     return libroRepositorio.save(libro);
}
public void modificar(String id,String titulo,Integer anio,Integer isbn,Integer ejemplares,String autorId,String editorialId) throws ErrorServicio{

    validar(titulo,anio,isbn,ejemplares);
    Optional<Libro> respuesta= libroRepositorio.findById(id);
    if (respuesta.isPresent()) {
        Libro libro= respuesta.get();
libro.setTitulo(titulo);
libro.setAnio(anio);
libro.setIsbn(isbn);
libro.setEjemplares(ejemplares);
/*libro.setEjemplaresPrestados(ejemplaresPrestados);
libro.setEjemplaresRestantes(ejemplaresRestantes); */
Autor autor= autorRepositorio.getOne(autorId);
libro.setAutor(autor);
Editorial editorial= editorialRepositorio.getOne(editorialId);
    libro.setEditorial(editorial);

libroRepositorio.save(libro);   
    } else {
     throw new ErrorServicio("No se encontro el libro");   
    }
 
} 

public void deshabilitar(String id) throws ErrorServicio{
Optional<Libro> respuesta= libroRepositorio.findById(id);
    if (respuesta.isPresent()) {
     Libro libro= respuesta.get();
     libro.setAlta(false);
     libroRepositorio.save(libro);
     
    }
  
}
@Transactional(rollbackFor = Exception.class)
public void eliminar(String id){
 libroRepositorio.deleteById(id);
    
}

@Transactional(readOnly = true)
public Libro buscarPorId(String id) throws ErrorServicio{
    Optional<Libro> respuesta = libroRepositorio.findById(id);
    if (respuesta.isPresent()) {
     return respuesta.get();
    } else {
    throw new ErrorServicio("El id no existe.");    
    }
  
}     


 @Transactional (readOnly = true)
  public List<Libro> listar(){
    return libroRepositorio.findAll ();  
}


private void validar(String titulo,Integer anio,Integer isbn,Integer ejemplares) throws ErrorServicio{
  if (titulo == null || titulo.isEmpty()) {
      throw new ErrorServicio("El titulo del libro no puede ser nulo");   
     }
      if (anio == null) {
      throw new ErrorServicio("El año no puede ser nulo");   
     }
      if (isbn == null) {
      throw new ErrorServicio("El isbn del libro no puede ser nulo");   
     }
       if (ejemplares == null || ejemplares < 0) {
      throw new ErrorServicio("El numero de ejemplares no puede ser nulo");   
     }
     
     
     
     
}
}
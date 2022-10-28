
package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

@Autowired
private EditorialRepositorio editorialRepositorio;
  
@Transactional(rollbackFor = Exception.class)
public Editorial crear(String nombre,boolean alta) throws ErrorServicio {
    if (nombre.isEmpty() || nombre == null) {
        throw new ErrorServicio("El nombre no puede ser nulo/vacio"); 
    }
  
 Editorial editorial= new Editorial();
 editorial.setNombre(nombre);
 editorial.setAlta(true);
 return editorialRepositorio.save(editorial);
}
public void modificar(String id,String nombre) throws ErrorServicio{
 Optional<Editorial> respuesta= editorialRepositorio.findById(id);
    if (respuesta.isPresent()) {
      Editorial editorial= respuesta.get();
      editorial.setNombre(nombre);
      editorialRepositorio.save(editorial);
    } else{
    throw new ErrorServicio("No se encotro la editorial solicitada");    
    }
    
}
 public void deshabilitar(String id) throws ErrorServicio{
Optional<Editorial> respuesta= editorialRepositorio.findById(id);
    if (respuesta.isPresent()) {
     Editorial editorial= respuesta.get();
     editorial.setAlta(false);
     editorialRepositorio.save(editorial);   
    }else{
     throw new ErrorServicio("No se encontro la editorial solicitada");   
    }
  }
@Transactional(readOnly = true)
public Editorial buscarPorId(String id) throws ErrorServicio{
    Optional<Editorial> respuesta = editorialRepositorio.findById(id);
    if (respuesta.isPresent()) {
     return respuesta.get();
    } else {
    throw new ErrorServicio("El id no existe.");    
    }
  
}

public void save(Editorial editorial){
editorialRepositorio.save(editorial);
}
 public List<Editorial> listar(){
  return editorialRepositorio.findAll();
 }
  @Transactional(rollbackFor = Exception.class)
public void eliminar(String id){
 editorialRepositorio.deleteById(id);
    
}
}

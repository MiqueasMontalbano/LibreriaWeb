
package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
 
@Autowired
private AutorRepositorio autorRepositorio;
 
@Transactional(rollbackFor = Exception.class)
public Autor crear(String nombre,boolean alta) throws ErrorServicio {
    validar(nombre);
 Autor autor= new Autor();
 autor.setNombre(nombre);
 autor.setAlta(true);
 return autorRepositorio.save(autor);
}

public void modificar(String id,String nombre) throws ErrorServicio{
 Optional<Autor> respuesta= autorRepositorio.findById(id);
    if (respuesta.isPresent()) {
      Autor autor= respuesta.get();
      autor.setNombre(nombre);
      autorRepositorio.save(autor);
    } else{
    throw new ErrorServicio("No se encotro el autor solicitado");    
    }
}
  public void deshabilitar(String id) throws ErrorServicio{
Optional<Autor> respuesta= autorRepositorio.findById(id);
    if (respuesta.isPresent()) {
     Autor autor= respuesta.get();
     autor.setAlta(false);
     autorRepositorio.save(autor);   
    }else{
     throw new ErrorServicio("No se encontro el autor solicitado");   
    }
  }
  @Transactional(rollbackFor = Exception.class)
public void eliminar(String id){
 autorRepositorio.deleteById(id);
    
}
@Transactional(readOnly = true)
public Autor buscarPorId(String id) throws ErrorServicio{
    Optional<Autor> respuesta = autorRepositorio.findById(id);
    if (respuesta.isPresent()) {
     return respuesta.get();
    } else {
    throw new ErrorServicio("El id no existe.");    
    }
  
}    
 public void validar(String nombre) throws ErrorServicio{
     if (nombre == null || nombre.isEmpty()) {
       throw new ErrorServicio("El nombre no puede ser nulo");  
       
     }
 
     
 }   
 public List<Autor> listar(){
  return autorRepositorio.findAll();
 }
 public Optional <Autor> listarId(String id){
     return autorRepositorio.findById(id);
 
 }
 public void save(Autor autor){
  autorRepositorio.save(autor);
 }
 public void getOne(String id){
  autorRepositorio.getOne(id);
 }
}


package com.libreria.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Libro {
    
@Id
@GeneratedValue(generator="uuid")
@GenericGenerator(name="uuid",strategy="uuid2")
private String id;  

@NotBlank()
private String titulo;
@NotBlank()
private Integer isbn;
@NotBlank()
private Integer anio;
@NotBlank()
private Integer ejemplares;
private Boolean alta;
//@Temporal (TemporalType.TIMESTAMP)
//private Date alta;

@NotEmpty
@OneToOne
     private Autor autor;  
  
@NotEmpty
    @OneToOne
    private Editorial editorial;

    public Libro(String id, @NotBlank String titulo,@NotBlank Integer isbn, @NotBlank Integer anio, @NotBlank Integer ejemplares, Boolean alta, @NotEmpty Autor autor,@NotEmpty Editorial editorial) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.alta = alta;
        this.autor = autor;
        this.editorial = editorial;
    }

  

 

    public Libro() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }


    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", titulo=" + titulo + ", isbn=" + isbn + ", anio=" + anio + ", ejemplares=" + ejemplares + ", alta=" + alta + '}';
    }


}

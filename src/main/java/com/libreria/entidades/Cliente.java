package com.libreria.entidades;
import com.libreria.entidades.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Cliente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2" ) 
    private String id;
    private String nickname;
    private String mail;
    private String contrasenia;
    private Boolean estado;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;

    
   public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMail() {
        return mail;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Boolean getEstado() {
        return estado;
    }

    public Date getAlta() {
        return alta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nickname=" + nickname + ", mail=" + mail + ", contrasenia=" + contrasenia + ", estado=" + estado + ", rol=" + rol + ", alta=" + alta + '}';
    }
    
    

}

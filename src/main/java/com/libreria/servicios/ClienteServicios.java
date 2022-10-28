package com.libreria.servicios;
import com.libreria.entidades.Rol;
import com.libreria.entidades.Cliente;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.libreria.repositorios.ClienteRepositorio;

@Service
public class ClienteServicios implements UserDetailsService{

    @Autowired
    private ClienteRepositorio usuarioRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Cliente registrar(String nickname, String mail, String contrasenia1, String contrasenia2) throws Exception {

        validar(nickname, mail, contrasenia1, contrasenia2);

        Cliente cliente = new Cliente();
        cliente.setNickname(nickname);
        cliente.setMail(mail);
        
        cliente.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia1));
      //  cliente.setAlta(new Date());
        cliente.setEstado(true);
        cliente.setRol(Rol.USER);
      

        return usuarioRepositorio.save(cliente);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Cliente modificar(String id, String nickname, String mail, String contrasenia1, String contrasenia2) throws Exception {

        validar(nickname, mail, contrasenia1, contrasenia2);

        Optional<Cliente> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente usuario = respuesta.get();
            usuario.setNickname(nickname);
            usuario.setMail(mail);
            usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia1));

            return usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("El usuario no existe.");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void estadoUsuario(String id) throws Exception {

        Optional<Cliente> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente usuario = respuesta.get();
            if (usuario.getEstado() == true) {
                usuario.setEstado(false);
            } else {
                usuario.setEstado(true);
            }
            usuarioRepositorio.save(usuario);
        } else {
            throw new Exception("El usuario no existe.");
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(String id) throws Exception {

        Optional<Cliente> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("El usuario que buscas no existe.");
        }

    }

    private void validar(String nickname, String mail, String contrasenia1, String contrasenia2) throws Exception {

        if (nickname == null || nickname.isEmpty()) {
            throw new Exception("El nickname no puede ser nulo.");
        }
        if (mail == null || mail.isEmpty()) {
            throw new Exception("El mail no puede ser nulo.");
        }
        if (contrasenia1 == null || contrasenia1.isEmpty() || contrasenia1.length() <= 7) { 
            throw new Exception("La contraseña no puede ser nula y debe tener mas de 7 caracteres.");
        }
        if ( !contrasenia1.equals(contrasenia2)) {
            throw new Exception("Las contraseñas deben ser iguales");
        }

    }
    

     
    

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Cliente usuario = (Cliente) usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {

            List<GrantedAuthority> permiso = new ArrayList();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROL_USER_REGISTRADO");
            permiso.add(p1);

//                GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO FOTO");
//                permiso.add(p2);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getMail(), usuario.getContrasenia(), permiso);
        } else {
            return null;
        }
    }
    
    
}


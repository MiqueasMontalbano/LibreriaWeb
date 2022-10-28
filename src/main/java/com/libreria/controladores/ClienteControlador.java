
package com.libreria.controladores;

import com.libreria.servicios.ClienteServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicios clienteServicio;
    
  @GetMapping("/registrar")
    public String registro() {
        return "cliente-registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nickname, @RequestParam String mail, @RequestParam String contrasenia1, @RequestParam String contrasenia2) {

        try {
            clienteServicio.registrar(nickname, mail, contrasenia1, contrasenia2);

        } catch (Exception e) {
            modelo.put("errorReg", e.getMessage());
            modelo.put("nickname", nickname);
            modelo.put("mail", mail);
            modelo.put("contrasenia1", contrasenia1);
            modelo.put("contrasenia2", contrasenia2);
            e.printStackTrace();
            return "cliente-registro.html";
        }
        modelo.put("titulo", "Bienvenido a la Galería de Arte Tamago!");
        modelo.put("descripcion", "Logeate para comenzar");
        return "redirect:/index";
   
    
}
    
     @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o contraseña incorrecta.");
        }
        if (logout != null) {
            model.put("logout", "Cerraste sesión correctamente.");
        }
        return "/login";
    }
}

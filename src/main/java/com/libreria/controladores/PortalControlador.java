
package com.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalControlador {
 
@GetMapping("/index")
public String index(ModelMap modelo){
    
return "index.html";    
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

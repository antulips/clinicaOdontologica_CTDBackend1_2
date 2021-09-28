package com.ClinicaOdontologica.controller.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/")
    public String home(){
        return"<h1>Welcome</h1>";
    }

    @GetMapping("/user")
    public String user(){
        return"<h1>Welcome</h1>";
    }

    @GetMapping("/admin")
    public String admin(){
        return"<h1>Welcome</h1>";
    }

    @GetMapping("/403")
    public String error() {
        return "<h1>Error 403</h1>";
    }
}

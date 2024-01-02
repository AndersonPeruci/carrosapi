package com.example.carrosapi.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "Bem-vindo API de Carros";
    }

    @GetMapping("/userInfo")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    //Usando RequestParam
    @GetMapping("/buscar")
    public String getCar(@RequestParam("modelo") String modelo, @RequestParam("ano") Integer ano){
        return "Model: " + modelo + " - Year: " + ano;
    }

    //Usando PathVariable
    @GetMapping("/buscar/modelo/{modelo}/ano/{ano}")
    public String getCarPathVariable(@PathVariable("modelo") String modelo, @PathVariable("ano") String ano){
        return "Model: " + modelo + " - Year: " + ano;
    }

    @PostMapping("/buscar")
    public String postCar(@RequestParam("modelo") String modelo, @RequestParam("ano") String ano){
        return "Model: " + modelo + " - Year: " + ano;
    }
}

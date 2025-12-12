package com.construction_app.mvc_cassangolo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EtapaIIController {
    @GetMapping("/etapa-2")
    public String etapa2() {
        return "etapa-2"; // busca etapa-2.html en templates
    }
}

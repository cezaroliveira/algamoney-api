package com.example.algamoney.api.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}

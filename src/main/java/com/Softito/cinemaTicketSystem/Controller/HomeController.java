package com.Softito.cinemaTicketSystem.Controller;


import com.Softito.cinemaTicketSystem.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private final RestTemplate restTemplate;

    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping("/index")
    public String getAllRoles(Model model) {
        List<Role> roles = restTemplate.getForObject("http://localhost:8080/roles", List.class);
        model.addAttribute("roles", roles);
        return "index";
    }
}

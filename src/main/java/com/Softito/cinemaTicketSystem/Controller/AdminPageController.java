package com.Softito.cinemaTicketSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin-panel")
public class AdminPageController {

    @GetMapping("")
    public String adminMain(){
        return "/admin/pages-blank";
    }
}

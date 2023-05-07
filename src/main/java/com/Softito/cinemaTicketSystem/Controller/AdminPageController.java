package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.Saloon;
import com.Softito.cinemaTicketSystem.Services.SaloonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin-panel")
public class AdminPageController {
    @Autowired
    private SaloonService saloonService;
    @GetMapping("")
    public String adminMain(){
        return "/admin/pages-blank";
    }

    @GetMapping("/saloon")
    public String saloonPage(Model model){
        List<Saloon>saloons=saloonService.getAll();
        model.addAttribute("saloon",saloons);
        return "/admin/saloonPage";
    }
    @GetMapping("/add")
    public String addSaloonPage(Model model){
        List<Saloon>saloons=saloonService.getAll();
        model.addAttribute("saloonId",saloons.size()+1);
        return "/admin/saloonAddPage";
    }

    @PostMapping("/addSaloon")
    public String addSaloon(@RequestParam Long capacity,@RequestParam Boolean status){

        Saloon saloon=new Saloon();
        saloon.setCapacity(capacity);
        saloon.setAvailable(status);
        saloonService.create(saloon);
        return "redirect:/admin-panel/saloon";
    }
    @GetMapping("/edit/{id}")
    public String editSaloon(@PathVariable Long id,Model model){
        Saloon saloons=saloonService.getById(id);
        model.addAttribute("saloon",saloons);
        return "/admin/saloonEditPage";
    }
    @PostMapping("/update/{id}")
    public String updateSaloon(@PathVariable Long id, @RequestParam Long capacity,@RequestParam Boolean status ) {
        Saloon saloon=saloonService.getById(id);
        saloon.setAvailable(status);
        saloon.setCapacity(capacity);
        saloonService.update(id,saloon);
        return "redirect:/admin-panel/saloon";
    }
}

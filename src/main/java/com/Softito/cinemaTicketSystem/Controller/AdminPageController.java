package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.*;
import com.Softito.cinemaTicketSystem.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin-panel")
public class AdminPageController {

    private int token = 0, error=0;
    private Admin admin=new Admin();

    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("token", token);
        if (token == 0) return "/admin/admin-login";
        else return "/admin/pages-blank";
    }

    @PostMapping("/loginAdmin")
    public String loginAdmin(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        if (adminService.isEmailExist(email)) {
            admin = adminService.getByEmail(email);
            if (admin.getPassword().equals(password)) {
                admin.setToken("1");
                token = 1;
                adminService.update(admin.getAdminId(),admin);
                return "redirect:/admin-panel";
            } else {
                error=3;
                return "redirect:/admin-panel";
            }
        } else {
            error=2;
            return "redirect:/admin-panel";
        }

    }

    @GetMapping("/isLogged/{id}")
    public String isLogged(@PathVariable Long id,Model model) {
        if (token == 1) {
            return "redirect:/admin-panel/"+id;
        } else {
            return "redirect:/admin-login";
        }
    }


    @GetMapping("/logoutAdmin")
    public String logoutAdmin() {
        admin.setToken("0");
        token = 0;
        admin = new Admin();
        return "redirect:/login";
    }


}

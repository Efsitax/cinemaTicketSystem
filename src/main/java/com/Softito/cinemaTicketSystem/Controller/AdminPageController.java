package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.*;
import com.Softito.cinemaTicketSystem.Services.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin-panel")
public class AdminPageController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private UserService userService;
    @Autowired
    private SaloonService saloonService;
    private int token = 0, error=0;
    private Admin admin=new Admin();

    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("token", token);
        if (token == 0) return "/admin/admin-login";
        else {
            List<Ticket> tickets = ticketService.getAll();
            Long totalEarning = 0L;
            for(Ticket ticket:tickets){
                totalEarning += ticket.getSession().getFilm().getPrice();
            }
            List<Film> films = filmService.getAll();
            List<FilmTotal> filmTotals = new ArrayList<>();
            Long total = 0L;
            for(Film film:films){
                for(Ticket ticket:tickets){
                    if(ticket.getSession().getFilm() == film) total += ticket.getSession().getFilm().getPrice();
                }
                FilmTotal filmTotal = new FilmTotal(film.getName(),total);
                filmTotals.add(filmTotal);
                total=0L;
            }
            List<User> users = userService.getAll();
            List<Saloon> saloons = saloonService.getAll();
            model.addAttribute("userNum",users.size());
            model.addAttribute("saloonNum",users.size());
            model.addAttribute("filmTotals",filmTotals);
            model.addAttribute("totalEarning",totalEarning);
            return "/admin/dashboard";
        }
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
@Getter
@Setter
class FilmTotal{
    private String name;
    private Long total;
    public FilmTotal(String name, Long total){
        this.name=name;
        this.total=total;
    }
}
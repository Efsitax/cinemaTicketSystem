package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.*;

import com.Softito.cinemaTicketSystem.Model.Ticket;
import com.Softito.cinemaTicketSystem.Services.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        if (token == 0) {
            if(error==2){
                String message = "Wrong email.";
                model.addAttribute("emailError", message);
                error=0;
            }
            if(error==3){
                String message = "Wrong password.";
                model.addAttribute("emailError", message);
                error=0;
            }
            return "/admin/admin-login";
        }
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

    @GetMapping("/logoutAdmin")
    public String logoutAdmin() {
        admin.setToken("0");
        token = 0;
        admin = new Admin();
        return "redirect:/admin-panel";
    }
    @GetMapping("/saloon")
    public String saloonPage(Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            List<Saloon> saloons = saloonService.getAll();
            model.addAttribute("saloon", saloons);
            return "/admin/saloonPage";
        }
    }
    @GetMapping("/film")
    public String filmPage(Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            List<Film> films = filmService.getAll();
            model.addAttribute("allFilms", films);
            return "admin/filmPage";
        }
    }
    @GetMapping("/film/edit/{id}")
    public String editFilm(@PathVariable Long id,Model model){
        if (token == 0) return "/admin/admin-login";
        else {
           Film film = filmService.getById(id);
            model.addAttribute("film", film);
            return "/admin/filmEditPage";
        }
    }
    @PostMapping("/film/update/{id}")
    public String updateFilm(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String language,
                             @RequestParam String description,
                             @RequestParam Long duration,
                             @RequestParam Long price,
                             @RequestParam Boolean status,
                             @RequestParam("photo") MultipartFile imageFile ) throws IOException {
        Film film = filmService.getById(id);
        film.setName(name);
        film.setLanguage(language);
        film.setDescription(description);
        film.setDuration(duration);
        film.setPrice(price);
        if(!imageFile.isEmpty()) film.setImage(imageFile.getBytes());
        film.setIsActive(status);
        filmService.update(id, film);
        return "redirect:/admin-panel/film";
    }
    @GetMapping("/film/add")
    public String addFilmPage(Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            List<Film> films = filmService.getAll();
            model.addAttribute("filmId", films.size() + 1);
            return "admin/filmAddPage";
        }
    }
    @PostMapping("/addFilm")
    public String addFilm(@RequestParam String name,
                          @RequestParam String language,
                          @RequestParam String description,
                          @RequestParam Long duration,
                          @RequestParam Long price,
                          @RequestParam Boolean status,
                          @RequestParam("photo") MultipartFile imageFile ) throws IOException {
        Film film = new Film();
        film.setName(name);
        film.setLanguage(language);
        film.setDescription(description);
        film.setDuration(duration);
        film.setPrice(price);
        film.setImage(imageFile.getBytes());
        film.setIsActive(status);
        filmService.create(film);
        return "redirect:/admin-panel/film";
    }
    @GetMapping("/saloon/add")
    public String addSaloonPage(Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            List<Saloon> saloons = saloonService.getAll();
            model.addAttribute("saloonId", saloons.size() + 1);
            return "/admin/saloonAddPage";
        }
    }
    @GetMapping("/saloon/edit/{id}")
    public String editSaloon(@PathVariable Long id,Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            Saloon saloons = saloonService.getById(id);
            model.addAttribute("saloon", saloons);
            return "/admin/saloonEditPage";
        }
    }
    @PostMapping("/addSaloon")
    public String addSaloon(@RequestParam Long capacity,@RequestParam Boolean status){
        Saloon saloon=new Saloon();
        saloon.setCapacity(capacity);
        saloon.setAvailable(status);
        saloonService.create(saloon);
        return "redirect:/admin-panel/saloon";
    }

    @PostMapping("/saloon/update/{id}")
    public String updateSaloon(@PathVariable Long id, @RequestParam Long capacity,@RequestParam Boolean status ) {
        Saloon saloon=saloonService.getById(id);
        saloon.setAvailable(status);
        saloon.setCapacity(capacity);
        saloonService.update(id,saloon);
        return "redirect:/admin-panel/saloon";
    }

    @GetMapping("/user")
    public String userPage(Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            List<User> users = userService.getAll();
            model.addAttribute("user", users);
            return "/admin/userPage";
        }
    }
    @GetMapping("/user/add")
    public String addUserPage(Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            List<User> users = userService.getAll();
            model.addAttribute("userId", users.size() + 100000);
            return "admin/userAddPage";
        }
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String name,
                          @RequestParam String surname,
                          @RequestParam String email,
                          @RequestParam Long balance,
                          @RequestParam String password,
                          @RequestParam String isActive){
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBalance(balance);
        user.setPassword(password);
        user.setIsActive(Boolean.valueOf(isActive));
        LocalDate currentDate = LocalDate.now();

        // Format the date in "yyyy-MM-dd" format
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Convert the formatted date to a java.util.Date object
        Date date = java.sql.Date.valueOf(formattedDate);

        user.setCreatedAt(date);
        userService.create(user);
        return "redirect:/admin-panel/user";
    }
    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id,Model model){
        if (token == 0) return "/admin/admin-login";
        else {
            User users = userService.getById(id);
            model.addAttribute("user", users);
            return "/admin/userEditPage";
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam Long balance,
                             @RequestParam Boolean isActive,
                             @RequestParam String createdAt) {
        User user = userService.getById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        user.setEmail(email);
        user.setBalance(balance);
        user.setIsActive(isActive);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = dateFormat.parse(createdAt);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            user.setCreatedAt(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userService.update(id, user);
        return "redirect:/admin-panel/user";
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
package com.Softito.cinemaTicketSystem.Controller;


import com.Softito.cinemaTicketSystem.Model.Film;
import com.Softito.cinemaTicketSystem.Model.Role;
import com.Softito.cinemaTicketSystem.Model.Session;
import com.Softito.cinemaTicketSystem.Model.User;
import com.Softito.cinemaTicketSystem.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SaloonService saloonService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private FilmService filmService;

    private int token = 0;
    private User user;


    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/filmsPage")
    public String getAllFilms(Model model) {
        List<Film> films = restTemplate.getForObject("http://localhost:8080/films", List.class);
        model.addAttribute("films", films);
        model.addAttribute("token", token);
        return "filmsPage";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("token", token);
        return "registerPage";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("token", token);
        return "loginPage";
    }

    @GetMapping("/isLogged")
    public String isLogged(Model model) {
        if (token==1) {
            Long capacity = saloonService.getCapacity(1L);
            model.addAttribute("capacity",capacity);
            return "satis";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/session/{id}")
    public String sessions(@PathVariable Long id, Model model) {

        Film films = filmService.getById(id);
        model.addAttribute("films",films);
        model.addAttribute("token", token);
        List<Session> sessions = sessionService.getAll();
        model.addAttribute("sessions",sessions);
        return "sessions";
    }
    @PostMapping("/saveUser")
    public String saveUser(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        if (!userService.isEmailExist(email)) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setBalance(100L);
            newUser.setIsActive(true);
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Format the date in "yyyy-MM-dd" format
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Convert the formatted date to a java.util.Date object
            Date date = java.sql.Date.valueOf(formattedDate);

            newUser.setCreated_at(date);
            newUser.setRole(new Role(2L));


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> entity = new HttpEntity<>(newUser, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8080/users/add",
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            return "redirect:/login";
        } else {
            model.addAttribute("emailError","Bu email zaten var");
            return "redirect:/register";
        }
    }

    @PostMapping("/loginUser")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        if (userService.isEmailExist(email)) {
            user = userService.getByEmail(email);
            if (user.getPassword().equals(password)) {
                user.setToken("1");
                token = 1;
                userService.update(user.getUserId(), user);
                return "redirect:/filmsPage";
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping("/logoutUser")
    public String logoutUser() {
        user.setToken("0");
        token = 0;
        userService.update(user.getUserId(), user);
        return "redirect:/filmsPage";
    }
}

package com.Softito.cinemaTicketSystem.Controller;


import com.Softito.cinemaTicketSystem.Model.*;
import com.Softito.cinemaTicketSystem.Repository.UserRepository;
import com.Softito.cinemaTicketSystem.Services.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    private int token = 0, hata=0;
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
    @GetMapping("/filmsPage/{id}")
    public String getAllFilms(@PathVariable Long id,Model model) {
        List<Film> films = restTemplate.getForObject("http://localhost:8080/films", List.class);

        model.addAttribute("films", films);
        model.addAttribute("token", token);

        model.addAttribute("id",id);
        User users=userService.getById(id);
        model.addAttribute("users",users);

        return "filmsPage";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("token", token);
        if(hata==1){
            String mesaj = "Bu email zaten var";
            model.addAttribute("emailError", mesaj);
            hata=0;
        }
        return "registerPage";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("token", token);
        if(hata==2){
            String mesaj = "Email hatali.";
            model.addAttribute("emailError", mesaj);
            hata=0;
        }
        if(hata==3){
            String mesaj = "Sifre hatali.";
            model.addAttribute("emailError", mesaj);
            hata=0;
        }
        return "loginPage";
    }

    @GetMapping("/credit/{id}")
    public String credit(@PathVariable Long id,Model model) {
        User users=userService.getById(id);
        model.addAttribute("users",users);
        return "credit";
    }



    @GetMapping("/isLogged/{id}")
    public String isLogged(@PathVariable Long id,Model model) {
        if (token == 1) {
            Session session = restTemplate.getForObject("http://localhost:8080/sessions/"+id, Session.class );
            List<Long> seatNums = restTemplate.getForObject("http://localhost:8080/tickets/seatnums/"+id, List.class);
            model.addAttribute("balance",user.getBalance());
            model.addAttribute("ses", session);
            model.addAttribute("seats",seatNums);
            return "satis";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/session/{id}")
    public String sessions(@PathVariable Long id, Model model) {
        Film film = restTemplate.getForObject("http://localhost:8080/films/"+id, Film.class );
        model.addAttribute("film", film);

        byte [] img=film.getImage();
        String base64Data = Base64.getEncoder().encodeToString(img);
        model.addAttribute("img",base64Data);
        model.addAttribute("token", token);

        List<Session> allSessions = restTemplate.exchange(
                "http://localhost:8080/sessions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Session>>() {}).getBody();
        List<Session> sessions = allSessions.stream().filter(s -> s.getFilm().getFilmId() == id)
                .collect(Collectors.toList());;

        model.addAttribute("reqSessions", sessions);

        return "sessions";
    }


    @PostMapping("/addBalance/{id}")
    public String addBalance(@PathVariable Long id,@RequestParam Long para){
        User users2=userService.getById(id);

        users2.setBalance(users2.getBalance()+para);
        userService.update(id,users2);

        return "redirect:/filmsPage/"+id;
    }

    @PostMapping("/saveUser")
    public String saveUser(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        Boolean isExist = restTemplate.getForObject("http://localhost:8080/users/isemailexist?email="+email, Boolean.class);
        if (!isExist) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setBalance(0L);
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
            hata=1;
            return "redirect:/register";
        }
    }

    @PostMapping("/loginUser")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        Boolean isExist = restTemplate.getForObject("http://localhost:8080/users/isemailexist?email="+email, Boolean.class);
        if (isExist) {
            user = restTemplate.getForObject("http://localhost:8080/users/email?email="+email, User.class);
            if (user.getPassword().equals(password)) {
                user.setToken("1");
                token = 1;
                restTemplate.put("http://localhost:8080/users/update/" + user.getUserId(), user);
                return "redirect:/filmsPage/"+user.getUserId();
            } else {
                hata=3;
                return "redirect:/login";
            }
        } else {
            hata=2;
            return "redirect:/login";
        }

    }

    @GetMapping("/logoutUser")
    public String logoutUser() {
        user.setToken("0");
        token = 0;


        return "redirect:/filmsPage";
    }
    @PostMapping("/buyticket")
    @ResponseBody
    public String buyTicket(@RequestBody Map<String, Object> requestData, Model model){
        List<String> seatNumsstr = (List<String>) requestData.get("seatNums");
        List<Integer> seatNums = new ArrayList<>();
        for(String str:seatNumsstr){
            Integer seat = Integer.valueOf(str);
            seatNums.add(seat);
        }
        Integer price = (Integer) requestData.get("price");
        Integer balance = (Integer) requestData.get("balance");
        Integer sessionId = (Integer) requestData.get("sessionId");
        Integer total = price*(seatNums.size());
        if(total>balance){
            return "Bakiye Yetersiz.";
        }
        else{
            balance-=total;
            Long balLg = balance.longValue();
            user.setBalance(balLg);
            restTemplate.put("http://localhost:8080/users/update/" + user.getUserId(), user);

            Long sesLg = sessionId.longValue();
            for (Integer seatNum: seatNums) {
                Long seatLg = seatNum.longValue();
                Ticket newTicket = new Ticket();

                newTicket.setUser(new User());
                newTicket.getUser().setUserId(user.getUserId());
                newTicket.setSession(new Session());
                newTicket.getSession().setSessionId(sesLg);
                newTicket.setSeatNum(seatLg);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Ticket> entity = new HttpEntity<>(newTicket, headers);
                ResponseEntity<String> response = restTemplate.exchange(
                        "http://localhost:8080/tickets/add",
                        HttpMethod.POST,
                        entity,
                        String.class
                );
            }
            return "Satin alim basarili.";
        }
    }
}

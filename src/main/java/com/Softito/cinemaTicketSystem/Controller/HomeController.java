package com.Softito.cinemaTicketSystem.Controller;


import com.Softito.cinemaTicketSystem.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private final RestTemplate restTemplate;
    private int token = 0, error=0;
    private User user=new User();

    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Film> films = restTemplate.getForObject("http://localhost:8080/films", List.class);
        model.addAttribute("token", token);
        model.addAttribute("films", films);
        model.addAttribute("balance",user.getBalance());
        return "homePage";
    }

    @GetMapping("/filmsPage")
    public String getAllFilms(Model model) {
        List<Film> films = restTemplate.getForObject("http://localhost:8080/films", List.class);
        model.addAttribute("films", films);
        model.addAttribute("token", token);
        model.addAttribute("balance",user.getBalance());
        return "filmsPage";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("token", token);
        if(error==1){
            String message = "This email is already exist.";
            model.addAttribute("emailError", message);
            error=0;
        }
        return "registerPage";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("token", token);
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
        return "loginPage";
    }

    @GetMapping("/credit")
    public String credit(Model model) {
        model.addAttribute("users",user);
        return "credit";
    }

    @GetMapping("/orderPage/{id}")
    public String orderPage(@PathVariable Long id,Model model){
        Session session = restTemplate.getForObject("http://localhost:8080/sessions/"+id, Session.class );
        List<Long> seatNums = restTemplate.getForObject("http://localhost:8080/tickets/seat-nums/"+id, List.class);
        model.addAttribute("balance",user.getBalance());
        model.addAttribute("token", token);
        model.addAttribute("ses", session);
        model.addAttribute("seats",seatNums);
        return "orderPage";
    }

    @GetMapping("/isLogged/{id}")
    public String isLogged(@PathVariable Long id,Model model) {
        if (token == 1) {
            return "redirect:/orderPage/"+id;
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
        model.addAttribute("balance",user.getBalance());
        List<Session> allSessions = restTemplate.exchange(
                "http://localhost:8080/sessions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Session>>() {}).getBody();
        List<Session> sessions = allSessions.stream().filter(s -> s.getFilm().getFilmId() == id)
                .collect(Collectors.toList());;

        model.addAttribute("reqSessions", sessions);

        return "sessionsPage";
    }


    @PostMapping("/addBalance")
    public String addBalance(@RequestParam Long para){
        user.setBalance(user.getBalance()+para);
        restTemplate.put("http://localhost:8080/users/update/" + user.getUserId(), user);
        return "redirect:/filmsPage";
    }

    @PostMapping("/saveUser")
    public String saveUser(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        Boolean isExist = restTemplate.getForObject("http://localhost:8080/users/is-email-exist?email="+email, Boolean.class);
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

            newUser.setCreatedAt(date);
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
            error=1;
            return "redirect:/register";
        }
    }

    @PostMapping("/loginUser")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        Boolean isExist = restTemplate.getForObject("http://localhost:8080/users/is-email-exist?email="+email, Boolean.class);
        if (isExist) {
            user = restTemplate.getForObject("http://localhost:8080/users/email?email="+email, User.class);
            if (user.getPassword().equals(password)) {
                user.setToken("1");
                token = 1;
                restTemplate.put("http://localhost:8080/users/update/" + user.getUserId(), user);
                return "redirect:/filmsPage";
            } else {
                error=3;
                return "redirect:/login";
            }
        } else {
            error=2;
            return "redirect:/login";
        }

    }

    @GetMapping("/logoutUser")
    public String logoutUser() {
        user.setToken("0");
        token = 0;
        restTemplate.put("http://localhost:8080/users/update/" + user.getUserId(), user);
        user = new User();

        return "redirect:/home";
    }
    @PostMapping("/buyticket")
    @ResponseBody
    public String buyTicket(@RequestBody Map<String, Object> requestData, Model model){
        List<String> seatNumsStr = (List<String>) requestData.get("seatNums");
        List<Integer> seatNums = new ArrayList<>();
        for(String str:seatNumsStr){
            Integer seat = Integer.valueOf(str);
            seatNums.add(seat);
        }
        Integer price = (Integer) requestData.get("price");
        Integer balance = (Integer) requestData.get("balance");
        Integer sessionId = (Integer) requestData.get("sessionId");
        Integer total = price*(seatNums.size());
        if(total>balance){
            return "Insufficient balance.";
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
            return "Purchase successful.";
        }
    }
}

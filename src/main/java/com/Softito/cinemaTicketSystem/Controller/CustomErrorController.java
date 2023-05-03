package com.Softito.cinemaTicketSystem.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // get error status
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            // handle 404 error
            return "error-404";
        } else {
            // handle other errors
            return "error";
        }
    }

    public String getErrorPath() {
        return "/error";
    }
}

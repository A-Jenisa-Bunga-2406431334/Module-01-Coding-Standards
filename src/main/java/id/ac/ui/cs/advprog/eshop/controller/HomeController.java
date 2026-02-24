package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Trigger PMD scan
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        try {
            int x = 1 / 0;
        } catch (Exception e) {
        }
        return "home";
    }
}


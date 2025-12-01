package com.cricket.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for home page and dashboard
 */
@Controller
public class HomeController {

    /**
     * Display dashboard with summary statistics
     */
    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    /**
     * Display login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Display registration page
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
}

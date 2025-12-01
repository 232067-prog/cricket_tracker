package com.cricket.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for match-related operations
 */
@Controller
@RequestMapping("/matches")
public class MatchController {

    /**
     * Display add match form
     */
    @GetMapping("/add")
    public String showAddForm() {
        return "add-match";
    }

    /**
     * Display match history
     */
    @GetMapping("/history")
    public String showHistory() {
        return "match-history";
    }

    /**
     * Display edit match form
     */
    @GetMapping("/edit")
    public String showEditForm() {
        return "edit-match";
    }

    /**
     * Display statistics page
     */
    @GetMapping("/statistics")
    public String showStatistics() {
        return "statistics";
    }
}

package com.cricket.tracker.controller;

import com.cricket.tracker.model.PlayerStatistics;
import com.cricket.tracker.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for home page and dashboard
 */
@Controller
public class HomeController {

    @Autowired
    private MatchService matchService;

    /**
     * Display dashboard with summary statistics
     */
    @GetMapping("/")
    public String dashboard(Model model) {
        PlayerStatistics stats = matchService.calculatePlayerStatistics();
        model.addAttribute("stats", stats);
        model.addAttribute("recentMatches", matchService.getRecentMatches(5));
        return "dashboard";
    }
}

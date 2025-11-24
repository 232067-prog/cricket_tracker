package com.cricket.tracker.controller;

import com.cricket.tracker.model.Match;
import com.cricket.tracker.model.PlayerStatistics;
import com.cricket.tracker.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controller for match-related operations
 */
@Controller
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    /**
     * Display add match form
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("match", new Match());
        return "add-match";
    }

    /**
     * Process match submission
     */
    @PostMapping("/add")
    public String addMatch(@Valid @ModelAttribute("match") Match match,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "add-match";
        }

        try {
            matchService.addMatch(match);
            redirectAttributes.addFlashAttribute("successMessage",
                    "✅ Match added successfully!");
            return "redirect:/matches/history";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "❌ " + e.getMessage());
            return "redirect:/matches/add";
        }
    }

    /**
     * Display match history
     */
    @GetMapping("/history")
    public String showHistory(Model model) {
        model.addAttribute("matches", matchService.getAllMatches());
        return "match-history";
    }

    /**
     * Display edit match form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Match> match = matchService.getMatchById(id);

        if (match.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "❌ Match not found!");
            return "redirect:/matches/history";
        }

        model.addAttribute("match", match.get());
        return "edit-match";
    }

    /**
     * Process match update
     */
    @PostMapping("/edit/{id}")
    public String updateMatch(@PathVariable Long id,
            @Valid @ModelAttribute("match") Match match,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "edit-match";
        }

        try {
            match.setId(id);
            matchService.updateMatch(match);
            redirectAttributes.addFlashAttribute("successMessage",
                    "✅ Match updated successfully!");
            return "redirect:/matches/history";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "❌ " + e.getMessage());
            return "redirect:/matches/edit/" + id;
        }
    }

    /**
     * Delete match
     */
    @PostMapping("/delete/{id}")
    public String deleteMatch(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = matchService.deleteMatch(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "✅ Match deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "❌ Failed to delete match!");
        }

        return "redirect:/matches/history";
    }

    /**
     * Display statistics page
     */
    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        PlayerStatistics stats = matchService.calculatePlayerStatistics();
        model.addAttribute("stats", stats);
        model.addAttribute("matches", matchService.getAllMatches());
        return "statistics";
    }
}

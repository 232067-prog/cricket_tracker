package com.cricket.tracker.service;

import com.cricket.tracker.model.Match;
import com.cricket.tracker.model.PlayerStatistics;
import com.cricket.tracker.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for match-related business logic
 */
@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    /**
     * Add a new match
     */
    public Match addMatch(Match match) throws IllegalArgumentException {
        // Check for duplicate
        if (matchRepository.existsByDateAndOpponent(
                match.getMatchDate(),
                match.getOpponentName(),
                null)) {
            throw new IllegalArgumentException(
                    "A match against " + match.getOpponentName() +
                            " on " + match.getMatchDate() + " already exists!");
        }

        // Calculate statistics
        match.calculateAllStats();

        // Save match
        return matchRepository.save(match);
    }

    /**
     * Update an existing match
     */
    public Match updateMatch(Match match) throws IllegalArgumentException {
        if (match.getId() == null) {
            throw new IllegalArgumentException("Match ID is required for update");
        }

        // Check if match exists
        Optional<Match> existing = matchRepository.findById(match.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Match not found with ID: " + match.getId());
        }

        // Check for duplicate (excluding current match)
        if (matchRepository.existsByDateAndOpponent(
                match.getMatchDate(),
                match.getOpponentName(),
                match.getId())) {
            throw new IllegalArgumentException(
                    "Another match against " + match.getOpponentName() +
                            " on " + match.getMatchDate() + " already exists!");
        }

        // Calculate statistics
        match.calculateAllStats();

        // Update match
        return matchRepository.save(match);
    }

    /**
     * Delete a match by ID
     */
    public boolean deleteMatch(Long id) {
        return matchRepository.deleteById(id);
    }

    /**
     * Get all matches
     */
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    /**
     * Get match by ID
     */
    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    /**
     * Get recent matches (last N matches)
     */
    public List<Match> getRecentMatches(int limit) {
        List<Match> allMatches = matchRepository.findAll();
        return allMatches.stream()
                .limit(limit)
                .toList();
    }

    /**
     * Calculate overall player statistics
     */
    public PlayerStatistics calculatePlayerStatistics() {
        List<Match> allMatches = matchRepository.findAll();
        PlayerStatistics stats = new PlayerStatistics();

        if (allMatches.isEmpty()) {
            return stats;
        }

        // Initialize totals
        int totalRuns = 0;
        int totalBallsFaced = 0;
        int totalWickets = 0;
        double totalOversBowled = 0.0;
        int totalRunsConceded = 0;
        int totalCatches = 0;

        int highestScore = 0;
        int bestBowlingWickets = 0;
        int mostCatches = 0;

        // Aggregate statistics
        for (Match match : allMatches) {
            totalRuns += match.getRunsScored();
            totalBallsFaced += match.getBallsFaced();
            totalWickets += match.getWicketsTaken();
            totalOversBowled += match.getOversBowled();
            totalRunsConceded += match.getRunsConceded();
            totalCatches += match.getCatches();

            // Track best performances
            if (match.getRunsScored() > highestScore) {
                highestScore = match.getRunsScored();
            }
            if (match.getWicketsTaken() > bestBowlingWickets) {
                bestBowlingWickets = match.getWicketsTaken();
            }
            if (match.getCatches() > mostCatches) {
                mostCatches = match.getCatches();
            }
        }

        // Set values
        stats.setTotalMatches(allMatches.size());
        stats.setTotalRuns(totalRuns);
        stats.setTotalBallsFaced(totalBallsFaced);
        stats.setTotalWickets(totalWickets);
        stats.setTotalOversBowled(totalOversBowled);
        stats.setTotalRunsConceded(totalRunsConceded);
        stats.setTotalCatches(totalCatches);
        stats.setHighestScore(highestScore);
        stats.setBestBowlingWickets(bestBowlingWickets);
        stats.setMostCatches(mostCatches);

        // Calculate overall statistics
        stats.calculateOverallStats();

        return stats;
    }

    /**
     * Get total match count
     */
    public long getTotalMatchCount() {
        return matchRepository.count();
    }
}

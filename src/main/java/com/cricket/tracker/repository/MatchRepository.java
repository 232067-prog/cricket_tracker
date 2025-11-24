package com.cricket.tracker.repository;

import com.cricket.tracker.model.Match;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory repository for Match entities
 * Uses ArrayList for storage (data will be lost on application restart)
 */
@Repository
public class MatchRepository {

    private final List<Match> matches = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Save a new match
     */
    public synchronized Match save(Match match) {
        if (match.getId() == null) {
            match.setId(idGenerator.getAndIncrement());
            matches.add(match);
        } else {
            // Update existing match
            int index = findIndexById(match.getId());
            if (index >= 0) {
                matches.set(index, match);
            }
        }
        return match;
    }

    /**
     * Find all matches sorted by date (newest first)
     */
    public synchronized List<Match> findAll() {
        return matches.stream()
                .sorted(Comparator.comparing(Match::getMatchDate).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find match by ID
     */
    public synchronized Optional<Match> findById(Long id) {
        return matches.stream()
                .filter(match -> match.getId().equals(id))
                .findFirst();
    }

    /**
     * Delete match by ID
     */
    public synchronized boolean deleteById(Long id) {
        return matches.removeIf(match -> match.getId().equals(id));
    }

    /**
     * Check if a match exists with the same date and opponent
     */
    public synchronized boolean existsByDateAndOpponent(LocalDate date, String opponent, Long excludeId) {
        return matches.stream()
                .filter(match -> !match.getId().equals(excludeId))
                .anyMatch(match -> match.getMatchDate().equals(date) &&
                        match.getOpponentName().equalsIgnoreCase(opponent.trim()));
    }

    /**
     * Get total count of matches
     */
    public synchronized long count() {
        return matches.size();
    }

    /**
     * Find index of match by ID
     */
    private int findIndexById(Long id) {
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Clear all matches (useful for testing)
     */
    public synchronized void deleteAll() {
        matches.clear();
        idGenerator.set(1);
    }
}

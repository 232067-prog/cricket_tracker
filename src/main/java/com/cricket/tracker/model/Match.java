package com.cricket.tracker.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Domain model representing a cricket match performance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    private Long id;

    @NotNull(message = "Match date is required")
    @PastOrPresent(message = "Match date cannot be in the future")
    private LocalDate matchDate;

    @NotBlank(message = "Opponent name is required")
    @Size(min = 2, max = 100, message = "Opponent name must be between 2 and 100 characters")
    private String opponentName;

    // Batting Statistics
    @Min(value = 0, message = "Runs scored cannot be negative")
    private Integer runsScored = 0;

    @Min(value = 0, message = "Balls faced cannot be negative")
    private Integer ballsFaced = 0;

    // Bowling Statistics
    @Min(value = 0, message = "Wickets taken cannot be negative")
    private Integer wicketsTaken = 0;

    @DecimalMin(value = "0.0", message = "Overs bowled cannot be negative")
    private Double oversBowled = 0.0;

    @Min(value = 0, message = "Runs conceded cannot be negative")
    private Integer runsConceded = 0;

    // Fielding Statistics
    @Min(value = 0, message = "Catches cannot be negative")
    private Integer catches = 0;

    // Calculated Statistics (not input by user)
    private Double battingAverage;
    private Double strikeRate;
    private Double bowlingAverage;
    private Double economyRate;

    /**
     * Calculate batting statistics
     */
    public void calculateBattingStats() {
        if (ballsFaced != null && ballsFaced > 0) {
            this.strikeRate = (runsScored * 100.0) / ballsFaced;
        } else {
            this.strikeRate = 0.0;
        }

        // Batting average is typically calculated across multiple innings
        // For a single match, we'll set it to runs scored
        this.battingAverage = runsScored != null ? runsScored.doubleValue() : 0.0;
    }

    /**
     * Calculate bowling statistics
     */
    public void calculateBowlingStats() {
        if (wicketsTaken != null && wicketsTaken > 0) {
            this.bowlingAverage = runsConceded.doubleValue() / wicketsTaken;
        } else {
            this.bowlingAverage = 0.0;
        }

        if (oversBowled != null && oversBowled > 0) {
            this.economyRate = runsConceded / oversBowled;
        } else {
            this.economyRate = 0.0;
        }
    }

    /**
     * Calculate all statistics
     */
    public void calculateAllStats() {
        calculateBattingStats();
        calculateBowlingStats();
    }
}

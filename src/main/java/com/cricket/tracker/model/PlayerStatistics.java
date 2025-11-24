package com.cricket.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for aggregated player statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatistics {

    // Aggregate Counts
    private int totalMatches;
    private int totalRuns;
    private int totalBallsFaced;
    private int totalWickets;
    private double totalOversBowled;
    private int totalRunsConceded;
    private int totalCatches;

    // Calculated Averages
    private double overallBattingAverage;
    private double overallStrikeRate;
    private double overallBowlingAverage;
    private double overallEconomyRate;

    // Best Performances
    private int highestScore;
    private int bestBowlingWickets;
    private int mostCatches;

    /**
     * Calculate overall statistics from totals
     */
    public void calculateOverallStats() {
        // Overall Batting Average = Total Runs / Total Matches
        if (totalMatches > 0) {
            this.overallBattingAverage = (double) totalRuns / totalMatches;
        } else {
            this.overallBattingAverage = 0.0;
        }

        // Overall Strike Rate = (Total Runs / Total Balls Faced) * 100
        if (totalBallsFaced > 0) {
            this.overallStrikeRate = (totalRuns * 100.0) / totalBallsFaced;
        } else {
            this.overallStrikeRate = 0.0;
        }

        // Overall Bowling Average = Total Runs Conceded / Total Wickets
        if (totalWickets > 0) {
            this.overallBowlingAverage = (double) totalRunsConceded / totalWickets;
        } else {
            this.overallBowlingAverage = 0.0;
        }

        // Overall Economy Rate = Total Runs Conceded / Total Overs Bowled
        if (totalOversBowled > 0) {
            this.overallEconomyRate = totalRunsConceded / totalOversBowled;
        } else {
            this.overallEconomyRate = 0.0;
        }
    }
}

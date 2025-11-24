package com.cricket.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application for Cricket Tracker
 * A performance tracking system for local cricket players
 */
@SpringBootApplication
public class CricketTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CricketTrackerApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("Cricket Tracker Application Started!");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("==============================================\n");
    }
}

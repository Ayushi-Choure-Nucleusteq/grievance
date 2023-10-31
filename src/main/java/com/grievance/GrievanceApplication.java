package com.grievance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Application.
 * This class is responsible for starting the Spring Boot application
 * and initializing beans that will be used in the application.
 */
@SpringBootApplication
public class GrievanceApplication {
    
    /**
     * Default Constructor.
     */
    protected GrievanceApplication() {
    }

    /**
     * The main method that is entry point for the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(GrievanceApplication.class, args);
    }
    
}

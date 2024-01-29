package com.gleb.vinnikov.venue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class VenueApplication {

    public static void main(String[] args) {
        SpringApplication.run(VenueApplication.class, args);
    }

}

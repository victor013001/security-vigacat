package com.vigacat.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration

public class VigacatApplication {

    public static void main(String... args) {
        SpringApplication.run(VigacatApplication.class, args);
    }



}

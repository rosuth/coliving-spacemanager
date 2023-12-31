package com.colivingspacemanager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class ColivingSpaceManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColivingSpaceManagerApplication.class, args);
    }

}

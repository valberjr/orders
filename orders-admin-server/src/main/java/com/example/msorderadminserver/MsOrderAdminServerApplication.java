package com.example.msorderadminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MsOrderAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsOrderAdminServerApplication.class, args);
    }

}

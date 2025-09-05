package com.book_micro.identity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DevteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevteriaApplication.class, args);
    }
}

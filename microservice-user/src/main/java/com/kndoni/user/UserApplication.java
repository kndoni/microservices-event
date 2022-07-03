package com.kndoni.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
//        scanBasePackages = {
//                "com.kndoni.user",
//                "com.kndoni.amqp",
//        }
)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}

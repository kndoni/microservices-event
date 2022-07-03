package com.kndoni.event;

import com.kndoni.event.model.Event;
import com.kndoni.event.repo.EventRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@EnableFeignClients
@SpringBootApplication()

public class EventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    @Bean
    CommandLineRunner run(EventRepo itemRepo) {
        return args -> {
            itemRepo.save(new Event(null,"Automotive Festival","Sports","Yearly festival of cars is officially open.", LocalDate.now()));
            itemRepo.save(new Event(null,"Last looks","Arts & Movies","Now in cinema", LocalDate.now()));
            itemRepo.save(new Event(null,"Kala Festival","Music","Music festival now in south.", LocalDate.parse("2021-05-08")));
        };
    }
}

package com.kndoni.event.repo;

import com.kndoni.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {
    Event findByName(String name);

}

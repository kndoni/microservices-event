package com.kndoni.event.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kndoni.event.model.Event;
import com.kndoni.event.model.Transaction;

import java.util.Collection;
import java.util.List;

public interface EventService {

    List<Event> allEvents();
    Event create(Event event);
    Collection<Event> list(int limit);
    Event get(Long id);
    Event update(Event event);
    Boolean delete(Long id);
    List<Transaction> findTransactionsOfUsers(Long userId) throws JsonProcessingException;
    List<Transaction> findTransactionsOfItem(Long eventId);
    Transaction saveTransaction(Transaction transaction);
}

package com.kndoni.event.amqp.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kndoni.amqp.MessagingConfig;
import com.kndoni.event.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserConsumer {

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = MessagingConfig.QUEUE1)
    public void consumeUser(String message) throws JsonProcessingException {
        User user = mapper.readValue(message, User.class);
        log.info("Vlerat e user: "+ user);
    }
}

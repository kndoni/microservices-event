package com.kndoni.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kndoni.event.model.Event;
import com.kndoni.event.model.Response;
import com.kndoni.event.model.Transaction;
import com.kndoni.event.service.implemetation.EventServiceImpl;
import com.kndoni.event.usercommunication.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@CrossOrigin(origins ="*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class EventController {
    @Autowired
    private final EventServiceImpl eventService;
    @Autowired
    private final UserClient userClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/list")
    public ResponseEntity<Response> getItems() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        //throw new InterruptedException("Something went wrong");
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("events", eventService.list(30)))
                        .message("Events retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveItem(@RequestBody @Valid Event event)  {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("events", eventService.create(event)))
                        .message("Events created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getItem(@PathVariable("id") Long id)   {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("events", eventService.get(id)))
                        .message("Events retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteItem(@PathVariable("id") Long id)   {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", eventService.delete(id)))
                        .message("Event deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/service/enroll")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) throws JsonProcessingException {
        transaction.setDateOfIssue(LocalDate.now());
        transaction.setEvent(eventService.get(transaction.getEvent().getId()));
//        List<Transaction> transactions = eventService.findTransactionsOfItem(eventId);
//        List<Long> userIdList = transactions.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        Long userEnrolledId = transaction.getUserId();
//        log.info("Tipi i userIdList: "+ userIdList.getClass());
//        log.info("Content of user id List: "+ userIdList);
//        String message = mapper.writeValueAsString(userEnrolledId);
//        log.info("Sending message: "+message);
//        rabbitTemplate.convertAndSend("internal.exchange", "internal.transaction.routing-key", message);
        return new ResponseEntity<>(eventService.saveTransaction(transaction), CREATED);
    }

    @GetMapping("/service/user/{userId}")
    public ResponseEntity<?> findTransactionsOfUser(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.findTransactionsOfUsers(userId));
    }


    @GetMapping("/service/event/{eventId}")
    public ResponseEntity<?> findUsersOfEvent(@PathVariable Long eventId) {
        List<Transaction> transactions = eventService.findTransactionsOfItem(eventId);
        if(CollectionUtils.isEmpty(transactions)) {
            return ResponseEntity.notFound().build();
        }
        List<Long> userIdList = transactions.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> users = userClient.getUserNames(userIdList);
        return ResponseEntity.ok(users);
    }

//    @RabbitListener(queues = MessagingConfig.QUEUE)
//    public void consumeMessages(String username) throws JsonProcessingException {
//        System.out.println("Message received from queue : " + username);
//        usernames = mapper.readValue(username, List.class);
//        System.out.println("Message received from queue after : " + usernames);
//
//    }


    @GetMapping("/service/all")
    public ResponseEntity<?> findAllEvents(){
        return ResponseEntity.ok(eventService.allEvents());
    }
}

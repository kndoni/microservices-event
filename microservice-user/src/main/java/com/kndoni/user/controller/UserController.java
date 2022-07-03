package com.kndoni.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kndoni.user.model.Role;
import com.kndoni.user.model.User;
import com.kndoni.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins ="*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws JsonProcessingException {
        if (userService.findByUsername(user.getUsername()) != null) {
            //Status CODE: 409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        System.out.println("Request body: " + user.getName());
        //Default role user
        user.setRole(Role.USER);
        userService.save(user);
        String message = mapper.writeValueAsString(user);
        log.info("Sending message: "+message);
        rabbitTemplate.convertAndSend("internal.exchange", "internal.event.routing-key", message);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(Principal principal) {
        //Principal principal = request.getUserPrincipal();
        if (principal == null || principal.getName() == null) {
            //logout will be successful
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

//    @RabbitListener(queues = MessagingConfig.QUEUE2)
//    public void getNamesOfUsers(String idList) throws JsonProcessingException {
//        log.info("IDLIST: "+ idList.getClass());
//        List<String> idUser= mapper.readValue(idList, List.class);
//        log.info("IDUSER : "+idUser);
//        log.info("IDUSER : "+idUser.getClass());
//
//    }
    @PostMapping("/service/names")
    public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList) throws JsonProcessingException {
        List<String> listOfNamesOfUsers = userService.findUsers(idList);
//        String message = mapper.writeValueAsString(listOfNamesOfUsers);
//        rabbitTemplate.convertAndSend("internal.exchange", "internal.event.routing-key", message);
        return ResponseEntity.ok(listOfNamesOfUsers);
    }

    @GetMapping("/service/health")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("its working");
    }
}

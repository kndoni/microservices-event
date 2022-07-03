package com.kndoni.event.usercommunication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//@FeignClient(name = "user-service", url = "http://user-service:8081")
@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST, value ="/api/user/service/names", consumes = "application/json")
    List<String> getUserNames(@RequestBody List<Long> userIdList);

}

package com.kndoni.user.service;

import com.kndoni.user.model.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findByUsername(String username);

    List<String> findUsers(List<Long> idList);

}

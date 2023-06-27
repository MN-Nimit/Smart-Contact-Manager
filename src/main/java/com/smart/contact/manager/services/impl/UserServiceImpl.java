package com.smart.contact.manager.services.impl;

import com.smart.contact.manager.entity.User;
import com.smart.contact.manager.repository.UserRepository;
import com.smart.contact.manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}

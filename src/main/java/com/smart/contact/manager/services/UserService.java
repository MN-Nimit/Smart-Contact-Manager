package com.smart.contact.manager.services;

import com.smart.contact.manager.entity.User;
import com.smart.contact.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    public User saveUser(User user);
}

package com.smart.contact.manager.controller;

import com.smart.contact.manager.entity.Contact;
import com.smart.contact.manager.entity.User;
import com.smart.contact.manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void commonData(Model model, Principal principal){

        String userName = principal.getName();
        System.out.println(userName);

        User user = userService.getUserByUserName(userName);
        System.out.println(user);

        model.addAttribute("user", user);
    }

    @GetMapping("/index")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("title", "User Dashboard");
        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model){

        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "normal/add_contact";
    }

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile multipartFile, Principal principal){

        try {
            String name = principal.getName();
            User user = userService.getUserByUserName(name);

            contact.setUser(user);
            user.getContacts().add(contact);
            userService.saveUser(user);

            System.out.println("Data : " + contact);
            System.out.println("Added to database");

            return "normal/add_contact";
        } catch (Exception e){
            e.printStackTrace();
            return "normal/add_contact";
        }
    }
}

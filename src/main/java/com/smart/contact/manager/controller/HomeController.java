package com.smart.contact.manager.controller;

import com.smart.contact.manager.entity.User;
import com.smart.contact.manager.helper.Message;
import com.smart.contact.manager.services.UserService;
//import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home - Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String register(@Valid @ModelAttribute("user") User user,  BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                           Model model, HttpSession session){

        try {
            if (!agreement) {
                System.out.println("Please agree terms and conditions");
                throw new Exception("Please agree terms and conditions");
            }

            if(result.hasErrors()){
                model.addAttribute("user",user);
                return "signup";
            }

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            System.out.println("Agreement : " + agreement);
            System.out.println("User:" + user);

            User result1 = userService.saveUser(user);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered!", "alert-success"));
            return "signup";

        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong! " + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Login - Smart Contact Manager");
        return "login";
    }
}

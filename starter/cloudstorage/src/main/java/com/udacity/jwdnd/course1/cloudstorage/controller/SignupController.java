package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signup(@ModelAttribute("signupform") SignupForm signupForm, Model model){
        // @ModelAttribute will instantiate SignupForm automatically and insert into model
        return "signup";
    }

    @PostMapping()
    public String HandleSignupPost(@ModelAttribute("signupform") SignupForm signupform, Model model){
        User u = new User(signupform.getFirstname(), signupform.getLastname(), signupform.getUsername(), signupform.getPassword());
        System.out.println(signupform.getFirstname()+ signupform.getLastname()+ signupform.getUsername()+ signupform.getPassword());
        userService.addUser(new User(signupform.getFirstname(), signupform.getLastname(), signupform.getUsername(), signupform.getPassword()));
        return "redirect:/login";
    }
}

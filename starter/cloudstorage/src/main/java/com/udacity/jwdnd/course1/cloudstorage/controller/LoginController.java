package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String login(@RequestParam(value = "invalidUser", required = false, defaultValue = "false") boolean invalidUser,
                        @RequestParam(value = "loggedOut", required = false, defaultValue = "false") boolean loggedOut,
                        Model model){
        model.addAttribute("hideInvalidUserAlert", !invalidUser);
        model.addAttribute("hideLoggedOutAlert", !loggedOut);
        return "login";
    }

}

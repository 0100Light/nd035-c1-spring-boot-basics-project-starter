package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signup(@ModelAttribute("signupform") SignupForm signupForm,
                         Model model,
                         @RequestParam(value = "signupSuccess", required = false, defaultValue = "false") boolean sigupSuccess,
                         @RequestParam(value = "userExist", required = false, defaultValue = "false") boolean userExist){
        // @ModelAttribute will instantiate SignupForm automatically and insert into model
        model.addAttribute("signupSuccess", sigupSuccess);
        model.addAttribute("userExist", userExist);
        return "signup";
    }

    @PostMapping()
    public String HandleSignupPost(@ModelAttribute("signupform") SignupForm signupform, Model model){
//        User u = new User(signupform.getFirstname(), signupform.getLastname(), signupform.getUsername(), signupform.getPassword());
//        System.out.println(signupform.getFirstname()+ signupform.getLastname()+ signupform.getUsername()+ signupform.getPassword());

        if (userService.getUser(signupform.getUsername()) == null){
            int res = userService.addUserHashed(signupform);
            return "redirect:/signup?signupSuccess=true";
        }
        System.out.println("user already exist");
        return "redirect:/signup?userExist=true";
        //TODO: show msg if user already exists
    }
}

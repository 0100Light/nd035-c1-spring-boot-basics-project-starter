package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public String handleError(@RequestParam(value = "errorType", required = false) String errorType, Model model){
        if (errorType != null && errorType.equals("fileSizeTooLarge")){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "File size is too large. Limit is 5 MB at most.");
        }
        return "error";
    }
}

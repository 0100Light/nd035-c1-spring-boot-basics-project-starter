package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Cred;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/home")
public class CredController {
    private CredMapper credMapper;

    public CredController(CredMapper credMapper) {
        this.credMapper = credMapper;
    }

    @GetMapping("/creds")
    public List<Cred> getCreds(){
        return credMapper.creds();
    }
}

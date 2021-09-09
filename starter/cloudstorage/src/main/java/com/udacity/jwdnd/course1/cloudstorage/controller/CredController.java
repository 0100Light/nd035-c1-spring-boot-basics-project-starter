package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Cred;
import com.udacity.jwdnd.course1.cloudstorage.model.EditCredForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class CredController {
    private CredMapper credMapper;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredController(CredMapper credMapper, EncryptionService encryptionService, UserService userService) {
        this.credMapper = credMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @PostMapping("/cred")
    public String addCred(@RequestParam String url, @RequestParam String username, @RequestParam String password, Principal principal){
        // use username as encryption key
        String enc_key = encryptionService.getRandomKey();
        String enc_pass = encryptionService.encryptValue(password, enc_key);
        int userId = userService.getUser(principal.getName()).getUserid();
        credMapper.addCred(new Cred(url, username, enc_key, enc_pass, userId));
        return "redirect:/home#nav-credentials";
    }

    @GetMapping("/cred/edit")
    public String editCred(@RequestParam String credId, Principal principal, Model model){
        int cid = Integer.parseInt(credId);
        Cred cred = credMapper.cred(cid);
        User userData = userService.getUser(principal.getName());

        String decPass = encryptionService.decryptValue(cred.getPassword(), cred.getKey());

        model.addAttribute("editCredForm", new EditCredForm(cid, cred.getUrl(), cred.getUsername(), decPass));
        model.addAttribute("user", userData);
        return "edit_cred";
    }

    @PostMapping("/cred/update")
    public String updateCred(@ModelAttribute EditCredForm editCredForm, Model model, Principal principal){
        User userData = userService.getUser(principal.getName());
        Cred originalCred = credMapper.cred(editCredForm.getCredId());
        String encPass = encryptionService.encryptValue(editCredForm.getPassword(), originalCred.getKey());

        Cred newCred = new Cred(
                editCredForm.getCredId(),
                editCredForm.getUrl(),
                editCredForm.getUsername(),
                originalCred.getKey(),
                encPass,
                userData.getUserid()
        );

        credMapper.updateCred(newCred);

        String rdUrl = "redirect:/home/cred/edit?credId=" + newCred.getCredentialid();
//        return "redirect:/home#nav-credentials";
        return rdUrl;
    }

    @GetMapping("/cred/delete")
    public String delCred(@RequestParam String credId){

        credMapper.delCred(Integer.parseInt(credId));
        return "redirect:/home";
    }
}

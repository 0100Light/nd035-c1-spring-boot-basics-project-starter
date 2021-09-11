package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileMapper fileMapper;
    private NoteMapper noteMapper;
    private CredMapper credMapper;
    private UserService userService;

    public HomeController(FileMapper fileMapper, NoteMapper noteMapper, CredMapper credMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.noteMapper = noteMapper;
        this.credMapper = credMapper;
        this.userService = userService;
    }

    @GetMapping()
    public String homePage(Principal principal, Model model){
        int userId = getUserData(principal).getUserid();
        List<NoteA> notes = noteMapper.getNotes(userId);
        List<Cred> creds = credMapper.creds(userId); // TODO: get with userId
        List<UploadFile> files = fileMapper.getFiles(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("creds", creds);
        model.addAttribute("files", files);

        model.addAttribute("login_user_name", principal.getName());
        return "home";
    }

    @PostMapping()
    public String postNotes(@RequestParam String noteTitle, @RequestParam String noteDescription, Model model, Principal principal){
        String title = noteTitle;
        String desc = noteDescription;
        int userId = getUserData(principal).getUserid();
        noteMapper.addNote(new NoteA(noteTitle, noteDescription, userId));


        int noteSize = noteMapper.countNotes();
        return "redirect:/home#nav-notes";
    }

    @GetMapping("/delete_note")
    public String delNote(@RequestParam String noteId){
        int noteid = Integer.parseInt(noteId);
        noteMapper.deleteNote(noteid);
        return "redirect:/home#nav-notes";
    }

    @GetMapping("/edit_note")
    public String editNote(@RequestParam String noteId, Model model){

        NoteA note = noteMapper.getNote(Integer.parseInt(noteId));
        model.addAttribute("note", note);
        return "edit_note";
    }

    @PostMapping("/update_note")
    public String updateNote(
            @RequestParam("newNoteTitle")String title,
            @RequestParam("newNoteDescription")String desc,
            @RequestParam("userId")String userId,
            @RequestParam("noteId")String noteId,
            Model model)
    {
        noteMapper.updateNote(new NoteA(Integer.parseInt(noteId), title, desc, Integer.parseInt(userId)));
        return "redirect:/home/edit_note?noteId=" + noteId;
    }

    // File
    @PostMapping("/file")
    public String addFile(@RequestParam MultipartFile fileUpload, Model model, Principal principal) throws IOException {
        if(fileUpload.isEmpty()) {
            model.addAttribute("success",false);
            model.addAttribute("message","No file selected to upload!");
            return "redirect:/home";
        }
        String fn = fileUpload.getOriginalFilename();
        Path uploadDir = Paths.get("./src/main/resources/upload");
        Files.createDirectories(uploadDir);
        Path uploadPath = Paths.get(String.valueOf(uploadDir), fn);
        byte[] bytes = fileUpload.getBytes();

        Files.write(uploadPath, bytes);

        // write file info to DB
        String fileExt = FilenameUtils.getExtension(fn);
        long fileSize = Files.size(uploadPath);

        User currentUser = userService.getUser(principal.getName()); // get user
        fileMapper.addFile(new UploadFile(fn, fileExt, fileSize, currentUser.getUserid(), uploadPath.toString()));

        model.addAttribute("success",true);
        model.addAttribute("message","New File added successfully!");
        return "redirect:/home";
    }

    @GetMapping("/file/delete")
    public String delFile(@RequestParam int fileId){
        // TODO: delete file on hard disk
        UploadFile file = fileMapper.getFile(fileId);
        Path filepath = Paths.get(file.getFilelocation());
        try {
            Files.deleteIfExists(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileMapper.delFile(fileId);
        return "redirect:/home";
    }

    public User getUserData(Principal principal){
        return userService.getUser(principal.getName());
    }
}

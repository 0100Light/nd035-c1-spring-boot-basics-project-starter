package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
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
    public String homePage(@RequestParam(value = "msg", required = false) String msg,
                           Principal principal, Model model){
        int userId = getUserData(principal).getUserid();
        List<NoteA> notes = noteMapper.getNotes(userId);
        List<Cred> creds = credMapper.creds(userId); // get with userId
        List<UploadFile> files = fileMapper.getFiles(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("creds", creds);
        model.addAttribute("files", files);

        model.addAttribute("login_user_name", principal.getName());
        // TODO: display message respectively

        var messageMap = new HashMap<String, String>();
        messageMap.put("aCred", "credential added");
        messageMap.put("uCred", "credential updated");
        messageMap.put("dCred", "credential deleted");
        messageMap.put("aNote", "note added");
        messageMap.put("uNote", "note updated");
        messageMap.put("dNote", "note deleted");
        messageMap.put("aFile", "file added");
        messageMap.put("uFile", "file updated");
        messageMap.put("dFile", "file deleted");
        messageMap.put("noFile", "no file to upload");
        messageMap.put("downloadFail", "error downloading file");

        if (msg != null) model.addAttribute("msg", messageMap.get(msg));
        return "home";
    }

    @PostMapping()
    public String postNotes(@RequestParam String noteTitle, @RequestParam String noteDescription, Model model, Principal principal){
        String title = noteTitle;
        String desc = noteDescription;
        int userId = getUserData(principal).getUserid();
        noteMapper.addNote(new NoteA(noteTitle, noteDescription, userId));


        int noteSize = noteMapper.countNotes();
        return "redirect:/home?msg=aNote";
    }

    @GetMapping("/delete_note")
    public String delNote(@RequestParam String noteId){
        int noteid = Integer.parseInt(noteId);
        noteMapper.deleteNote(noteid);
        return "redirect:/home?msg=dNote";
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
        return "redirect:/home?msg=uNote";
    }

    // File
    @PostMapping("/file")
    public String addFile(@RequestParam MultipartFile fileUpload, Model model, Principal principal) throws IOException {
        if(fileUpload.isEmpty()) {
            model.addAttribute("success",false);
            model.addAttribute("message","No file selected to upload!");
            return "redirect:/home?msg=noFile";
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
        return "redirect:/home?msg=aFile";
    }

    @GetMapping("/file/delete")
    public String delFile(@RequestParam("fileId") int fileId){
        // TODO: delete file on hard disk
        UploadFile file = fileMapper.getFile(fileId);
        Path filepath = Paths.get(file.getFilelocation());
        try {
            Files.deleteIfExists(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileMapper.delFile(fileId);
        return "redirect:/home?msg=dFile";
    }

    @GetMapping(value = "/file/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadFile(@PathVariable("filename") String filename,
                             HttpServletResponse response) throws IOException {
        try {
            String filePath = "./src/main/resources/upload/" + filename;
            InputStream is = new FileInputStream(new File(filePath));
            IOUtils.copy(is, response.getOutputStream());
            String fileExt = FilenameUtils.getExtension(filePath);
            response.setHeader("Content-Disposition", "attachment;filename=download." + fileExt);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("/home?msg=downloadFail");
        }

    }

    public User getUserData(Principal principal){
        return userService.getUser(principal.getName());
    }
}

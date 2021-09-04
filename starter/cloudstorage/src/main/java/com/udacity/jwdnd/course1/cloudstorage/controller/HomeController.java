package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteMapper noteMapper;

    public HomeController(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @GetMapping()
    public String Homepage(Model model){
        List<NoteA> notes = noteMapper.getNotes();
        model.addAttribute("notes", notes);
        return "home";
    }

    @PostMapping()
    public String postNotes(@RequestParam String noteTitle, @RequestParam String noteDescription, Model model){
        String title = noteTitle;
        String desc = noteDescription;
        noteMapper.addNote(new Note(title, desc, 1));


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
        NoteA note = noteMapper.getNote(noteId);
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
        return "redirect:/home#nav-notes";
    }
}

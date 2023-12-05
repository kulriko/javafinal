package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notes")

public class NoteController {

    @Autowired 
    private NoteService noteService;

    @GetMapping("/")
    public ResponseEntity<List<Notes>> getAllNotes(){
        return new ResponseEntity<List<Notes>>(noteService.allNotes(), HttpStatus.OK);
        // System.out.println(noteService.allNotes().get(0).getContent());
        // return new ResponseEntity<List<Notes>>(noteService.allNotes(), HttpStatus.OK);
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<Optional<Notes>> getSingleNote(@PathVariable String username){
        return new ResponseEntity<Optional<Notes>>(noteService.findNoteByUsername(username), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Notes> createNote(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Notes>(noteService.createNote(payload.get("title"), payload.get("content"), payload.get("username")), HttpStatus.CREATED);
    }
}

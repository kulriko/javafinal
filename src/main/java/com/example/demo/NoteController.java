package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/v1/notes")

public class NoteController {

    @Autowired 
    private NoteService noteService;
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/")
    public ResponseEntity<List<Notes>> getAllNotes(){
        return new ResponseEntity<List<Notes>>(noteService.allNotes(), HttpStatus.OK);
        // System.out.println(noteService.allNotes().get(0).getContent());
        // return new ResponseEntity<List<Notes>>(noteService.allNotes(), HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{username}")
    public ResponseEntity<Optional<Notes>> getSingleNote(@PathVariable String username){
        return new ResponseEntity<Optional<Notes>>(noteService.findNoteByUsername(username), HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping
    public ResponseEntity<Notes> createNote(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Notes>(noteService.createNote(payload.get("title"), payload.get("content"), payload.get("username")), HttpStatus.CREATED);
    }
}

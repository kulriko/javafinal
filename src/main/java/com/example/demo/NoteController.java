package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notes")
@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
public class NoteController {

    @Autowired 
    private NoteService noteService;
    @Autowired
    private ObjectMapper objectMapper;
    @GetMapping("/")
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<Notes> notes = noteService.allNotes();
        List<NoteDTO> noteDTOs = notes.stream()
                .map(note -> new NoteDTO(
                        note.getId().toString(),
                        note.getTitle(),
                        note.getContent(),
                        note.getUsername()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(noteDTOs, HttpStatus.OK);
    }
    @GetMapping("/{username}")
    public ResponseEntity<Optional<Notes>> getSingleNote(@PathVariable String username){
        return new ResponseEntity<Optional<Notes>>(noteService.findNoteByUsername(username), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Notes> createNote(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Notes>(noteService.createNote(payload.get("title"), payload.get("content"), payload.get("username")), HttpStatus.CREATED);
    }
    @PostMapping("/")
    public ResponseEntity<Notes> addNote(@RequestBody Notes note) {
        return new ResponseEntity<>(noteService.addNote(note), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notes> updateNote(@PathVariable String id, @RequestBody Notes note) {
        return new ResponseEntity<>(noteService.updateNote(id, note), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<String> exportNote(@PathVariable String id) throws Exception {
        Optional<Notes> note = noteService.findNoteById(id);

        if (note.isPresent()) {
            String jsonNote = objectMapper.writeValueAsString(note.get());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonNote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importNote(@RequestBody String jsonNote) throws Exception {
        Notes importedNote = objectMapper.readValue(jsonNote, Notes.class);
        noteService.importNote(importedNote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

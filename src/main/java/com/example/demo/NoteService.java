package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    public List<Notes> allNotes(){
        System.out.println(noteRepository.findAll().toString());
        return noteRepository.findAll();
    }
    public Optional<Notes> findNoteByUsername(String username){
        return noteRepository.findNoteByUsername(username);
    }
    @Autowired
    private MongoTemplate mongoTemplate;

    public Notes createNote(String title, String content, String username) {
        username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Tworzymy notatkę z przypisanym użytkownikiem
        Notes note = new Notes(title, content, username);
        return noteRepository.insert(note);
    }

    //ta wersja dodawania jest jakas prostsza
    public Notes addNote(Notes note) {
        return noteRepository.save(note);
    }


    public Notes updateNote(String id, Notes updatedNote) {
        Optional<Notes> existingNoteOptional = findNoteById(new String(id));
            Notes existingNote = existingNoteOptional.get();
    
            // Update only the fields you want to modify
            existingNote.setTitle(updatedNote.getTitle());
            existingNote.setContent(updatedNote.getContent());
    
            // Save the updated note
            return noteRepository.save(existingNote);

    }
    

    public void deleteNoteById(String id) {
        noteRepository.deleteById(new ObjectId(id));
    }

    public Optional<Notes> findNoteById(String id) {
        return noteRepository.findById(new ObjectId(id));
    }
    public void importNote(Notes importedNote) {
        noteRepository.save(importedNote);
    }

}

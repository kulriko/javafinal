package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    public List<Notes> allNotes(){
        System.out.println(noteRepository.findAll().toString());
        return noteRepository.findAll();
    }
    public Optional<Notes> findNoteByUser(String user){
        return noteRepository.findNoteByUser(user);
    }
}

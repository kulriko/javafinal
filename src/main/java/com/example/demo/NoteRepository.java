package com.example.demo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
@Repository
public interface NoteRepository extends MongoRepository<Notes, ObjectId>{
    Optional<Notes> findNoteByUsername(String username);
}

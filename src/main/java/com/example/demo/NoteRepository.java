package com.example.demo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NoteRepository extends MongoRepository<Notes, ObjectId>{
    Optional<Notes> findNoteByUser(String user);
}

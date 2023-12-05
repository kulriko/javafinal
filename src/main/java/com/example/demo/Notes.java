package com.example.demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private String user;
    
    public Notes(String content, String user){
        this.title = title;
        this.content = content;
        this.user = user;
    }
}

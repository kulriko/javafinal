package com.example.demo;

import lombok.Data;

@Data
public class NoteDTO {
    private String id;
    private String title;
    private String content;
    private String username;

    public NoteDTO(String id, String title, String content, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
    }
}

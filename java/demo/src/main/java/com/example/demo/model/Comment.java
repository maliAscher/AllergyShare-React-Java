package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment {//חוות דעת,מבקר
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private String content;
    private int score;

    @ManyToOne//הרבה תגובות לשיתוף אחד
    private Sharing sharing;

    @ManyToOne//הרבה תגובות למשתמש אחד
    private User user;


    public Comment(String content, int score) {
        this.content = content;// אם לא רוצה להכניס תוכן מה עושים?
        this.score = score;
        this.date = LocalDate.now();
    }

    public Comment() {
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}


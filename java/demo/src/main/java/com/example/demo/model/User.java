package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String lastName;
    private String name;
    private String mail;
    private String password;


    @JsonIgnore
    //קשר של יחיד לרבים
    @OneToMany(mappedBy = "user")//לכל משתמש הרבה שיתופים
    private List<Sharing> sharingList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")//לכל משתמש הרבה תגובות
    private List<Comment> comment;

    public User(Long id, String name, String mail, String password, String Lname) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        boolean flag = true;
        while (flag) {

        }
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Sharing> getSharing() {
        return sharingList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
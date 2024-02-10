package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

    @Entity//המחלקה הזאת תהפך לטבלה
    @Table(name = "sharings")
    public class Sharing {
//        public List<Content> getContents;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String description;
        //הנתיב של התמונה
        private String img;
        //דרך אחת לשמור את התמונה בשרת
//        private byte[] img;
        private LocalDate dateUpload;
        private int score;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @ManyToOne//הרבה שיתופים למשתמש אחד
        private User user;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @ManyToOne//הרבה שיתופים לקטוגריה אחת
        private Category category;

        @JsonIgnore
        @OneToMany(mappedBy = "sharing" ,cascade = CascadeType.ALL)//לכל שיתוף הרבה תגובות
        private List<Comment> comment;
        @OneToMany(mappedBy = "sharing")
        private List<Content> contents;
        public Sharing() {
        }

        public Sharing(Long id,String title, String description, String img, User user, Category category) {
            this.title = title;
            this.description = description;
            this.img = img;// האם אפשר לי=עשות תמונת עם ערך ברירת מחדל?
            this.user = user;
            this.category = category;
            this.dateUpload = LocalDate.now();
            this.score = 0;
            this.category.addSharing(this);
            this.id = id;
        }

        public Long getId() {return id;}

        public void setId(Long id) {this.id = id;}

        public void addComment(Comment comment) {
            this.comment.add(comment);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String picture) {
            this.img = picture;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {this.user = user;}

        public Category getCategory() {
            return category;
        }
        public void setCategory(Category category) {
            this.category = category;
        }

        public List<Comment> getComment() {
            return comment;
        }

        public LocalDate getDateUpload() {
            return dateUpload;
        }
        public void setDateUpload(LocalDate DateUpload) {
            this.dateUpload = dateUpload;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public List<Content> getContents() {
            return contents;
        }

        public void setContents(List<Content> contents) {
            this.contents = contents;
        }
        //        public List<com.example.demo.model.Contents> getContents() {
//            return Contents;
//        }
//
//        public void setContents(List<com.example.demo.model.Contents> contents) {
//            this.Contents = contents;
//        }
    }



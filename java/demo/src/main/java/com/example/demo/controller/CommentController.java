package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
    private CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
//    @GetMapping("/get")//על ידי קישור//הדרך הפשוטה יותר
//    public List<Comment> getComment(){
//        return commentRepository.findAll();
//    }
    @GetMapping("/getComments")//הדרך היותר מדוייקת
    public ResponseEntity<List<Comment>> getComment2(){
        try{
            List<Comment> commentList = new ArrayList<>();
            commentRepository.findAll().forEach(e->commentList.add(e));
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        }
        catch (Exception e){
            //שגיאה 500
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    //השמה

    }
//    @GetMapping("/get/{id}")    //על ידי פרמטר
//    public ResponseEntity<Comment> getCommentById(@PathVariable long id){
//        Comment e=commentRepository.findById(id).orElse(null);
//        if(e !=null) {
//            return new ResponseEntity<>(e,HttpStatus.OK);
//        }
//        else{//אם לא תמצא שיתוף תחזיר שגיאה 404-not found
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//    @GetMapping("/comment")
//    public String getCategories(){
//        return "Mali Ascher";
//    }

    //להוסיף דברים
    @PostMapping("/createComments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment c){
        try{
            Comment newComment = commentRepository.save(c);
            return  new ResponseEntity<>(newComment,HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e);
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @RequestBody Comment comment) {
        Comment com=commentRepository.findById(id).orElse(null);
        if(comment !=null) {
            com.setContent(comment.getContent());
            com.setScore(comment.getScore());
            commentRepository.save(com);
            return new ResponseEntity<>(com,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable long id){
        try{
            commentRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.demo.controller;

import com.example.demo.dto.SharingDTO;
import com.example.demo.model.Sharing;
import com.example.demo.model.User;
import com.example.demo.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //השמה
//    @GetMapping("/get")//על ידי קישור//הדרך הפשוטה יותר
//    public List<User> getUser(){
//        return userRepository.findAll();
//    }
    @GetMapping("/get")//הדרך היותר מדוייקת
    public ResponseEntity<List<User>> getUser2(){
        try{
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(e->userList.add(e));
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        catch (Exception e){
            //שגיאה 500
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//    @GetMapping("/get/{id}")    //על ידי פרמטר
//    public ResponseEntity<User> getUserById(@PathVariable long id){
//        User e=userRepository.findById(id).orElse(null);
//        if(e !=null) {
//            return new ResponseEntity<>(e,HttpStatus.OK);
//        }
//        else{//אם לא תמצא שיתוף תחזיר שגיאה 404-not found
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
    @GetMapping("/user")
    public String getUsers(){
        return "Mali Ascher";
    }

//להוסיף דברים
    @PostMapping("/uCreate")
    public ResponseEntity<User> createUser(@RequestBody User u){
        try{
            User newUser = userRepository.save(u);
            System.out.println(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//עדכון
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        User us=userRepository.findById(id).orElse(null);
        if(user !=null) {
            us.setLastName(user.getLastName());
            us.setMail(user.getMail());
            us.setName(user.getName());
            us.setPassword(user.getPassword());
            userRepository.save(us);
            return new ResponseEntity<>(us,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    //Delete-אם המשתמש רוצה למחוק משהו שהעלה
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable long id){
        try{
            userRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<User> check(@RequestBody User u) throws IOException {
        User user=userRepository.findByMail(u.getMail());
        System.out.println(user);
        if(user!=null && user.getPassword().equals(u.getPassword())){//mail & pass correct
            return new ResponseEntity<>(user,HttpStatus.OK);//200

        }
        else if(user!=null){//mail correct & pass uncorrect
            return new ResponseEntity<>(user,HttpStatus.CREATED);//201- יחזור סטטוס לא שגיאה אבל גם לא אוקי
        }
        else//mail&password uncorrect- send to Sign UP
            return new ResponseEntity<>(HttpStatus.ACCEPTED);//202
    }




}

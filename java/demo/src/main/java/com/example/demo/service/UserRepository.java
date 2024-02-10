package com.example.demo.service;

import com.example.demo.model.Sharing;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User,Long>{
    User findByMail(String mail);
}

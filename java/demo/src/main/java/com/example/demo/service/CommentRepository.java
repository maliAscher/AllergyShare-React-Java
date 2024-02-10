package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Sharing;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CommentRepository extends JpaRepository<Comment,Long> {

}

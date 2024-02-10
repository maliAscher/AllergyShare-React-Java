package com.example.demo.service;

import com.example.demo.model.Sharing;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface SharingRepository extends JpaRepository<Sharing,Long> {
    List<Sharing> findAllByDateUploadBefore(LocalDate d);
    List<Sharing> findAllByCategoryId(Long id);
    Sharing findSharingByTitle(String t);

}

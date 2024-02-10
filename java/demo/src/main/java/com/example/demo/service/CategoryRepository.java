package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Sharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CategoryRepository extends JpaRepository<Category,Long> {
}

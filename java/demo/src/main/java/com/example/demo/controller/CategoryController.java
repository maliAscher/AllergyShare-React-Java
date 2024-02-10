package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    //השמה
//    @GetMapping("/get")//על ידי קישור//הדרך הפשוטה יותר
//    public List<Category> getUCategory(){
//        return categoryRepository.findAll();
//    }
    @GetMapping("/get")//הדרך היותר מדוייקת
    public ResponseEntity<List<Category>> getCategory2(){
        try{
            List<Category> categoryList = new ArrayList<>();
            categoryRepository.findAll().forEach(e->categoryList.add(e));
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
        catch (Exception e){
            //שגיאה 500
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//    @GetMapping("/get/{id}")    //על ידי פרמטר
//    public ResponseEntity<Category> getCategoryById(@PathVariable long id){
//        Category e=categoryRepository.findById(id).orElse(null);
//        if(e !=null) {
//            return new ResponseEntity<>(e,HttpStatus.OK);
//        }
//        else{//אם לא תמצא שיתוף תחזיר שגיאה 404-not found
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
    @GetMapping("/category")
    public String getCategories(){
        return "Mali Ascher";
    }

    //להוסיף דברים
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category c){
        try{
            Category newCategory = categoryRepository.save(c);
            return  new ResponseEntity<>(newCategory,HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e);
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //עדכון
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category category) {
        Category cat=categoryRepository.findById(id).orElse(null);
        if(category !=null) {
            cat.setDescription(category.getDescription());
            cat.setIcon(category.getIcon());
            cat.setName(category.getName());
            categoryRepository.save(cat);
            return new ResponseEntity<>(cat,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    //Delete-אם המשתמש רוצה למחוק משהו שהעלה
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable long id){
        try{
            categoryRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

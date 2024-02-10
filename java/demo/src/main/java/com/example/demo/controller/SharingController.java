package com.example.demo.controller;

import com.example.demo.dto.SharingDTO;
import com.example.demo.model.Sharing;
import com.example.demo.service.MapStructMapper;
import com.example.demo.service.SharingRepository;
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

//(origins = "http://localhost:3000",methods = {RequestMethod.GET,RequestMethod.POST})
//jdk - 17

@RestController
@RequestMapping("/api/sharing")
@CrossOrigin
public class SharingController {
    //Get-מקבלת מהשרת
    private SharingRepository sharingRepository;
    private MapStructMapper mapper;

    @Autowired
    public SharingController(SharingRepository sharingRepository, MapStructMapper mapper) {
        this.sharingRepository = sharingRepository;
        this.mapper = mapper;
    }

    private static String UPLOAD_DIRETORY=System.getProperty("user.dir")+"\\images\\";
//    @Autowired
//    public SharingController(SharingRepository sharingRepository,MapStructMapper mapper){
//        this.sharingRepository=sharingRepository;
//        this.mapper=mapper;
//    }

@GetMapping("/get")//על ידי קישור//הדרך הפשוטה יותר
    public List<Sharing> getSharings(){
        return sharingRepository.findAll();
    }
    @GetMapping("/get2")//הדרך היותר מדוייקת
    public ResponseEntity<List<Sharing>> getSharings2(){
        try{
            List<Sharing> sharingList = new ArrayList<>();
            sharingRepository.findAll().forEach(e->sharingList.add(e));
            return new ResponseEntity<>(sharingList,HttpStatus.OK);
        }
        catch (Exception e){
            //שגיאה 500
           return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/get/{id}")    //על ידי פרמטר
    public ResponseEntity<Sharing> getSharingById(@PathVariable long id){
        Sharing e=sharingRepository.findById(id).orElse(null);
        if(e !=null) {
            return new ResponseEntity<>(e,HttpStatus.OK);
        }
        else{//אם לא תמצא שיתוף תחזיר שגיאה 404-not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/shar")
    public String getSharing(){
        return "Mali Ascher";
    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<Sharing> getSharingById(@PathVariable long id){
//        Sharing sharing =sharingRepository.findById(id).orElse(null);
//        if(sharing != null){
//            return  new ResponseEntity<>(sharing,)
//        }
    //}
    //Post-להוסיף מידע לשרת
    @PostMapping("/create")
    public ResponseEntity<Sharing> createSharing(@RequestBody Sharing s){
        try{
            Sharing newSharing = sharingRepository.save(s);
            return  new ResponseEntity<>(newSharing,HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e);
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Put-לעדכן משהו מהשרת
    @PutMapping("/update/{id}")
    public ResponseEntity<Sharing> updateSharing(@PathVariable long id, @RequestBody Sharing share) {
        Sharing sh=sharingRepository.findById(id).orElse(null);
        if(share !=null) {
            sh.setCategory(share.getCategory());
            sh.setDateUpload(share.getDateUpload());
            sh.setScore(share.getScore());
            sh.setTitle(share.getTitle());
            sh.setDescription(share.getDescription());
            sh.setImg(share.getImg());

            sharingRepository.save(sh);
            return new ResponseEntity<>(sh,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
     //Delete-אם המשתמש רוצה למחוק משהו שהעלה
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteShar(@PathVariable long id){
        try{
            sharingRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//-----------פונקציה שתעלה את מה שרוצה להעלות עם תמונה-----------
@PostMapping("/uploadShare")
    public ResponseEntity uploadShareWithImage(@RequestPart("image") MultipartFile file, @RequestPart ("sharing") SharingDTO s) throws IOException {

      try{
        String filePath=UPLOAD_DIRETORY+file.getOriginalFilename();
        //        הנתיב בו נשמור את התמונה
        Path filename= Paths.get(filePath);
        Files.write(filename,file.getBytes());
        s.setImagePath(filePath);
        Sharing newSharing=sharingRepository.save(mapper.dtoToSharing(s));
        return new ResponseEntity(newSharing,HttpStatus.CREATED);
      }
        catch (IOException e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getdto/{id}")
    public ResponseEntity<SharingDTO> getDTO(@PathVariable long id) throws IOException {
        Sharing s=sharingRepository.findById(id).orElse(null);
//        SharingDTO sd=new SharingDTO();
//        sd.setId(s.getId());
        if(s!=null) {
            return new ResponseEntity<>(mapper.sharingToDto(s), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}

package com.example.demo.service;
import com.example.demo.dto.SharingDTO;
import com.example.demo.model.Content;
import com.example.demo.model.Sharing;
import org.mapstruct.Mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    //ידע להחזיר באופן אוטומטי גם רשימה כזאת על ידי השימוש בפונקציה הכתובה למטה👇
    List<SharingDTO> sharingToDto(List<Sharing> sharings);
//    כאן נכתוב פונקציות ששייכות להמרה בין המחלקות
    default Sharing dtoToSharing (SharingDTO s) throws IOException {

        Sharing share=new Sharing();
        share.setId(s.getId());
        List<Content> list= new ArrayList<>();
        List<String> strings=s.getContents();
//        for (int i = 0; i < strings.size(); i++) {
//            list.add(new Content((long)i,strings.get(i),i,share));
//        }
        share.setContents(list);
//        share.setId(list);
        share.setImg(s.getImagePath());
        return share;
    }
    default SharingDTO sharingToDto (Sharing s) throws IOException {
        SharingDTO sharingDTO= new SharingDTO();
        sharingDTO.setId(s.getId());
        sharingDTO.setCategory(s.getCategory());
        sharingDTO.setDescription(s.getDescription());
        sharingDTO.setScore(s.getScore());
        sharingDTO.setTitle(s.getTitle());
        sharingDTO.setDateUpload(s.getDateUpload());
        sharingDTO.setComment(s.getComment());
        sharingDTO.setUser(s.getUser());
//כstring
//        StringBuilder builder = new StringBuilder();
//        List<Content> list=s.getContents();
//        for (int i=0; i<list.size(); i++){
//            builder.append(list.get(i).getText()+"\n");
//        }
//        sharingDTO.setContents(builder.toString());
//כlist של string
        List<Content> list=s.getContents();
        List<String> list1=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i).getText());

        }
        sharingDTO.setContents(list1);



        //ההמרה של התמונה שקיבלנו לביטים כדי שיחזרו לריאקט בהצלחה
        Path filename = Paths.get(s.getImg());
        //הופך את התמונה למערך ביטים
        byte[] byteImage = Files.readAllBytes(filename);
        sharingDTO.setImage(Base64.getEncoder().encodeToString(byteImage));
        return sharingDTO;
    }
}

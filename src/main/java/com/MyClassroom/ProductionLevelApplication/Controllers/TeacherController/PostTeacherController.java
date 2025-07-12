package com.MyClassroom.ProductionLevelApplication.Controllers.TeacherController;


import com.MyClassroom.ProductionLevelApplication.Services.TeacherServices.TeacherPostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:3000")
public class PostTeacherController {

    @Autowired
    TeacherPostServices teacherPostServices;

    @PostMapping("/upload-assignment")
    public ResponseEntity<String> uploadAssignments(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("file") MultipartFile file, @RequestParam("uploadedBy") String uploadedBy , Principal principal)
    {
        if (principal.getName().equals("teacher@demo.com")){
            return new ResponseEntity<>("It's a demo account soo you can't upload SORRY!!", HttpStatus.OK);
        }

        if (!"zafermairaj7255@gmail.com".equals(principal.getName())) {
            return new ResponseEntity<>("SORRY Only registered teacher is allowed to upload!!..", HttpStatus.OK);
        }

        String result = teacherPostServices.upload_Assignments(title,description,file,uploadedBy);

       if (result.equals("Assignment uploaded successfully")) {
           return new ResponseEntity<>(result, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping("/upload-recordvideos")
    public ResponseEntity<?> uploadRecordVideos(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile video,
            @RequestParam String uploadedBy,
            Principal principal

    )
    {
        System.out.println(principal.getName());
        if (principal.getName().equals("teacher@demo.com")){
            return new ResponseEntity<>("It's a demo account soo you can't upload SORRY!!", HttpStatus.OK);
        }

        if (!"zafermairaj7255@gmail.com".equals(principal.getName())) {
            return new ResponseEntity<>("SORRY Only registered teacher is allowed to upload!!..", HttpStatus.OK);
        }

        String result = teacherPostServices.uploadClass(title, description, video, uploadedBy);
        if (result.equals("Video Upload Successfully")) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }
    }

}

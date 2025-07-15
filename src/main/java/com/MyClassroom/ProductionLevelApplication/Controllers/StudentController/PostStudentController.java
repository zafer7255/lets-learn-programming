package com.MyClassroom.ProductionLevelApplication.Controllers.StudentController;

import com.MyClassroom.ProductionLevelApplication.Services.StudentServices.StudentPostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = {"http://localhost:3000", "https://your-frontend-url.vercel.app"})
public class PostStudentController {

    @Autowired
    StudentPostServices studentPostServices;

    @PostMapping("/submit-assignment")
    public ResponseEntity<?> uploadCompletedAssignment(
            @RequestParam("assignmentId") int assignmentId,
            @RequestParam("studentEmail") String studentEmail,
            @RequestParam("studentName") String studentName,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file,
            Principal principal
    )
    {
        if(principal.getName().equals("student@demo.com")){
            return new ResponseEntity<>("It's a demo account you can't upload anything ", HttpStatus.OK);
        }
        String result = studentPostServices.uploadCompletedAssignment(assignmentId,studentEmail,studentName,title,file);
        if(result.equals("Assignment uploaded successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }
    }
}

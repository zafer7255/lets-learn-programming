package com.MyClassroom.ProductionLevelApplication.Controllers.StudentController;


import com.MyClassroom.ProductionLevelApplication.Models.Student;
import com.MyClassroom.ProductionLevelApplication.Services.StudentServices.RegisterStudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = {"http://localhost:3000", "https://your-frontend-url.vercel.app"})
public class StudentRegisterController {

    @Autowired
    RegisterStudentServices registerStudentServices;

    @PostMapping("/student")
    public ResponseEntity<?> registerStudent(@RequestBody Student student)
    {
        String result = registerStudentServices.RegisterStudent(student);

        if(result.equals("We Send verification link to your email verify it") || result.equals("Verified")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

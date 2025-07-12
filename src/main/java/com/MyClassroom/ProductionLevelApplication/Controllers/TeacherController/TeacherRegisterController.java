package com.MyClassroom.ProductionLevelApplication.Controllers.TeacherController;

import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.Services.TeacherServices.RegisterTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherRegisterController {

    @Autowired
    RegisterTeacherService registerTeacherService;

    @PostMapping("/teacher")
    public ResponseEntity<?> registerTeacher(@RequestBody Teacher teacher) {
        String result = registerTeacherService.RegisterTeacher(teacher);
        if (result.equals("We Send verification link to your email verify it") || result.equals("Verified")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}

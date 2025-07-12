package com.MyClassroom.ProductionLevelApplication.Controllers;


import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.StudentRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.TeacherRepo;
import com.MyClassroom.ProductionLevelApplication.Services.UserServices.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.MyClassroom.ProductionLevelApplication.Models.Student;


import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    @PostMapping("/student")
    public ResponseEntity<?> loginStudent(@RequestBody Map<String, String> credentials)
    {

         String email = credentials.get("email");
         String password = credentials.get("password");

         Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email,password)
        );

        if (authentication.isAuthenticated()){
            Student student = studentRepo.findByEmail(email);

            if (student != null && student.isVerified()) {
                String token = jwtService.generateToken(email);
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "user", student
                ));
            }
            else if (student != null && student.isVerified() == false)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verify your account first");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/teacher")
    public ResponseEntity<?> loginTeacher(@RequestBody Map<String,String> credentials)
    {
        String email = credentials.get("email");
        String password = credentials.get("password");


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        if(authentication.isAuthenticated())
        {
            Teacher teacher = teacherRepo.findByEmail(email);
            if(teacher != null && teacher.isVerified()) {
                String token = jwtService.generateToken(email);
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "user", teacher
                ));
            }
            else if (teacher != null && teacher.isVerified() == false)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verify your account first");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

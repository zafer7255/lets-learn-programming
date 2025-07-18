package com.MyClassroom.ProductionLevelApplication.Controllers.TeacherController;


import com.MyClassroom.ProductionLevelApplication.Models.Completed_Assignments;
import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.Models.Student;
import com.MyClassroom.ProductionLevelApplication.Services.TeacherServices.TeacherGetServices;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000", "https://your-frontend-url.vercel.app"})
@RequestMapping("/teacher")
public class GetTeacherController {

    @Autowired
    TeacherGetServices teacherGetServices;
    @Autowired
    Cloudinary cloudinary;

    @GetMapping("/detail")
    public ResponseEntity<?> GetTeacherDetail(@RequestParam String email , Principal principal) {

        if (principal.getName().equals("teacher@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }

        Teacher teacher = teacherGetServices.GetTeacherDetails(email);
        if (teacher.getEmail() != null) {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Worker not found ",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getallstudent")
    public ResponseEntity<?> getAllStudents(Principal principal)
    {
        if (principal.getName().equals("teacher@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }

        List<Student> students = teacherGetServices.GetAllStudents();
        if(!students.isEmpty()) {
            return new ResponseEntity<>(students,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Students Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-completed-assignments")
    public ResponseEntity<?> getCompletedAllAssignments(Principal principal)
    {
        if (principal.getName().equals("teacher@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }

       List<Completed_Assignments> completedAssignments = teacherGetServices.getAllCompletedAssignments();
       if (!completedAssignments.isEmpty()) {
           return new ResponseEntity<>(completedAssignments,HttpStatus.OK);
       } else {
           return new ResponseEntity<>("Completed_Assignments Not Found",HttpStatus.NOT_FOUND);
       }
    }

    @PutMapping("/assignments/{id}/grade")
    public ResponseEntity<?> updateAssignmentGrade(@PathVariable int id , @RequestBody Map<String, String> body , Principal principal)
    {
        if (principal.getName().equals("teacher@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }

        String result = teacherGetServices.updateAssignmentForGrade(id,body);
        if(result.equals("Grade updated")){
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/download-completed-assignment/{filename:.+}")
    public ResponseEntity<Resource> downloadCompletedAssignment(@PathVariable String filename , Principal principal) {

        if (principal.getName().equals("teacher@demo.com")){
            return ResponseEntity.badRequest().build();
        }

        try {
            // Construct Cloudinary public ID with folder
            String publicId = "student/completed-assignments/" + filename;

            // Generate Cloudinary URL for the raw file
            String fileUrl = cloudinary.url()
                    .resourceType("raw")
                    .generate(publicId);

            // Use RestTemplate to download the file bytes from Cloudinary
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(fileUrl, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                byte[] fileBytes = response.getBody();

                ByteArrayResource resource = new ByteArrayResource(fileBytes);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .contentLength(fileBytes.length)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

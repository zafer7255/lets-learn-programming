package com.MyClassroom.ProductionLevelApplication.Controllers.StudentController;


import com.MyClassroom.ProductionLevelApplication.Models.ClassRecording;
import com.MyClassroom.ProductionLevelApplication.Models.Student;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.AssignmentsRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.ClassRecordingRepo;
import com.MyClassroom.ProductionLevelApplication.Services.StudentServices.StudentGetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://your-frontend-url.vercel.app"})
@RequestMapping("/student")
public class GetStudentController {

    @Autowired
    StudentGetServices studentGetServices;
    @Autowired
    AssignmentsRepo assignmentsRepo;
    @Autowired
    ClassRecordingRepo classRecordingRepo;

    @GetMapping("/details/{email}")
    public ResponseEntity<?> GetStudentDetail(@PathVariable String email , Principal principal)
    {
        if (principal.getName().equals("student@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }

        if (email.equals("student@demo.com")){
            Student student = new Student();

            student.setVerified(true);
            student.setPassword("123456");
            student.setEmail("student@demo.com");
            student.setName("Recruter");
            student.setPhoneNo("000 000 000");
            student.setRole("STUDENT");

            return new ResponseEntity<>(student,HttpStatus.OK);
        }
        Student student = studentGetServices.GetStudentDetails(email);
        if(student.getEmail() != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Worker not found ",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-assignments")
    public ResponseEntity<?> GetAllAssignment(Principal principal)
    {
        if (principal.getName().equals("student@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assignmentsRepo.findAll());
    }

    @GetMapping("download-assignment/{filename:.+}")
    public ResponseEntity<Resource> downloadAssignments(@PathVariable String filename , Principal principal)
    {
        if (principal.getName().equals("student@demo.com")){
            return ResponseEntity.badRequest().build();
        }
        try
        {
            Path filePath = Paths.get("/home/zafer/MY_CLASS_ROOM/ProductionLevelApplication/src/main/java/com/MyClassroom/ProductionLevelApplication/UploadAssignments/")
                    .resolve(filename)
                    .normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);


        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("all-recorded-classes")
    public ResponseEntity<?> GetAllClasses(Principal principal)
    {
        if (principal.getName().equals("student@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(classRecordingRepo.findAll());
    }

    @GetMapping("video/{filename:.+}")
    public ResponseEntity<Resource> downloadVideos(@PathVariable String filename , Principal principal)
    {
        if (principal.getName().equals("student@demo.com")){
            return ResponseEntity.badRequest().build();
        }
        try
        {
            Path filePath = Paths.get("/home/zafer/MY_CLASS_ROOM/ProductionLevelApplication/src/main/java/com/MyClassroom/ProductionLevelApplication/UploadRecordingVideos/")
                    .resolve(filename)
                    .normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);


        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/recorded-info/{id}")
    public ResponseEntity<?> classRecordingData(@PathVariable int id , Principal principal)
    {
        if (principal.getName().equals("student@demo.com")){
            return new ResponseEntity<>("You're Not Allowed to featch anything Sorry man!!",HttpStatus.NOT_FOUND);
        }
        for (ClassRecording classRecording : classRecordingRepo.findAll())
        {
            if (classRecording.getId() == id){
                return new ResponseEntity<>(classRecording,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Not Found" , HttpStatus.NOT_FOUND);
    }
}

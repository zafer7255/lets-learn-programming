package com.MyClassroom.ProductionLevelApplication.Services.StudentServices;

import com.MyClassroom.ProductionLevelApplication.Models.Completed_Assignments;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.Completed_AssignmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StudentPostServices {

    @Autowired
    Completed_AssignmentsRepo completed_assignmentsRepo;
    public String uploadCompletedAssignment(int assignmentId, String studentEmail, String studentName, String title, MultipartFile file) {

        try {

            String uploadDir = "/home/zafer/MY_CLASS_ROOM/ProductionLevelApplication/src/main/java/com/MyClassroom/ProductionLevelApplication/UploadCompletedAssignment/";
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            String fileUrl = fileName;

            Completed_Assignments completedAssignments = new Completed_Assignments();
            completedAssignments.setAssignmentId(assignmentId);
            completedAssignments.setStudentEmail(studentEmail);
            completedAssignments.setStudentName(studentName);
            completedAssignments.setTitle(title);
            completedAssignments.setFileURL(fileUrl);
            completed_assignmentsRepo.save(completedAssignments);
            return "Assignment Upload Successfully";
        } catch (Exception e) {
            System.out.println(e);
            return "Something went wrong";
        }
    }
}

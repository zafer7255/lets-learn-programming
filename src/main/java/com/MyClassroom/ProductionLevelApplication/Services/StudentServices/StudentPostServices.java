package com.MyClassroom.ProductionLevelApplication.Services.StudentServices;

import com.MyClassroom.ProductionLevelApplication.Models.Completed_Assignments;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.Completed_AssignmentsRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class StudentPostServices {

    @Autowired
    Cloudinary cloudinary;
    @Autowired
    Completed_AssignmentsRepo completed_assignmentsRepo;

    public String uploadCompletedAssignment(int assignmentId, String studentEmail, String studentName, String title, MultipartFile file) {

        try {
            // Cloud folder path like: teacher/completedassignment/studentEmail/
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "resource_type", "raw",
                    "folder", "student/completed-assignments"// 'raw' is used for non-image/video files like PDF
            ));

            String fileUrl = uploadResult.get("secure_url").toString();

            // Save to DB
            Completed_Assignments completedAssignments = new Completed_Assignments();
            completedAssignments.setAssignmentId(assignmentId);
            completedAssignments.setStudentEmail(studentEmail);
            completedAssignments.setStudentName(studentName);
            completedAssignments.setTitle(title);
            completedAssignments.setFileURL(fileUrl);

            completed_assignmentsRepo.save(completedAssignments);

            return "Assignment uploaded successfully";

        } catch (Exception e) {
            System.out.println(e);
            return "Something went wrong";
        }
    }
}

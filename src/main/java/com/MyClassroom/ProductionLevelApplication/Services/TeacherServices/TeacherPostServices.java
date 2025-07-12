package com.MyClassroom.ProductionLevelApplication.Services.TeacherServices;

import com.MyClassroom.ProductionLevelApplication.Models.Assignments;
import com.MyClassroom.ProductionLevelApplication.Models.ClassRecording;
import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.AssignmentsRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.ClassRecordingRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class TeacherPostServices {

    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    AssignmentsRepo assignmentsRepo;

    @Autowired
    ClassRecordingRepo classRecordingRepo;
    public String UpdateTeacher(Teacher teacher , String email) {

        for (Teacher teacher1 : teacherRepo.findAll()) {
            if(teacher1.getEmail().equals(email)) {
                 teacher.setEmail(teacher1.getEmail());
                 teacher.setPassword(teacher1.getPassword());
                 teacherRepo.save(teacher1);
                 return "UPDATED";
            }
        }
        return "User Not Found";
    }

    public String upload_Assignments(String title, String description, MultipartFile file, String uploadedBy) {
        try {
            String uploadDir = "/home/zafer/MY_CLASS_ROOM/ProductionLevelApplication/src/main/java/com/MyClassroom/ProductionLevelApplication/UploadAssignments/";
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Correct file URL to save in DB
            String fileUrl = fileName;

            Assignments assignments = new Assignments();
            assignments.setTitle(title);
            assignments.setDescription(description);
            assignments.setFileUrl(fileUrl);
            assignments.setUploadedBy(uploadedBy);

            assignmentsRepo.save(assignments);
            return "Assignment uploaded successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }

    public String uploadClass(String title, String description, MultipartFile video, String uploadedBy) {

        try {
            String uploadDir = "/home/zafer/MY_CLASS_ROOM/ProductionLevelApplication/src/main/java/com/MyClassroom/ProductionLevelApplication/UploadRecordingVideos/";
            String fileName = UUID.randomUUID() + "-" + video.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, video.getBytes());

            String videoUrl = fileName;
            ClassRecording classRecording = new ClassRecording();
            classRecording.setTitle(title);
            classRecording.setDescription(description);
            classRecording.setVideoUrl(videoUrl);
            classRecording.setUploadedBy(uploadedBy);

            classRecordingRepo.save(classRecording);

            return "Video Upload Successfully";

        } catch (Exception e) {

            System.out.println(e);
            return "Video Upload failed";

        }

    }
}

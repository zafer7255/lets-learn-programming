package com.MyClassroom.ProductionLevelApplication.Services.TeacherServices;

import com.MyClassroom.ProductionLevelApplication.Models.Assignments;
import com.MyClassroom.ProductionLevelApplication.Models.ClassRecording;
import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.AssignmentsRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.ClassRecordingRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.TeacherRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class TeacherPostServices {

    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    AssignmentsRepo assignmentsRepo;
    @Autowired
    Cloudinary cloudinary;

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
            // Upload the file to Cloudinary (can be PDF, DOCX, etc.)
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "resource_type", "raw",
                    "folder", "teacher/class-assignments"// 'raw' is used for non-image/video files like PDF
            ));

            // Get the file URL
            String fileUrl = uploadResult.get("secure_url").toString();

            // Save file details in DB
            Assignments assignments = new Assignments();
            assignments.setTitle(title);
            assignments.setDescription(description);
            assignments.setFileUrl(fileUrl);  // now cloud URL
            assignments.setUploadedBy(uploadedBy);

            assignmentsRepo.save(assignments);
            return "Assignment uploaded successfully to Cloudinary";
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }

    public String uploadClass(String title, String description, MultipartFile video, String uploadedBy) {

        try {
            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(video.getBytes(), ObjectUtils.asMap(
                    "resource_type", "video",
                    "folder", "teacher/class-recordings"
            ));

            // Get the video URL
            String videoUrl = uploadResult.get("secure_url").toString();

            // Save in your database
            ClassRecording classRecording = new ClassRecording();
            classRecording.setTitle(title);
            classRecording.setDescription(description);
            classRecording.setVideoUrl(videoUrl);  // now cloud URL
            classRecording.setUploadedBy(uploadedBy);

            classRecordingRepo.save(classRecording);

            return "Video uploaded successfully to Cloudinary";

        } catch (Exception e) {
            e.printStackTrace();
            return "Video upload failed";
        }

    }
}

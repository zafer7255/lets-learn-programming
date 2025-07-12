package com.MyClassroom.ProductionLevelApplication.Services.StudentServices;

import com.MyClassroom.ProductionLevelApplication.Models.Student;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentGetServices {

    @Autowired
    StudentRepo studentRepo;
    public Student GetStudentDetails (String email) {

        for (Student student : studentRepo.findAll()) {
            if(student.getEmail().equals(email)) {
                return student;
            }
        }
        return new Student();
    }

    public List<Student> GetAllStudents()
    {
        return studentRepo.findAll();
    }
}

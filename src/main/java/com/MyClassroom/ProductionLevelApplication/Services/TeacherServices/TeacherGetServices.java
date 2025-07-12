package com.MyClassroom.ProductionLevelApplication.Services.TeacherServices;

import com.MyClassroom.ProductionLevelApplication.Models.Completed_Assignments;
import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.Models.Student;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.Completed_AssignmentsRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.TeacherRepo;
import com.MyClassroom.ProductionLevelApplication.Services.StudentServices.StudentGetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherGetServices {

    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    StudentGetServices studentGetServices;
    @Autowired
    Completed_AssignmentsRepo completedAssignmentsRepo;

    public Teacher GetTeacherDetails(String email) {

        for (Teacher teacher : teacherRepo.findAll()) {
            if (teacher.getEmail().equals(email)) {
                return teacher;
            }
        }
        return new Teacher();
    }

    public List<Student> GetAllStudents() {
        List<Student> students = studentGetServices.GetAllStudents();
        return students;
    }

    public List<Completed_Assignments> getAllCompletedAssignments() {
        return completedAssignmentsRepo.findAll();
    }

    public String updateAssignmentForGrade(int id, Map<String, String> body) {

        for (Completed_Assignments comp : completedAssignmentsRepo.findAll()) {
            if (comp.getId() == id) {
                comp.setGrade(body.get("grade"));
                comp.setStatus("Graded");
                completedAssignmentsRepo.save(comp);
                return "Grade updated";
            }
        }
        return "Assignment Not Found";
    }
}

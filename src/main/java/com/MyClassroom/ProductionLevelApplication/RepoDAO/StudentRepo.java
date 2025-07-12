package com.MyClassroom.ProductionLevelApplication.RepoDAO;


import com.MyClassroom.ProductionLevelApplication.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {
    Student findByEmail(String email);
    Student findByEmailAndPassword(String email , String password);
}

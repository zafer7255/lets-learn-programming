package com.MyClassroom.ProductionLevelApplication.RepoDAO;


import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo  extends JpaRepository<Teacher,String> {
    Teacher findByEmail(String email);
    Teacher findByEmailAndPassword(String email , String password);
}

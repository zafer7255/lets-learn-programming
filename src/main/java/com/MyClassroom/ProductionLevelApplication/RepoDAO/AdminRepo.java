package com.MyClassroom.ProductionLevelApplication.RepoDAO;

import com.MyClassroom.ProductionLevelApplication.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,String> {

    Admin findByEmail(String email);
}

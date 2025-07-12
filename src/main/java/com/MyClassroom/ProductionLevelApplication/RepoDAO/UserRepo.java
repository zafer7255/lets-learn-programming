package com.MyClassroom.ProductionLevelApplication.RepoDAO;

import com.MyClassroom.ProductionLevelApplication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
    User findByEmail(String email);
}

package com.MyClassroom.ProductionLevelApplication.RepoDAO;

import com.MyClassroom.ProductionLevelApplication.Models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}

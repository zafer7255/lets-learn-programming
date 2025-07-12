package com.MyClassroom.ProductionLevelApplication.RepoDAO;

import com.MyClassroom.ProductionLevelApplication.Models.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentsRepo extends JpaRepository<Assignments,Integer> {
    Assignments findById(int id);
}

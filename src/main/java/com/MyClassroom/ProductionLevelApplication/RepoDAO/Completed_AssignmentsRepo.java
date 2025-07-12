package com.MyClassroom.ProductionLevelApplication.RepoDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.MyClassroom.ProductionLevelApplication.Models.Completed_Assignments;

public interface Completed_AssignmentsRepo extends JpaRepository<Completed_Assignments,Integer> {

    Completed_Assignments findById(int id);
}

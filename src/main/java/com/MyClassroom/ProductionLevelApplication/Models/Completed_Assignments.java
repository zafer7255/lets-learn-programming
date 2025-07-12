package com.MyClassroom.ProductionLevelApplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Completed_Assignments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int assignmentId;
    private String studentEmail;
    private String studentName;

    private String title;
    private String fileURL;
    private String status = "Submitted";
    private String grade = "Not graded yet";
}

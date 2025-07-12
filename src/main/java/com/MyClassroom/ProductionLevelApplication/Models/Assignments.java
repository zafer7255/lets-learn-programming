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
public class Assignments {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String title;
        private String description;
        private String fileUrl;
        private String uploadedBy;
}

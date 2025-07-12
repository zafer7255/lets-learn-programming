package com.MyClassroom.ProductionLevelApplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class Student {

    @Id
    private String email;
    private String password;
    private String phoneNo;
    private String role;
    private String name;
    private boolean verified = false;
}

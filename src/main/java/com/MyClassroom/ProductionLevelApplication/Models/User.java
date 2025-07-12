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
public class User {

    @Id
    private String email;
    private String password;
    private String role;
    private  boolean verified = false;
}

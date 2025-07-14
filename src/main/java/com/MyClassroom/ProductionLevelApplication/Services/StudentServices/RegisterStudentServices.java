package com.MyClassroom.ProductionLevelApplication.Services.StudentServices;

import com.MyClassroom.ProductionLevelApplication.Models.Student;
import com.MyClassroom.ProductionLevelApplication.Models.User;
import com.MyClassroom.ProductionLevelApplication.Models.VerificationToken;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.StudentRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.UserRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.VerificationTokenRepo;
import com.MyClassroom.ProductionLevelApplication.Services.EmailService.UserEmailService;
import com.MyClassroom.ProductionLevelApplication.Services.UserServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class RegisterStudentServices {

    private final String role = "STUDENT";
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    VerificationTokenRepo verificationTokenRepo;
    @Autowired
    UserEmailService userEmailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String RegisterStudent(Student student) {

        for (User user : userRepo.findAll()){

            if (user.getEmail().equals(student.getEmail()) && !user.getRole().equals(student.getRole()))
            {
                if(user.isVerified()){
                    return "Email Already Exist With " + user.getRole() + " Role";
                } else {
                    return "Email Already Exist With " + user.getRole() + " Role but it is not verified soo register again with " + user.getRole() + " Role";
                }
            }
            else if(user.getEmail().equals(student.getEmail()) && user.getRole().equals(student.getRole())) {

                if (user.isVerified()){
                    return "Email Already Exist Try New One";
                } else {
                     break;
                }
            }
        }

        String password = student.getPassword();
        student.setPassword(encoder.encode(student.getPassword()));
        if(userService.RegisterUser(student.getEmail(),student.getPassword(),role , password)){

            if (student.getEmail().equals("student@demo.com") && password.equals("123456"))
            {
                student.setVerified(true);
                studentRepo.save(student);
                return "Verified";
            }
            studentRepo.save(student);

            //Email Services Start here

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUserEmail(student.getEmail());
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            verificationTokenRepo.save(verificationToken);

            //Using multithreading here for email services

            CompletableFuture.runAsync(() -> {
                userEmailService.sendVerificationMail(student.getEmail(),token);
            });

            return "We Send verification link to your email verify it";

        } else {
            return "Error While Username Registered";
        }
    }

    public boolean VerifyThis(String email)
    {
        for (Student student : studentRepo.findAll())
        {
            if (student.getEmail().equals(email)) {
                student.setVerified(true);
                studentRepo.save(student);
                return true;
            }
        }
        return false;
    }
}

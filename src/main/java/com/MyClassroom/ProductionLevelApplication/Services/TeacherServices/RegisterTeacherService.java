package com.MyClassroom.ProductionLevelApplication.Services.TeacherServices;

import com.MyClassroom.ProductionLevelApplication.Models.Teacher;
import com.MyClassroom.ProductionLevelApplication.Models.User;
import com.MyClassroom.ProductionLevelApplication.Models.VerificationToken;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.TeacherRepo;
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
public class RegisterTeacherService {

    private final String role = "TEACHER";
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    VerificationTokenRepo verificationTokenRepo;
    @Autowired
    UserEmailService userEmailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    public String RegisterTeacher(Teacher teacher) {

        for (User user : userRepo.findAll()) {

            if (user.getEmail().equals(teacher.getEmail()) && !user.getRole().equals(teacher.getRole()))
            {
                if (user.isVerified()) {
                    return "Email Already Exist With " + user.getRole() + " Role";
                }
                else {
                    return "Email Already Exist With " + user.getRole() + " Role but it is not verified soo register again with " + user.getRole() + "Role";
                }
            }
           else if(user.getEmail().equals(teacher.getEmail()) && user.getRole().equals(teacher.getRole())){
               if (user.isVerified()){
                   return "Email Already Exist Try New One";
               } else {
                   break;
               }
            }
        }


        String pass = teacher.getPassword();
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        if(userService.RegisterUser(teacher.getEmail(),teacher.getPassword(),role,pass)) {
            if(teacher.getEmail().equals("teacher@demo.com") && pass.equals("123456"))
            {
                teacher.setVerified(true);
                teacherRepo.save(teacher);
                return "Verified";
            }
            teacherRepo.save(teacher);


            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUserEmail(teacher.getEmail());
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            verificationTokenRepo.save(verificationToken);

            //Using multithreading here for email services

            CompletableFuture.runAsync(() -> {
                userEmailService.sendVerificationMail(teacher.getEmail(),token);
            });


            return "We Send verification link to your email verify it";
        } else {
            return "Error While Username Registered";
        }

    }

    public boolean VerifyThis(String email)
    {
        for (Teacher teacher : teacherRepo.findAll())
        {
            if (teacher.getEmail().equals(email)) {
                teacher.setVerified(true);
                teacherRepo.save(teacher);
                return true;
            }
        }
        return false;
    }
}

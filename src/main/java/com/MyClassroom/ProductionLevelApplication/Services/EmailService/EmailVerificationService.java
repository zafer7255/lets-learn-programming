package com.MyClassroom.ProductionLevelApplication.Services.EmailService;


import com.MyClassroom.ProductionLevelApplication.Models.User;
import com.MyClassroom.ProductionLevelApplication.Models.VerificationToken;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.UserRepo;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.VerificationTokenRepo;
import com.MyClassroom.ProductionLevelApplication.Services.StudentServices.RegisterStudentServices;
import com.MyClassroom.ProductionLevelApplication.Services.TeacherServices.RegisterTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailVerificationService {

    @Autowired
    VerificationTokenRepo verificationTokenRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RegisterStudentServices registerStudentServices;
    @Autowired
    RegisterTeacherService registerTeacherService;


    public String VerifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(token);

        if (verificationToken.getToken().equals(null)) {
            return "Invalid Token";
        } else if(!verificationToken.getToken().equals(token)) {
            return "Something went wrong";
        } else if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Verification time expired";
        }

        for (User user : userRepo.findAll()) {
            if (user.getEmail().equals(verificationToken.getUserEmail())){
                user.setVerified(true);
                userRepo.save(user);
                boolean result = false;
                if(user.getRole().equals("STUDENT"))
                {
                    result = registerStudentServices.VerifyThis(user.getEmail());
                } else if( user.getRole().equals("TEACHER")) {
                    result = registerTeacherService.VerifyThis(user.getEmail());
                }
                if (result){
                    return "<h1>Verified</h2>";
                }
                return "Not Verified";
            }
        }
        return "Something went wrong";
    }
}

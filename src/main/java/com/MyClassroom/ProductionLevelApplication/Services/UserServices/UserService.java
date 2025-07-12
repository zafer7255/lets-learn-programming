package com.MyClassroom.ProductionLevelApplication.Services.UserServices;


import com.MyClassroom.ProductionLevelApplication.Models.User;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public boolean RegisterUser(String email , String password , String role , String pass)
    {
       try {
           User user = new User();
           user.setEmail(email);
           user.setRole(role);
           user.setPassword(password);
           if ( (email.equals("student@demo.com") && pass.equals("123456")) || (email.equals("teacher@demo.com") && pass.equals("123456")) )
           {
               user.setVerified(true);
           }
           userRepo.save(user);
           return true;
       } catch (Exception e) {
           System.out.println("Exception while Register User" +  e);
           return false;
       }
    }
}

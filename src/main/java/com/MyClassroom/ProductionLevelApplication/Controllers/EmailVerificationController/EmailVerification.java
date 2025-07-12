package com.MyClassroom.ProductionLevelApplication.Controllers.EmailVerificationController;


import com.MyClassroom.ProductionLevelApplication.Services.EmailService.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class EmailVerification {

    @Autowired
    EmailVerificationService emailVerificationService;

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token)
    {
        System.out.println("Verifying " + token);
        String result = emailVerificationService.VerifyToken(token);

        System.out.println("Result = " + result);

        if (result.equals("Verified")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }
    }
}

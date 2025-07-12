package com.MyClassroom.ProductionLevelApplication.Services;

import com.MyClassroom.ProductionLevelApplication.Models.User;
import com.MyClassroom.ProductionLevelApplication.Models.UserPrincipal;
import com.MyClassroom.ProductionLevelApplication.RepoDAO.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found " + email);
        }
        return new UserPrincipal(user);
    }
}

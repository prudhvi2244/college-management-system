package com.cms.in.service;


import com.cms.in.entity.UserRegistration;
import com.cms.in.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements IUserRegistrationService {
    @Autowired
    private UserRegistrationRepository urepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserRegistration saveUser(UserRegistration user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        return urepo.save(user);
    }
}

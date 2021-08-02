package com.cms.in.controller;


import com.cms.in.entity.UserRegistration;
import com.cms.in.jwt.JwtUtil;
import com.cms.in.service.IUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    private IUserRegistrationService uservice;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "/")
    public String homePage()
    {
        return "home";
    }
    @GetMapping(value = "/about")
    public String aboutPage()
    {
        return "about";
    }

    @GetMapping(value = "/admissions")
    public String admissionsPage()
    {
        return "admissions";
    }



    @PostMapping(value = "/login")
    public ResponseEntity<UserResponse> loginPage(@RequestBody UserRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        String token=jwtUtil.generateToken(request.getUsername());
        UserResponse userResponse=new UserResponse(token);
        return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
    }

    @GetMapping(value = "/register")
    public String getUserRegistrationPage()
    {
        return "register";
    }

    @PostMapping(value = "/register")
    public String postUserRegistrationPage(@RequestBody UserRegistration user, Model model)
    {
        UserRegistration userRegistration=uservice.saveUser(user);
        model.addAttribute("msg","Registration Success!");
        return "register";
    }


}

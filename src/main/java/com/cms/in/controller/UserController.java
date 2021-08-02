package com.cms.in.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping(value = "/welcome")
    public String getWelcomePage(Principal principal, HttpSession session)
    {
        String username=principal.getName();
        session.setAttribute("username",username);
        return "user/user-welcome";
    }

    @GetMapping(value = "/access-denied")
    public String accessDeniedPage()
    {
        return "user/access-denied";
    }


    @GetMapping(value = "/allStudents")
    public String getAllStudents()
    {
        return "user/all-students";
    }

    @GetMapping(value = "/allTeachers")
    public String getAllTeachers()
    {
        return "user/all-teachers";
    }

}

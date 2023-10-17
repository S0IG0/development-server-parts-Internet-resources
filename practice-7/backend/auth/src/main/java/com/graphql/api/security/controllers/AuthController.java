package com.graphql.api.security.controllers;


import com.graphql.api.security.custom.models.User;
import com.graphql.api.security.custom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "views/register";
    }

    @PostMapping("/register")
    public String registerPost(
            @RequestParam("username") String username,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        userService.saveUser(new User(
                username,
                firstname,
                lastname,
                email,
                password
        ));

        return "redirect:login";
    }
}

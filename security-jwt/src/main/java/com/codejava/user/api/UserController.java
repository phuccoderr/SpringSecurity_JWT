package com.codejava.user.api;

import com.codejava.security.JwtService;
import com.codejava.security.UserInfoDetailsService;
import com.codejava.user.User;
import com.codejava.user.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired private UserInfoDetailsService userInfoDetailsService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Security tutorials";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody @Valid User user) {
        return userInfoDetailsService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(loginRequest.getEmail());

        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        List<User> allUser = userInfoDetailsService.getAllUser();
        return allUser;
    }

    @GetMapping("/users/{id}")
    public User getAllUser(@PathVariable("id")Integer id) {
        return userInfoDetailsService.getUser(id);
    }
}

package com.codejava.security;

import com.codejava.user.User;
import com.codejava.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email);
        if (user != null) {
            return new UserInfoDetail(user);
        } throw new UsernameNotFoundException("Cloud not find user with Email: " + email);
    }

    public String addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return "User added successfully";
    }

    public List<User> getAllUser() {
        return repo.findAll();
    }

    public User getUser(Integer id) {
        return repo.findById(id).get();
    }
}

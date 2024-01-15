package com.codejava.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("phuc@gmail.com");
        user.setPassword("0123456789");
        user.setEnabled(true);

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPassword());
        user.setPassword(encode);

        repo.save(user);

    }

    @Test
    public void testFindByEmail() {
        User byEmail = repo.findByEmail("phuc@gmail.com");
        System.out.println(byEmail.getEmail());

    }
}

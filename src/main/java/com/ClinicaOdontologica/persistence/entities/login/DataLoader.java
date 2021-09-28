package com.ClinicaOdontologica.persistence.entities.login;

import com.ClinicaOdontologica.persistence.repository.login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("password");
        String password2 = passwordEncoder.encode("password2");

        //userRepository.save(new User("admin", "adminTester", "admin", "admin@gmail.com", password2, UserRoles.ADMIN));
        userRepository.save(new User("user", "tester", "user", "user@gmail.com", password, UserRoles.USER));
    }
}

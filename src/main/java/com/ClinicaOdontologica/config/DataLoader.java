package com.ClinicaOdontologica.config;

import com.ClinicaOdontologica.persistence.entities.login.User;
import com.ClinicaOdontologica.persistence.entities.login.UserRole;
import com.ClinicaOdontologica.persistence.repository.login.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private IUserRepository IUserRepository;

    @Autowired
    public DataLoader(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("password");
        String password2 = passwordEncoder.encode("password2");

        IUserRepository.save(new User("admin", "admin", "admin", "admin@gmail.com", password2, UserRole.ROLE_ADMIN));
        IUserRepository.save(new User("user", "user", "user", "user@gmail.com", password, UserRole.ROLE_USER));
    }
}

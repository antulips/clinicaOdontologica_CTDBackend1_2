package com.ClinicaOdontologica.service.impl.login;

import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.persistence.entities.login.User;
import com.ClinicaOdontologica.persistence.repository.login.UserRepository;
import com.ClinicaOdontologica.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) throws ServiceException {
        if (user != null){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);
            return userRepository.save(user);
        } else {
            throw new ServiceException(String.format(Messages.ERROR_AL_CREAR, "usuario"));
        }
    }

    public Collection<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow((() -> new UsernameNotFoundException(String.format(Messages.ERROR_AL_CREAR ))));
    }
}

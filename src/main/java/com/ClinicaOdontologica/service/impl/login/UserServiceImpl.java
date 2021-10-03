package com.ClinicaOdontologica.service.impl.login;

import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.persistence.entities.login.User;
import com.ClinicaOdontologica.persistence.repository.login.IUserRepository;
import com.ClinicaOdontologica.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

    private final IUserRepository IUserRepository;

    @Autowired
    public UserServiceImpl(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }

    public User create(User user) throws ServiceException {
        if (user != null){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);
            return IUserRepository.save(user);
        } else {
            throw new ServiceException(String.format(Messages.ERROR_AL_CREAR, "usuario"));
        }
    }

    public Collection<User> getAll(){
        return IUserRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return IUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(Messages.ERROR_AL_CREAR ));
    }
}

package com.ClinicaOdontologica.config;

import com.ClinicaOdontologica.persistence.entities.login.UserRole;
import com.ClinicaOdontologica.service.impl.login.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Sobreescribimos los métodos de configuración que necesitamos
    //http
    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/turnos/**").hasAnyAuthority(UserRole.ROLE_USER.name(), UserRole.ROLE_ADMIN.name())
                    .antMatchers("/odontologos/**", "/paciente/**").hasAnyAuthority(UserRole.ROLE_ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }
}

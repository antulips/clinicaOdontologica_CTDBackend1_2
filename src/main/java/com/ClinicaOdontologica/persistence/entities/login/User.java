package com.ClinicaOdontologica.persistence.entities.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String Password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User() {

    }

    //constructor sin id
    public User(String name, String lastname, String username, String email, String password, UserRole userRole) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        Password = password;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(grantedAuthority);
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserRole getAppUsuarioRoles() {
        return userRole;
    }

    public void setAppUsuarioRoles(UserRole userRole) {
        this.userRole = userRole;
    }
}

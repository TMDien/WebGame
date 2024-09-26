package com.web.game.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "token")
    private String token;

    @Column(name = "time_reset_password")
    private LocalDateTime password_reset_token_expiry;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String email, String password, boolean enabled, String userName, String token) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.userName = userName;
        this.token = token;
    }

    public User(String email, String password, boolean enabled, String userName,
                Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getPassword_reset_token_expiry() {
        return password_reset_token_expiry;
    }

    public void setPassword_reset_token_expiry(LocalDateTime password_reset_token_expiry) {
        this.password_reset_token_expiry = password_reset_token_expiry;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", userName='" + userName + '\'' +
                '}';
    }
}
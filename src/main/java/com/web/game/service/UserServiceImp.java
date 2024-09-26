package com.web.game.service;

import com.web.game.dao.RoleDAO;
import com.web.game.dao.UserDAO;
import com.web.game.entity.Role;
import com.web.game.entity.User;
import com.web.game.user.RegisUser;
import com.web.game.util.EmailDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public  class UserServiceImp implements UserService {

    // Service Email
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private UserDAO userDao;

    private RoleDAO roleDao;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDAO userDao, RoleDAO roleDao, BCryptPasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public User findByUserName(String userName) {
        // check the database if the user already exists
        return userDao.findByEmail(userName);
    }

    @Override
    public void save(RegisUser user) {
        User newUser = new User();

        // assign user details to the user object
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUserName(user.getUserName());
        newUser.setEnabled(true);

        newUser.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        userDao.save(newUser);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }


    @Override
    public boolean sendSimpleEmail(EmailDetail emailDetail){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetail.getRecipient());
            mailMessage.setText(emailDetail.getMsgBoy());
            mailMessage.setSubject(emailDetail.getSubject());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findByToken(String token) {
        return userDao.finByToken(token);
    }
}

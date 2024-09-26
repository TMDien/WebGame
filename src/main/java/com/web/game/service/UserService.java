package com.web.game.service;

import com.web.game.entity.User;
import com.web.game.user.RegisUser;
import com.web.game.util.EmailDetail;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUserName(String userName);
    void save(RegisUser user);
    void save(User user);

    boolean sendSimpleEmail(EmailDetail emailDetail);

    User findByToken(String token);
}

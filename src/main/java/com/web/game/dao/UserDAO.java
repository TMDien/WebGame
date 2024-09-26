package com.web.game.dao;

import com.web.game.entity.User;

public interface UserDAO {
    User findByEmail(String email);
    void save(User user);
    User finByToken(String token);
}

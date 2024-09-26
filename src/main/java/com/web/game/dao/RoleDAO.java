package com.web.game.dao;

import com.web.game.entity.Role;

public interface RoleDAO {
    public Role findRoleByName(String theRoleName);
}

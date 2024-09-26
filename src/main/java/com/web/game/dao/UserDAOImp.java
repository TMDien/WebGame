package com.web.game.dao;

import com.web.game.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImp implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImp(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public User findByEmail(String email) {

        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where email=:uName and enabled=true", User.class);
        theQuery.setParameter("uName", email);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.merge(user);
    }

    @Override
    public User finByToken(String token) {
        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where token=:token and enabled=true", User.class);
        theQuery.setParameter("token", token);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }
}

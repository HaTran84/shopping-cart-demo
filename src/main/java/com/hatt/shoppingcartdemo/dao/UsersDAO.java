package com.hatt.shoppingcartdemo.dao;

import com.hatt.shoppingcartdemo.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UsersDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Users findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Users.class, userName);
    }
}

package com.hatt.shoppingcartdemo.dao;

import com.hatt.shoppingcartdemo.entity.Colour;
import com.hatt.shoppingcartdemo.model.ColourInfo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class ColourDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Colour findCatagory(int colourId) {
        try {
            String sql = "Select e from " + Colour.class.getName() + " e Where e.colourId =:colourId ";
            Session session = this.sessionFactory.getCurrentSession();
            Query<Colour> query = session.createQuery(sql, Colour.class);
            query.setParameter("colourId", colourId);
            return (Colour) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ColourInfo> queryColours() {

        String sql = "Select new " + ColourInfo.class.getName() //
                + "(c.colourId, c.name, c.priority) " + " from "//
                + Colour.class.getName() + " c ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<ColourInfo> query = session.createQuery(sql, ColourInfo.class);
        try {
            List<ColourInfo> result = query.getResultList();
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return new ArrayList<>();
    }


}
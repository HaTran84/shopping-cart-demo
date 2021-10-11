package com.hatt.shoppingcartdemo.dao;

import com.hatt.shoppingcartdemo.entity.Catagory;
import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.model.CatagoryInfo;
import com.hatt.shoppingcartdemo.model.ProductInfo;
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
public class CatagoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Catagory findCatagory(String catId) {
        try {
            String sql = "Select e from " + Catagory.class.getName() + " e Where e.catId =:catId ";
            Session session = this.sessionFactory.getCurrentSession();
            Query<Catagory> query = session.createQuery(sql, Catagory.class);
            query.setParameter("catId", catId);
            return (Catagory) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CatagoryInfo> queryCatagories() {

        String sql = "Select new " + CatagoryInfo.class.getName() //
                + "(c.catId, c.name, c.priority) " + " from "//
                + Catagory.class.getName() + " c ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<CatagoryInfo> query = session.createQuery(sql, CatagoryInfo.class);
        try {
            List<CatagoryInfo> result = query.getResultList();
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return new ArrayList<>();
    }


}
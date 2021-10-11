package com.hatt.shoppingcartdemo.dao;

import com.hatt.shoppingcartdemo.entity.Brand;
import com.hatt.shoppingcartdemo.entity.Catagory;
import com.hatt.shoppingcartdemo.model.BrandInfo;
import com.hatt.shoppingcartdemo.model.CatagoryInfo;
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
public class BrandDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Brand findCatagory(int brandId) {
        try {
            String sql = "Select e from " + Brand.class.getName() + " e Where e.brandId =:brandId ";
            Session session = this.sessionFactory.getCurrentSession();
            Query<Brand> query = session.createQuery(sql, Brand.class);
            query.setParameter("brandId", brandId);
            return (Brand) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<BrandInfo> queryBrands() {

        String sql = "Select new " + BrandInfo.class.getName() //
                + "(c.brandId, c.name, c.priority) " + " from "//
                + Brand.class.getName() + " c ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<BrandInfo> query = session.createQuery(sql, BrandInfo.class);
        try {
            List<BrandInfo> result = query.getResultList();
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return new ArrayList<>();
    }


}
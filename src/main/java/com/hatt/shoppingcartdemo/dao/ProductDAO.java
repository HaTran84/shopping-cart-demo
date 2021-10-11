package com.hatt.shoppingcartdemo.dao;

import java.io.IOException;
import java.util.Date;

import javax.persistence.NoResultException;

import com.hatt.shoppingcartdemo.entity.Catagory;
import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.entity.ProductCatagory;
import com.hatt.shoppingcartdemo.form.ProductForm;
import com.hatt.shoppingcartdemo.model.ProductInfo;
import com.hatt.shoppingcartdemo.model.SearchProductInfo;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Product findProduct(int productId) {
        try {
            String sql = "Select e from " + Product.class.getName() + " e Where e.productId =:productId ";
            System.out.println(sql);
            Session session = this.sessionFactory.getCurrentSession();
            Query<Product> query = session.createQuery(sql, Product.class);
            query.setParameter("productId", productId);
            return (Product) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductInfo findProductInfo(int productId) {
        Product product = this.findProduct(productId);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getProductId(), product.getName(), product.getPrice(), null, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {

        Session session = this.sessionFactory.getCurrentSession();
        int productId = productForm.getProductId();

        Product product = null;

        boolean isNew = false;
        if (productId > 0) {
            product = this.findProduct(productId);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setProductId(productId);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());

        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            session.persist(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }

    //todo : not finish implemented
    public PaginationResult<ProductInfo> queryProducts(SearchProductInfo searchProductInfo) {
        //String sql = new StringBuilder().append("Select new ").append(ProductInfo.class.getName()).append("(e.productId, e.name, e.price) ").append(" FROM Product e INNER JOIN e.catagories t").toString();
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.productId, p.name, p.price, colour.colourId, colour.name) " + " from "//
                + Product.class.getName() + " p  LEFT JOIN p.colours pc LEFT JOIN pc.colour colour";
        if (searchProductInfo.getCatId() != null && searchProductInfo.getCatId().size() > 0) {
            sql += " INNER JOIN p.catagories pc ";
        }
        if (searchProductInfo.getBrandId() != null && searchProductInfo.getBrandId().size() > 0) {
            sql += " INNER JOIN p.brands brand ";
        }
        if (searchProductInfo.getLikeName() != null && searchProductInfo.getLikeName().length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        if (searchProductInfo.getCatId() != null && searchProductInfo.getCatId().size() > 0) {
            sql = addANDorWHERE(sql);
            sql += " lower(pc.catagory.catId) in :catId ";
        }
        if (searchProductInfo.getBrandId() != null && searchProductInfo.getBrandId().size() > 0) {
            sql = addANDorWHERE(sql);
            sql += " brand.brand.brandId in :brandId ";
        }
        if (searchProductInfo.getColourId() != null && searchProductInfo.getColourId().size() > 0) {
            sql = addANDorWHERE(sql);
            sql += " colour.colour.colourId in :colourId ";
        }
        if (searchProductInfo.getMinPrice() != null && searchProductInfo.getMinPrice() > 0) {
            sql = addANDorWHERE(sql);
            sql += " p.price >= :minPrice ";
        }
        if (searchProductInfo.getMaxPrice() != null && searchProductInfo.getMaxPrice() > 0) {
            sql = addANDorWHERE(sql);
            sql += " p.price <= :maxPrice ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = this.sessionFactory.getCurrentSession();
        Query<ProductInfo> query = session.createQuery(sql, ProductInfo.class);

        if (searchProductInfo.getLikeName() != null && searchProductInfo.getLikeName().length() > 0) {
            query.setParameter("likeName", "%" + searchProductInfo.getLikeName().toLowerCase() + "%");
        }
        if (searchProductInfo.getCatId() != null && searchProductInfo.getCatId().size() > 0) {
            query.setParameter("catId", searchProductInfo.getCatId());
        }
        if (searchProductInfo.getBrandId() != null && searchProductInfo.getBrandId().size() > 0) {
            query.setParameter("brandId", searchProductInfo.getBrandId());
        }
        if (searchProductInfo.getColourId() != null && searchProductInfo.getColourId().size() > 0) {
            query.setParameter("colourId", searchProductInfo.getColourId());
        }
        if (searchProductInfo.getMinPrice() != null && searchProductInfo.getMinPrice() > 0) {
            query.setParameter("minPrice", searchProductInfo.getMinPrice());
        }
        if (searchProductInfo.getMaxPrice() != null && searchProductInfo.getMaxPrice() > 0) {
            query.setParameter("maxPrice", searchProductInfo.getMaxPrice());
        }
        return new PaginationResult<ProductInfo>(query, searchProductInfo.getPage(), searchProductInfo.getMaxResult(), searchProductInfo.getMaxNavigationPage());
    }

    private String addANDorWHERE(String sql) {
        if (sql.contains("Where")) {
            sql += " AND ";
        } else {
            sql += " Where ";
        }
        return sql;
    }

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        SearchProductInfo searchProductInfo = new SearchProductInfo(page, maxResult, maxNavigationPage, null, null, null, null, null, null);
        return queryProducts(searchProductInfo);
    }

}
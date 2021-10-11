package com.hatt.shoppingcartdemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Products_Brand")
public class ProductBrand implements Serializable {
    private static final long serialVersionUID = 7550745928843183535L;

    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCTS_BRAND_BRAN_FK"))
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCTS_BRAND_PROD_FK"))
    private Product product;

    public ProductBrand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

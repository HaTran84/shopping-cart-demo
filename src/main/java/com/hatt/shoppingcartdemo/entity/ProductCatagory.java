package com.hatt.shoppingcartdemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Products_Catagory")
public class ProductCatagory implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCTS_CATAGORY_CATA_FK"))
    private Catagory catagory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCTS_CATAGORY_PROD_FK"))
    private Product product;

    public ProductCatagory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

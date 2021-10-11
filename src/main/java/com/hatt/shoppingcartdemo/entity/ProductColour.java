package com.hatt.shoppingcartdemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Products_Colour")
public class ProductColour implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLOUR_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCTS_COLOUR_COLO_FK"))
    private Colour colour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCTS_COLOUR_PROD_FK"))
    private Product product;

    public ProductColour() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

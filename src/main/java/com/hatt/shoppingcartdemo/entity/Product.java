package com.hatt.shoppingcartdemo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Products")
public class Product implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "DESCRIPTION", length = 4000, nullable = true)
    private String description;

    @Column(name = "Name", length = 255, nullable = false)
    private String name;

    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "QUANITY", nullable = false)
    private int quantity;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;

    @OneToMany(mappedBy = "product")
    private Set<ProductCatagory> catagories = new HashSet<ProductCatagory>();

    @OneToMany(mappedBy = "product")
    private Set<ProductBrand> brands = new HashSet<ProductBrand>();

    @OneToMany(mappedBy = "product")
    private Set<ProductColour> colours = new HashSet<ProductColour>();

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quatity) {
        this.quantity = quatity;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductCatagory> getCatagories() {
        return catagories;
    }

    public void setCatagories(Set<ProductCatagory> catagories) {
        this.catagories = catagories;
    }

    public Set<ProductBrand> getBrands() {
        return brands;
    }

    public void setBrands(Set<ProductBrand> brands) {
        this.brands = brands;
    }

    public Set<ProductColour> getColours() {
        return colours;
    }

    public void setColours(Set<ProductColour> colours) {
        this.colours = colours;
    }
}
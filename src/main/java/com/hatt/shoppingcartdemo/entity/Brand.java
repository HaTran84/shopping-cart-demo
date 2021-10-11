package com.hatt.shoppingcartdemo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Brand")
public class Brand implements Serializable {
    @Id
    @Column(name = "brand_id", nullable = false)
    private int brandId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 4000, nullable = true)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @OneToMany(mappedBy = "brand")
    private Set<ProductBrand> productBrand = new HashSet<ProductBrand>();

    public Brand() {
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<ProductBrand> getCatagoryBrand() {
        return productBrand;
    }

    public void setCatagoryBrand(Set<ProductBrand> productBrand) {
        this.productBrand = productBrand;
    }
}

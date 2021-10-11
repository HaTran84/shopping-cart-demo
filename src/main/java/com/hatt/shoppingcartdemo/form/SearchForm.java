package com.hatt.shoppingcartdemo.form;

import com.hatt.shoppingcartdemo.model.CatagoryInfo;

import java.util.Arrays;
import java.util.List;

public class SearchForm {
    private boolean valid;

    private String[] catIdList;
    private Integer[] brandIdList;
    private Integer[] colourIdList;
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Integer pageNumber;

    public SearchForm() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String[] getCatIdList() {
        return catIdList;
    }

    public void setCatIdList(String[] catIdList) {
        this.catIdList = catIdList;
    }

    public Integer[] getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(Integer[] brandIdList) {
        this.brandIdList = brandIdList;
    }

    public Integer[] getColourIdList() {
        return colourIdList;
    }

    public void setColourIdList(Integer[] colourIdList) {
        this.colourIdList = colourIdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "valid=" + valid +
                ", catIdList=" + Arrays.toString(catIdList) +
                ", brandIdList=" + Arrays.toString(brandIdList) +
                ", colourIdList=" + Arrays.toString(colourIdList) +
                ", name='" + name + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", pageNumber=" + pageNumber +
                '}';
    }
}

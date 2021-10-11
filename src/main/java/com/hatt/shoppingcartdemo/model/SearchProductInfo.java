package com.hatt.shoppingcartdemo.model;

import java.util.List;

public class SearchProductInfo {
    private int page;
    private int maxResult;
    private int maxNavigationPage;
    private String likeName;
    private List<String> catId;
    private List<Integer> brandId;
    private List<Integer> colourId;
    private Double minPrice;
    private Double maxPrice;

    public SearchProductInfo() {
    }

    public SearchProductInfo(int page, int maxResult, int maxNavigationPage, String likeName, List<String> catId, List<Integer> brandId, List<Integer> colourId, Double minPrice, Double maxPrice) {
        this.page = page;
        this.maxResult = maxResult;
        this.maxNavigationPage = maxNavigationPage;
        this.likeName = likeName;
        this.catId = catId;
        this.brandId = brandId;
        this.colourId = colourId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getMaxNavigationPage() {
        return maxNavigationPage;
    }

    public void setMaxNavigationPage(int maxNavigationPage) {
        this.maxNavigationPage = maxNavigationPage;
    }

    public String getLikeName() {
        return likeName;
    }

    public void setLikeName(String likeName) {
        this.likeName = likeName;
    }

    public List<String> getCatId() {
        return catId;
    }

    public void setCatId(List<String> catId) {
        this.catId = catId;
    }

    public List<Integer> getBrandId() {
        return brandId;
    }

    public void setBrandId(List<Integer> brandId) {
        this.brandId = brandId;
    }

    public List<Integer> getColourId() {
        return colourId;
    }

    public void setColourId(List<Integer> colourId) {
        this.colourId = colourId;
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
}

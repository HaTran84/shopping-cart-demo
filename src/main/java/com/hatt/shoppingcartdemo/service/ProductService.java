package com.hatt.shoppingcartdemo.service;

import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.form.ProductForm;
import com.hatt.shoppingcartdemo.model.*;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;

import java.util.List;
import java.util.Map;

public interface ProductService {
    PaginationResult<ProductInfo> readProducts(SearchProductInfo searchProductInfo);
    CartInfo addProduct(CartInfo cartInfo, int productId, Integer colourId);
    CartInfo removeProduct(CartInfo cartInfo, int productId, Integer colourId);
    Product findProduct(int productId);
    void saveProduct(ProductForm productForm);
    List<CatagoryInfo> getCatagories();
    List<BrandInfo> getBrands();
    List<ColourInfo> getColours();
    Map<Integer, ColourInfo> getColourInfoMap();
}

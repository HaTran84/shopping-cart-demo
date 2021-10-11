package com.hatt.shoppingcartdemo.service.impl;

import com.hatt.shoppingcartdemo.dao.BrandDAO;
import com.hatt.shoppingcartdemo.dao.CatagoryDAO;
import com.hatt.shoppingcartdemo.dao.ColourDAO;
import com.hatt.shoppingcartdemo.dao.ProductDAO;
import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.form.ProductForm;
import com.hatt.shoppingcartdemo.model.*;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;
import com.hatt.shoppingcartdemo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CatagoryDAO catagoryDAO;

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    private ColourDAO colourDAO;

    // Use temp . If DB change I'll think this case, maybe should move to cache class
    private List<CatagoryInfo> catagories;
    private List<BrandInfo> brands;
    private List<ColourInfo> colours;
    private Map<Integer, ColourInfo> colourInfoMap;

    @Override
    public PaginationResult<ProductInfo> readProducts(SearchProductInfo searchProductInfo) {
        return productDAO.queryProducts(searchProductInfo);
    }

    @Override
    public CartInfo addProduct(CartInfo cartInfo, int productId, Integer colourId) {
        Product product = null;
        if (productId > 0) {
            product = productDAO.findProduct(productId);
        }
        if (product != null) {
            ProductInfo productInfo = new ProductInfo(product);
            if (colourId != null && colourId > 0) {
                productInfo.setColourId(colourId);
                productInfo.setColourName(getColourInfo(colourId) != null ? getColourInfo(colourId).getName() : null);
            }
            cartInfo.addProduct(productInfo, 1);
        }
        return cartInfo;
    }

    @Override
    public CartInfo removeProduct(CartInfo cartInfo, int productId, Integer colourId) {
        Product product = null;
        if (productId > 0) {
            product = productDAO.findProduct(productId);
        }
        if (product != null) {
            ProductInfo productInfo = new ProductInfo(product);
            if (colourId != null && colourId > 0) {
                productInfo.setColourId(colourId);
                productInfo.setColourName(getColourInfo(colourId) != null ? getColourInfo(colourId).getName() : null);
            }
            cartInfo.removeProduct(productInfo);

        }
        return cartInfo;
    }

    @Override
    public Product findProduct(int productId) {
        Product product = null;
        if (productId > 0) {
            product = this.productDAO.findProduct(productId);
        }
        return product;
    }

    @Override
    public void saveProduct(ProductForm productForm) {
        productDAO.save(productForm);
    }

    @Override
    public List<CatagoryInfo> getCatagories() {
        if (catagories == null) {
            catagories = catagoryDAO.queryCatagories();
        }
        return catagories;
    }
    @Override
    public List<BrandInfo> getBrands() {
        if (brands == null) {
            brands = brandDAO.queryBrands();
        }
        return brands;
    }
    @Override
    public List<ColourInfo> getColours() {
        if (colours == null) {
            colours = colourDAO.queryColours();
        }
        return colours;
    }
    @Override
    public  Map<Integer, ColourInfo> getColourInfoMap() {
        if (colourInfoMap == null) {
            colourInfoMap = new HashMap<>();
            colours = getColours();
            for (ColourInfo item :colours) {
                if (colourInfoMap.get(item.getColourId()) == null) {
                    colourInfoMap.put(item.getColourId(), item);
                }
            }
        }
        return colourInfoMap;
    }
    private ColourInfo getColourInfo(Integer colourId) {
        colourInfoMap = getColourInfoMap();
        return colourInfoMap.get(colourId);
    }
}

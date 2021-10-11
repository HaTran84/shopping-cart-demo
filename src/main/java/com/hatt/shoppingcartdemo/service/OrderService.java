package com.hatt.shoppingcartdemo.service;

import com.hatt.shoppingcartdemo.model.CartInfo;
import com.hatt.shoppingcartdemo.model.OrderDetailInfo;
import com.hatt.shoppingcartdemo.model.OrderInfo;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;

import java.util.List;

public interface OrderService {
    void saveOrder(CartInfo cartInfo);
    PaginationResult<OrderInfo> readOrders(int page);
    List<OrderDetailInfo> readOrderDetails(String orderId);
    OrderInfo readOrder(String orderId);
}

package com.hatt.shoppingcartdemo.service.impl;

import com.hatt.shoppingcartdemo.dao.OrderDAO;
import com.hatt.shoppingcartdemo.model.CartInfo;
import com.hatt.shoppingcartdemo.model.OrderDetailInfo;
import com.hatt.shoppingcartdemo.model.OrderInfo;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;
import com.hatt.shoppingcartdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO orderDAO;
    @Override
    public void saveOrder(CartInfo cartInfo) {
        orderDAO.saveOrder(cartInfo);
    }

    @Override
    public PaginationResult<OrderInfo> readOrders(int page) {
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;
        return orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
    }

    @Override
    public List<OrderDetailInfo> readOrderDetails(String orderId) {
        return this.orderDAO.listOrderDetailInfos(orderId);
    }

    @Override
    public OrderInfo readOrder(String orderId) {
        return this.orderDAO.getOrderInfo(orderId);
    }
}

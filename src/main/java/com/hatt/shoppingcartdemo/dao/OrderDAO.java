package com.hatt.shoppingcartdemo.dao;

import com.hatt.shoppingcartdemo.entity.Order;
import com.hatt.shoppingcartdemo.entity.OrderDetail;
import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.model.*;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public class OrderDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ProductDAO productDAO;

    private int getMaxOrderNum() {
        String sql = "Select max(o.orderNum) from " + Order.class.getName() + " o ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<Integer> query = session.createQuery(sql, Integer.class);
        Integer value = (Integer) query.getSingleResult();
        if (value == null) {
            return 0;
        }
        return value;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(CartInfo cartInfo) {
        Session session = this.sessionFactory.getCurrentSession();

        int orderNum = this.getMaxOrderNum() + 1;
        Order order = new Order();

        order.setId(UUID.randomUUID().toString());
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());

        session.persist(order);

        List<CartLineInfo> lines = cartInfo.getCartLines();

        for (CartLineInfo line : lines) {
            OrderDetail detail = new OrderDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setQuanity(line.getQuantity());
            detail.setColourId(line.getProductInfo().getColourId());

            int productId = line.getProductInfo().getProductId();
            Product product = this.productDAO.findProduct(productId);
            detail.setProduct(product);

            session.persist(detail);
        }

        // Order Number!
        cartInfo.setOrderNum(orderNum);
        // Flush
        session.flush();
    }

    // @page = 1, 2, ...
    public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
        String sql = "Select new " + OrderInfo.class.getName()//
                + "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
                + " ord.customerName, ord.customerAddress, ord.customerEmail, ord.customerPhone) " + " from "
                + Order.class.getName() + " ord "//
                + " order by ord.orderNum desc";

        Session session = this.sessionFactory.getCurrentSession();
        Query<OrderInfo> query = session.createQuery(sql, OrderInfo.class);
        return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
    }

    public Order findOrder(String orderId) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Order.class, orderId);
    }

    public OrderInfo getOrderInfo(String orderId) {
        Order order = this.findOrder(orderId);
        if (order == null) {
            return null;
        }
        return new OrderInfo(order.getId(), order.getOrderDate(), //
                order.getOrderNum(), order.getAmount(), order.getCustomerName(), //
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }

    public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {
        String sql = "Select new " + OrderDetailInfo.class.getName() //
                + "(d.id, d.product.productId, d.product.name , d.quanity,d.price,d.amount, d.colourId) "//
                + " from " + OrderDetail.class.getName() + " d "//
                + " where d.order.id = :orderId ";

        Session session = this.sessionFactory.getCurrentSession();
        Query<OrderDetailInfo> query = session.createQuery(sql, OrderDetailInfo.class);
        query.setParameter("orderId", orderId);

        return query.getResultList();
    }

}

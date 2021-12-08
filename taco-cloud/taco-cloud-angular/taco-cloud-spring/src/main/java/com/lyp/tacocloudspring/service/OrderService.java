package com.lyp.tacocloudspring.service;

import com.lyp.tacocloudspring.entity.Order;

import java.util.List;


public interface OrderService {

    Order saveOrder(Order order);

    Order updateOrder(Order order);

    List<Order> findOrderByUserByPlacedAtDesc(String username, int pageNum, int pageLength);

    List<Order> findAllOrder();

    Order findOrderById(String orderId);

    int deleteOrderById(String orderId);

    void saveTacoToOrder(String tacoId, String orderId);
}

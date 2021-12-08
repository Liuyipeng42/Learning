package com.lyp.tacocloudspring.dao;

import com.lyp.tacocloudspring.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;


@Mapper
public interface OrderDao {

    Order saveOrder(Order order);

    List<Order> findOrderByUserByPlacedAtDesc(@Param("username") String username,
                                              @Param("pageNum") int pageNum,
                                              @Param("pageLength") int pageLength);

    List<Order> findAllOrder();

    Order findOrderById(String orderId);

    int deleteOrderById(String orderId);

    Order updateOrder(Order order);

    int saveTacoToOrder(@Param("tacoId") String tacoId,
                             @Param("orderId") String orderId);
}

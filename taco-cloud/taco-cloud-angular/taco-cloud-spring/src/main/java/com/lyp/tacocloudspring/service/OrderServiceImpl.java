package com.lyp.tacocloudspring.service;

import com.lyp.tacocloudspring.dao.IngredientDao;
import com.lyp.tacocloudspring.dao.OrderDao;
import com.lyp.tacocloudspring.dao.TacoDao;
import com.lyp.tacocloudspring.entity.Order;
import com.lyp.tacocloudspring.entity.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private TacoDao tacoDao;
    private IngredientDao ingredientDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao, TacoDao tacoDao, IngredientDao ingredientDao) {
        this.orderDao = orderDao;
        this.tacoDao = tacoDao;
        this.ingredientDao = ingredientDao;
    }

    @Override
    public List<Order> findAllOrder(){
        return orderDao.findAllOrder();
    }

    @Override
    public Order findOrderById(String orderId){
        return orderDao.findOrderById(orderId);
    }

    @Override
    public int deleteOrderById(String orderId) {
        return orderDao.deleteOrderById(orderId);
    }

    @Override
    public Order saveOrder(Order order) {
        orderDao.saveOrder(order);
        String orderId = String.valueOf(order.getId());
        for (Taco taco : order.getTacos()) {
            saveTacoToOrder(String.valueOf(taco.getId()), orderId);
        }
        return order;
    }

    @Override
    public Order updateOrder(Order order){
        return orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findOrderByUserByPlacedAtDesc(String username, int pageNum, int pageLength) {
        List<Order> orders = orderDao.findOrderByUserByPlacedAtDesc(username, pageNum, pageLength);
        List<Taco> tacos;
        for (Order order: orders) {
            tacos = tacoDao.getTacosByOrderId(String.valueOf(order.getId()));
            for(Taco taco: tacos){
                taco.setIngredients(ingredientDao.getIngredientsByTacoId(String.valueOf(taco.getId())));
            }
            order.setTacos(tacos);
        }
        return orders;
    }

    @Override
    public void saveTacoToOrder(String tacoId, String orderId) {
        orderDao.saveTacoToOrder(tacoId, orderId);
    }
}

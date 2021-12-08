package tacos.service;

import tacos.domain.Order;
import tacos.domain.User;

import java.util.List;


public interface OrderService {

    String saveOrder(Order order);

    List<Order> findByUserOrderByPlacedAtDesc(String username, int pageNum, int pageLength);

    int saveTacoToOrder(String tacoId, String orderId);
}

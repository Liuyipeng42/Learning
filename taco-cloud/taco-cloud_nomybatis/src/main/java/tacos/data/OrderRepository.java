package tacos.data;

import tacos.domain.Order;
import tacos.domain.User;

import java.util.List;

public interface OrderRepository {
    Order save(Order order);

    List<Order> findByUserOrderByPlacedAtDesc(User user);
}
package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tacos.dao.IngredientDao;
import tacos.dao.OrderDao;
import tacos.dao.TacoDao;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.domain.User;
import tacos.service.OrderService;


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
    public String saveOrder(Order order) {
        orderDao.saveOrder(order);
        String orderId = String.valueOf(order.getId());
        for (Taco taco : order.getTacos()) {
            saveTacoToOrder(String.valueOf(taco.getId()), orderId);
        }
        return orderId;
    }

    @Override
    public List<Order> findByUserOrderByPlacedAtDesc(String username, int pageNum, int pageLength) {
        List<Order> orders = orderDao.findByUserOrderByPlacedAtDesc(username, pageNum, pageLength);
        List<Taco> tacos;
        for (Order order: orders) {
            tacos = tacoDao.getTacosByOrderId(String.valueOf(order.getId()));
            for(Taco taco: tacos){
                taco.setIngredientList(ingredientDao.getIngredientsByTacoId(String.valueOf(taco.getId())));
            }
            order.setTacos(tacos);
        }
        return orders;
    }

    @Override
    public int saveTacoToOrder(String tacoId, String orderId) {
        return orderDao.saveTacoToOrder(tacoId, orderId);
    }
}

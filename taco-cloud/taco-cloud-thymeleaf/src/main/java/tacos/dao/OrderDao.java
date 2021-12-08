package tacos.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tacos.domain.Order;
import tacos.domain.User;

import java.util.List;


@Mapper
public interface OrderDao {

    int saveOrder(Order order);

    List<Order> findByUserOrderByPlacedAtDesc(@Param("username") String username,
                                              @Param("pageNum") int pageNum,
                                              @Param("pageLength") int pageLength);

    int saveTacoToOrder(@Param("tacoId") String tacoId,
                             @Param("orderId") String orderId);
}

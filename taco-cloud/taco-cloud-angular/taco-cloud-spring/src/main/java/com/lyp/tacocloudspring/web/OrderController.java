package com.lyp.tacocloudspring.web;

import com.lyp.tacocloudspring.entity.Order;
import com.lyp.tacocloudspring.service.OrderService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders",
        produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Order> allOrders() {
        return orderService.findAllOrder();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public Order putOrder(@PathVariable String orderId, @RequestBody Order order) {
        order.setId(Long.valueOf(orderId));
        return orderService.updateOrder(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") String orderId,
                            @RequestBody Order patch) {

        Order order = orderService.findOrderById(orderId);
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getId() != null) {
            order.setId(patch.getId());
        }
        if (patch.getCity() != null) {
            order.setCity(patch.getCity());
        }
        if (patch.getName() != null) {
            order.setName(patch.getName());
        }
        if (patch.getPlacedAt() != null) {
            order.setPlacedAt(patch.getPlacedAt());
        }
        if (patch.getState() != null) {
            order.setState(patch.getState());
        }
        if (patch.getStreet() != null) {
            order.setStreet(patch.getStreet());
        }
        if (patch.getUser() != null) {
            order.setUser(patch.getUser());
        }
        if (patch.getZip() != null) {
            order.setZip(patch.getZip());
        }
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") String orderId) {
        try {
            orderService.deleteOrderById(orderId);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

}

package tacos.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import tacos.domain.Order;
import tacos.domain.User;
import tacos.service.OrderService;

import java.util.Date;
import java.util.List;


@Slf4j
@Data
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@ConfigurationProperties(prefix="orderlist")
public class OrderController {

    private final OrderService orderService;
    private int pageNum = 1;

    private int pageLength;

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    public int getPageLength() {
        return pageLength;
    }

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/current")
    // 处理对路径 “/current” 的请求，如果针对 “/current“ 发送 HTTP GET 请求，那么这个方法将会处理请求
    public String orderForm() {
        return "orderForm";
    }

    // @AuthenticationPrincipal 获取已认证的用户信息
    // Order order: 将 session中的 order的对象赋值给 order
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Processing order: " + order);

        order.setUser(user);

        order.setPlacedAt(new Date());
        orderService.saveOrder(order);
        sessionStatus.setComplete();

        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

        return "redirect:/";
    }

    @GetMapping( "/orderList")
    public String ordersForUserFirstPage(@AuthenticationPrincipal User user, Model model) {
        System.out.println(pageLength);
        model.addAttribute("pagenum", pageNum);
        model.addAttribute("orders",
                orderService.findByUserOrderByPlacedAtDesc(user.getUsername(), 0, pageLength));
        return "orderList";
    }

    @GetMapping(value ="/orderList", params = "page")
    public String ordersForUser(@AuthenticationPrincipal User user, Model model,
                                @RequestParam(name = "page") Integer page) {

        pageNum ++;
        List<Order> orderList = orderService.findByUserOrderByPlacedAtDesc(user.getUsername(), page, pageLength);
        if (orderList.size() == 0){
            pageNum --;
            model.addAttribute("orders",
                    orderService.findByUserOrderByPlacedAtDesc(user.getUsername(), page - 1, pageLength));
            model.addAttribute("lastPage", "最后一页");
        }else {
            model.addAttribute("orders", orderList);
        }

        model.addAttribute("pagenum", pageNum);
        return "orderList";
    }
}

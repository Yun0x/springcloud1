package cn.tedu.sp04.controller;
import cn.tedu.sp01.entity.Item;
import cn.tedu.sp01.entity.Order;
import cn.tedu.sp01.entity.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 获取订单
    @GetMapping("/{orderId}")
    public JsonResult<Order> getOrder(
            @PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        return JsonResult.ok().data(order);
    }
    // 添加订单
    @GetMapping("/add")
    public JsonResult<?> addOrder() {
        // Demo 数据
        Order order = new Order();
        order.setId("kj65h4g43fvc");
        order.setUser(new User(8,null,null));
        order.setItems(Arrays.asList(new Item[]{
                new Item(1,"商品1",1),
                new Item(2,"商品2",3),
                new Item(3,"商品3",6),
                new Item(4,"商品4",3),
                new Item(5,"商品5",2)
        }));
        orderService.addOrder(order);
        return JsonResult.ok().msg("添加订单成功");
    }

    @GetMapping("/favicon.ico")
    public void ico() {
    }

}

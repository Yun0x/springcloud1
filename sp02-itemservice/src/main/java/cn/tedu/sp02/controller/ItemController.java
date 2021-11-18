package cn.tedu.sp02.controller;
import cn.tedu.sp01.entity.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class ItemController {
    @Autowired
    private ItemService itemService;

    // 获取订单商品列表
    @GetMapping("/{orderId}")
    public JsonResult<List<Item>> getItems(
            @PathVariable String orderId) throws InterruptedException {
        List<Item> items = itemService.getItems(orderId);



        // 延迟代码，90%概率执行延迟
        if (Math.random() < 0.9) {
            // 阻塞代码，随机 [0,5000)
            int t = new Random().nextInt(5000);
            log.info("阻塞时间： "+t);
            Thread.sleep(t);
        }

        return JsonResult.ok().data(items);
    }
    // 减少商品库存
    // @RequestBody 完整地接收 post 请求协议体数据
    // [{id:1,name:xxx,number:1},{...},{...}]
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(
            @RequestBody List<Item> items) {
        itemService.decreaseNumber(items);
        return JsonResult.ok().msg("减少库存成功");
    }

    @GetMapping("/favicon.ico")
    public void ico() {
    }
}

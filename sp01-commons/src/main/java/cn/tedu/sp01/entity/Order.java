package cn.tedu.sp01.entity; // pojo, vo, po
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id; // 202111150008iuj465u7j545u
    private User user; // 订单所属的用户
    private List<Item> items;  // 订单中的商品列表
}

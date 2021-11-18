package cn.tedu.sp06.filter;
import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {
    // 设置过滤器的类型：pre, routing, post, error
    @Override
    public String filterType() {
        // return "pre";
        return FilterConstants.PRE_TYPE;
    }

    // 设置顺序号
    @Override
    public int filterOrder() {
        // 前置过滤器中有5个默认的过滤器，自定义过滤器放到末尾
        // 第5个过滤器中，向上下文对象放入了 serviceId
        // 后面过滤器中才能访问这个数据
        return 6;
    }
    /*
    针对当前请求，是否要执行下面的过滤代码
     */
    @Override
    public boolean shouldFilter() {
        /*
        调用商品，需要判断权限，
        调用用户或订单不检查权限
         */

        // 获得一个请求上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        // 从上下文对象获得调用的后台服务的 serviceId
        String serviceId =
            (String) ctx.get(FilterConstants.SERVICE_ID_KEY);// "serviceId"
        // 如果调用的是 item-service，返回true
        return "item-service".equals(serviceId);
    }
    // 过滤代码
    @Override
    public Object run() throws ZuulException {
        // http://localhost:3001/item-service/iuy4tgf3?token=uy4t34t
        // 获得上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获得 request 对象
        HttpServletRequest request = ctx.getRequest();
        // 接收 token 参数
        String token = request.getParameter("token");
        // 如果 token 不存在: null, "", "    "
        if (StringUtils.isBlank(token)) {
            // 阻止继续调用
            ctx.setSendZuulResponse(false);
            // 直接返回响应
            // JsonResult - {code:400, msg:未登录, data:null}
            String json = JsonResult
                                .build()
                                .code(400)
                                .msg("Not Login! 未登录！")
                                .toString();
            ctx.addZuulResponseHeader(
                    "Content-Type", "application/json;charset=UTF-8");
            ctx.setResponseBody(json);
        }

        return null; //zuul当前版本这个返回值不起任何作用
    }
}

package cn.tedu.sp06.fb;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OrderFB implements FallbackProvider {
    /*
    设置针对哪个后台服务进行降级
      - item-service:  只对商品服务降级
      - *:     对所有服务都应用当前降级类
      - null:  对所有服务都应用当前降级类
     */
    @Override
    public String getRoute() {
        return "order-service";
    }
    /*
    向客户端返回的响应数据
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }
            @Override
            public void close() {
                // 用来关闭下面的流
                // BAIS不占用底层系统资源，不需要关闭
            }
            @Override
            public InputStream getBody() throws IOException {
                // JsonResult - {code:500, msg:xxx, data:null}
                String json = JsonResult
                                .build()
                                .code(500)
                                .msg("后台服务出错，请稍后重试！")
                                .toString();
                // 字符串变成byte[]数组，封装到 BAIS
                return new ByteArrayInputStream(json.getBytes("UTF-8"));
            }
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders h = new HttpHeaders();
                h.add("Content-Type","application/json;charset=UTF-8");
                return h;
            }
        };
    }
}

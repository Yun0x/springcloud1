**统一的入口**

1. 新建 spring 模块： sp06-zuul

2. 添加依赖：

    - eureka client

    - zuul
    - sp01

3. yml

   ```yml
   # 转发规则
   zuul:
     routes:
     	# ** 包含深层子路径
     	# *  只包含一层路径
     	# service-id 作为访问子路径，是默认设置
     	# 根据注册表中的注册信息，zuul可以自动配置
     	# 最好自己手动配置，防止注册表不全
       item-service: /item-service/**
       user-service: /user-service/**
       order-service: /order-service/**
   ```

4. 启动类注解：`@EnableZuulProxy`



**统一的权限校验**

zuul 的过滤器 ZuulProxy，可以过滤客户端请求，在过滤器中可以检查访问权限



http://localhost:3001/item-service/iuy4tgf3		没有登录，不允许访问

http://localhost:3001/item-service/iuy4tgf3?token=iu5y4t3tg3		已登录，可以访问

1. 新建过滤器类：AccessFilter  按照 Zuul 的规则实现
2. 添加注解 `@Component`

- zuul的自动配置，会在 spring 容器中发现过滤器实例，完成自动配置



- 启用重试

  - 添加 spring-retry 依赖
  - yml 配置启用重试： `zuul.retryable=true`
  - 如果需要可以配置重试参数
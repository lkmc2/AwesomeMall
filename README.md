# AwesomeMall
基于SSM的电商**后端API服务**

## 项目功能
+ 商品管理
+ 发货地址管理
+ 订单管理
+ 购物车管理
+ 分类管理
+ 用户管理
+ 支付宝支付

## 项目使用的技术

- 框架：Spring 4.3.7.RELEASE、Spring MVC 4.3.7.RELEASE、Mybatis 3.4.1

- 数据库：MySQL 5.7

- API文档：Swagger2

- 插件：Druid连接池、Mybatis Generator、PageHelper分页插件、Jackson、Logback、Google Guava、Joda Time、Apache Commons



## 项目运行方式

1. 创建数据库mmall。

2. 在数据库中运行data.sql文件。

3. 修改src/main/resources/datasource.properties配置文件中的的数据库用户名和密码。

4. 将项目以Web Application运行。

5. 浏览器中打开http://localhost:8080/swagger-ui.html ，可访问到项目的API文档。

<img src="https://raw.githubusercontent.com/lkmc2/AwesomeMall/master/picture/Swagger2.png"/>
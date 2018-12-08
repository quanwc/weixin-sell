## weixin-sell项目：
<br>

### 项目介绍：
#### Spring Boot微信点餐系统 <br>
##### 技术要点：项目采用前后端分离，通过RESTful接口进行通信
- 前端：Vue -> WebApp
- 后端：SpringBoot + Bootstrap + Freemarker + jQuery<br>
        数据库：SpringBoot + JPA、SpringBoot + MyBatis<br>
        缓存：SpringBoot + Redis（分布式session、分布式锁）<br>
        消息推送：WebSocket<br>
- 微信相关特性：扫码登录、模板消息推送、微信支付和退款
##### 功能分析：
- 买家（微信端），是一个基于微信服务号点餐<br>
- 卖家（PC端），是一个后台管理系统<br>
功能关系图：
![image](src/main/resources/images/image1.jpg)<br>
部署架构图：
![image](src/main/resources/images/image2.jpg)







- 时间：        
    startDate：2018-12-8
    endDate:
    
<br>

>### 环境参数：
    SpringBoot：1.5.10
    JDK 1.8.0_111   
    Idea 2017.3.4
    Maven 3.3.9
    Redis 3.2.100
    Nginx 1.11.7
    开发环境 Linux
    部署环境 centos7.3

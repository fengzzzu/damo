# damo
一个脚手架，基于jdk1.7,spring-boot1.5.11,dubbo2.5.10

交流QQ群：

## 前言

  damo项目专注编码规范，开发速度。探索微服务。
  

### 组织结构

``` lua
damo
├── damo-parent -- 引用定义[v1.0]
├── damo-api -- 统一网关[v1.0]
├── damo-api-test -- 统一网关的集成测试[v1.0]
├── damo-service -- dubbo服务[v1.0]
|    ├── damo-service-api -- 服务定义[v1.0]
|    ├── damo-service-consumer -- 服务调用者[v1.0]
|    └── damo-service-provider -- 服务提供者[v1.0]
```

### 技术选型

技术 | 名称 | 官网
----|------|----
Apache Shiro | 安全框架  | [http://shiro.apache.org/](http://shiro.apache.org/)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Dubbo | 分布式服务框架  | [http://dubbo.io/](http://dubbo.io/)
Redis | 分布式缓存数据库  | [https://redis.io/](https://redis.io/)
Swagger2 | 接口测试框架  | [http://swagger.io/](http://swagger.io/)
Jenkins | 持续集成工具  | [https://jenkins.io/index.html](https://jenkins.io/index.html)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)

TBSchedule & elastic-job | 分布式调度框架  | [https://github.com/dangdangdotcom/elastic-job](https://github.com/dangdangdotcom/elastic-job)
Quartz | 作业调度框架  | [http://www.quartz-scheduler.org/](http://www.quartz-scheduler.org/)



项目编译  mvn clean install -DskipTests

# docker

## easyz-jingle

- cd easyz-jingle
- docker compose up

## nacos

- cd nacos-docker
- docker-compose -f example/standalone-derby.yaml up
- [http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/?spm=5238cd80.382dab05.0.0.48d42909WuMTty)

# nacos

## all-services.yaml

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 30MB
      max-request-size: 30MB
  zipkin:
    base-url: http://centos:9411/  #zipkin server的请求地址
    discoveryClientEnabled: false #让nacos把它当成一个URL，而不要当做服务名
  sleuth:
    sampler:
      probability: 1.0  #采样的百分比
  data:
    mongodb:
      uri: mongodb://root:newborne@centos:27017/easyz_jingle
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://centos:3306/easyz_jingle?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&useSSL=false
    username: root
    password: newborne
  cloud:
    nacos:
      discovery:
        server-addr: centos:8848
    sentinel:
      transport:
        port: 9999 #跟控制台交流的端口,随意指定一个未使用的端口即可
        dashboard: centos:8858 # 指定控制台服务的地址
  redis:
    jedis:
      pool:
        max-wait: 5000ms
        max-Idle: 100
        min-Idle: 10
    timeout: 10s
    host: centos
    port: 6379
    password: newborne
mybatis-plus:
  type-enums-package: cn.easyz.common.model.enums
  global-config:
    db-config:
      id-type: auto
rocketmq:
  name-server: centos:9876
  producer:
    group: EASYZ_PRODUCER_GROUP
ribbon:
  ReadTimeout: 5000
  ConnectTimeout: 5000
feign:
  sentinel:
    enabled: true # 开启feign对sentinel的支持
fdfs:
  so-timeout: 1501
  connect-timeout: 601
  thumb-image.width: 150
  thumb-image.height: 150
  tracker-list: 
    - centos:22122
  web-server-url: centos:8888
```

### easyz-login/bootstrap.yml

```yaml
spring:
  application:
    name: easyz-login
  cloud:
    nacos:
      config:
        server-addr: centos:8848 # nacos的服务端地址
        file-extension: yaml
        shared-dataids: all-service.yaml # 配置要引入的配置
        refreshable-dataids: all-service.yaml # 配置要实现动态配置刷新的配置
  profiles:
    active: dev # 环境标识
```

#### easyz-login.yaml

```yaml
server:
  port: 18081
config:
  appName: easyz-login
spring:
  application:
    name: easyz-login

```

#### easyz-login-dev.yaml

```yaml
config:
  env: dev
```

#### easyz-login-test.yaml

```yaml
config:
  env: test
```

### easyz-server/bootstrap.yaml

```yaml
spring:
  application:
    name: easyz-server
  cloud:
    nacos:
      config:
        server-addr: centos:8848 # nacos的服务端地址
        file-extension: yaml
        shared-dataids: all-service.yaml # 配置要引入的配置
        refreshable-dataids: all-service.yaml # 配置要实现动态配置刷新的配置
  profiles:
    active: dev # 环境标识
dubbo:
  registry:
    address: spring-cloud://centos # 注册中心
  cloud:
    subscribed-services: easyz-dubbo  # 订阅的提供者名称
```

#### easyz-server.yaml

```yaml
server:
  port: 18083
config:
  appName: easyz-server
spring:
  application:
    name: easyz-server
easyz:
  cache:
    enable: true

```

### easyz-dubbo/bootstrap.yaml

```yaml
spring:
  application:
    name: easyz-dubbo
  cloud:
    nacos:
      config:
        server-addr: centos:8848 # nacos的服务端地址
        file-extension: yaml
        shared-dataids: all-service.yaml # 配置要引入的配置
        refreshable-dataids: all-service.yaml # 配置要实现动态配置刷新的配置
  profiles:
    active: dev # 环境标识
dubbo:
  scan:
    base-packages: com.easyz.dubbo.api # 开启包扫描
  protocols:
    dubbo:
      name: dubbo # 服务协议
      port: -1 # 服务端口
  registry:
    address: spring-cloud://centos # 注册中心

```

#### easyz-dubbo.yaml

```yaml
server:
  port: 18082
config:
  appName: easyz-dubbo
spring:
  application:
    name: easyz-dubbo

```

### easyz-neo4j/bootstrap.yaml

```yaml
spring:
  application:
    name: easyz-neo4j
  cloud:
    nacos:
      config:
        server-addr: centos:8848 # nacos的服务端地址
        file-extension: yaml
        shared-dataids: all-service.yaml # 配置要引入的配置
        refreshable-dataids: all-service.yaml # 配置要实现动态配置刷新的配置
  profiles:
    active: dev # 环境标识
```

#### easyz-neo4j.yaml

```yaml
server:
  port: 18084
config:
  appName: easyz-neo4j
spring:
  application:
    name: easyz-neo4j
  data:
    neo4j:
      uri: bolt://centos:7687
      username: neo4j
      password: newborne
  

```

#### easyz-neo4j-dev.yaml

```yaml
config:
  env: dev
```

#### easyz-neo4j-test.yaml

```yaml
config:
  env: test
```



### easyz-gateway/application.yaml

```yaml
server:
  port: 7000
spring:
  servlet:
    multipart:
      max-file-size: 30MB
      max-request-size: 30MB
  application:
    name: easyz-gateway
  cloud:
    nacos:
      discovery:
        server-addr: centos:8848 # 将gateway注册到nacos
    gateway:
      discovery:
        locator:
          enabled: true # 让gateway可以发现nacos中的微服务
      routes:
        - id: easyz-login
          uri: lb://easyz-login # lb指的是从nacos中按照名称获取微服务,并遵循负载均衡策略
          predicates:
            - Path=/easyz-login/**
          filters:
            - StripPrefix=1
        - id: easyz-server
          uri: lb://easyz-server # lb指的是从nacos中按照名称获取微服务,并遵循负载均衡策略
          predicates:
            - Path=/easyz-server/**
          filters:
            - StripPrefix=1
        - id: easyz-neo4j
          uri: lb://easyz-neo4j # lb指的是从nacos中按照名称获取微服务,并遵循负载均衡策略
          predicates:
            - Path=/easyz-neo4j/**
          filters:
            - StripPrefix=1
  profiles:
    active: dev # 环境标识
```

# neo4j

```cypher
// mysql迁移用户信息
CALL apoc.load.jdbc(
'jdbc:mysql://localhost/yizhi_scrip?user=root&password=密码&useUnicode=true&characterEncoding=utf8',
'select user_id,nick_name,logo,tags,sex,age,edu,city,birthday ,cover_pic,industry from ap_user_info'
) YIELD row CREATE (n:UserNode {user_id: row.user_id, nick_name: row.nick_name,logo:row.logo,tags:row.tags,sex:row.sex,age:row.age,edu:row.edu,city:row.city,birthday:row.birthday,cover_pic:row.cover_pic,industry:row.industry})

// 查询user_id
MATCH (p1:UserNode{user_id:1}) RETURN p1
// 查询用户信息
match(x:UserNode{}) return x
// 查询friend联系
MATCH (p1:UserNode)-[r:user_recommend]-(p2:UserNode) RETURN p1,p2,r
// 删除user_recommend联系
MATCH (p1:UserNode)-[r:user_recommend]-(p2:UserNode) DELETE r

// 查询articleType
match(x:ArticleTypeNode{}) return x
// 查询article_belong
MATCH (p1:ArticleNode)-[r:article_belong]-(p2:ArticleTypeNode) RETURN p1,p2,r
// 删除article_belong
MATCH (p1:ArticleNode)-[r:article_belong]-(p2:ArticleTypeNode) DELETE p1,r
// 查询article_recommend
MATCH (p1:ArticleNode)-[r:article_recommend]-(p2:UserNode) RETURN p1,p2,r
// 删除article_recommend
MATCH (p1:ArticleNode)-[r:article_recommend]-(p2:UserNode) DELETE r
// 查询article_belong和article_recommend
MATCH (p1:ArticleNode)-[r:article_belong]-(p2:ArticleTypeNode) ,(p1)-[r1:article_recommend]-(p3:UserNode) RETURN p1,p2,r,p3,r1

// 查询material_belong
MATCH (p1:MaterialNode)-[r:material_belong]-(p2:MaterialTypeNode) RETURN p1,p2,r
// 删除material_belong
MATCH (p1:MaterialNode)-[r:material_belong]-(p2:MaterialTypeNode) DELETE p1,r
// 查询material_recommend
MATCH (p1:MaterialNode)-[r:material_recommend]-(p2:UserNode) RETURN p1,p2,r
// 删除material_recommend
MATCH (p1:MaterialNode)-[r:material_recommend]-(p2:UserNode) DELETE r
// 查询material_belong和material_recommend
MATCH (p1:MaterialNode)-[r:material_belong]-(p2:MaterialTypeNode) ,(p1)-[r1:material_recommend]-(p3:UserNode) RETURN p1,p2,r,p3,r1

// 查询所有
MATCH (p1)
OPTIONAL MATCH (p1)-[r]-(p2)
RETURN p1,r,p2
// 删库
MATCH (n)
OPTIONAL MATCH (n)-[r]-()
DELETE n,r
```




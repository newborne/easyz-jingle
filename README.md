# 易知创作平台 easyz-jingle

## 项目简介

易知创作平台是一个基于微服务架构的智能创作平台，旨在为用户提供了一个高效、智能的创作环境。平台整合了大语言模型技术，提供从创作灵感获取、素材推荐、在线创作到社交互动的全方位支持。

## 核心功能

- **智能推荐**：基于用户兴趣和行为的个性化创作推荐。
- **在线创作**：支持实时保存、动态修改段落和智能素材推荐。
- **社交互动**：用户可以分享创作、建立联系、参与社区讨论。
- **知识图谱**：通过 Neo4j 构建用户和创作的关系图谱。
- **多端支持**：支持 Web 和移动端，提供一致的用户体验。

## 技术栈

- **后端**：Java、Spring Cloud、Dubbo、MyBatis-Plus
- **数据库**：MongoDB、MySQL、Redis、Neo4j
- **消息队列**：RocketMQ
- **服务治理**：Nacos、Sentinel
- **前端**：Vue.js、Element UI

## 项目结构

```
easyz-jingle/
├── easyz-login/              # 登录服务
├── easyz-server/             # 核心服务
├── easyz-dubbo/              # Dubbo 服务
├── easyz-neo4j/              # Neo4j 服务
├── easyz-gateway/            # 网关服务
├── nacos-docker/             # Nacos 配置中心
├── docker-compose.yml        # Docker 配置
└── README.md                 # 项目说明
```

## 快速开始

### 克隆项目

```bash
git clone https://github.com/newborne/easyz-jingle.git
```

### 启动项目

1. **启动 Nacos 配置中心**

```bash
cd nacos-docker
docker-compose -f example/standalone-derby.yaml up
```

访问 Nacos 控制台：[http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/)

2. **启动项目服务**

```bash
cd easyz-jingle
docker compose up
```

### 配置说明

- **数据库配置**：MongoDB、MySQL、Redis 和 Neo4j 的连接信息在 `all-services.yaml` 中配置。
- **服务端口**：各服务的端口和配置在 `bootstrap.yaml` 和 `application.yaml` 中定义。

## 贡献指南

欢迎贡献代码和建议！请参考 [CONTRIBUTING.md](CONTRIBUTING.md) 了解如何参与项目。

## 联系方式

- **邮箱**：<newborne@foxmail.com>
- **GitHub**：<https://github.com/newborne/easyz-jingle>

## 标签

# 智能创作 #大语言模型 #微服务 #SpringCloud #Neo4j #推荐系统 #前后端分离

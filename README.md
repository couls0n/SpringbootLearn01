# Spring Boot 最小 Demo

这是一个最小可运行的 Spring Boot 项目，包含：

- 一个启动类
- 一个控制器
- 两个接口：`GET /` 和 `GET /hello`

## 运行方式

在项目根目录执行：

```powershell
mvn spring-boot:run
```

启动后访问：

- `http://localhost:8080/`
- `http://localhost:8080/hello`
- `http://localhost:8080/hello?name=mos`

## 项目结构

- `src/main/java/com/example/moslearn/MosLearnDemoApplication.java`
- `src/main/java/com/example/moslearn/HelloController.java`
- `src/main/resources/application.properties`

## 下一步可以学什么

你可以继续在这个 demo 上练习：

1. 改成返回 JSON
2. 接收 `POST` 请求
3. 连接数据库
4. 分层成 `controller/service/repository`

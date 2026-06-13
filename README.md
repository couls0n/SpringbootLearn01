# Spring Boot Demo

这是一个用于学习 Spring Boot 的最小后端 demo，当前包含：

- `controller / service / repository` 分层
- DTO 参数接收和校验
- 全局异常处理
- MySQL + Spring Data JPA 持久化
- 用户 CRUD 接口

## MySQL 准备

项目默认连接本机 MySQL：

- 开发库：`moslearn`
- 测试库：`moslearn_test`
- 用户名：`moslearn`
- 密码：`moslearn123`

先用 MySQL root 账号执行初始化脚本：

```powershell
mysql -uroot -p < docs/mysql-setup.sql
```

如果你的 MySQL 账号不同，也可以用环境变量覆盖：

```powershell
$env:MYSQL_USERNAME="your_user"
$env:MYSQL_PASSWORD="your_password"
$env:MYSQL_URL="jdbc:mysql://localhost:3306/moslearn?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&characterEncoding=utf8"
mvn spring-boot:run
```

测试库也可以单独覆盖：

```powershell
$env:MYSQL_TEST_USERNAME="your_user"
$env:MYSQL_TEST_PASSWORD="your_password"
$env:MYSQL_TEST_URL="jdbc:mysql://localhost:3306/moslearn_test?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&characterEncoding=utf8"
mvn test
```

## 运行

```powershell
mvn spring-boot:run
```

常用接口：

- `GET http://localhost:8080/api/index`
- `GET http://localhost:8080/api/hello?name=mos`
- `GET http://localhost:8080/api/users`
- `POST http://localhost:8080/api/users`
- `PUT http://localhost:8080/api/users/{id}`
- `DELETE http://localhost:8080/api/users/{id}`

创建用户请求体示例：

```json
{
  "name": "Alice",
  "email": "alice@example.com"
}
```

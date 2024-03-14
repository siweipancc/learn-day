# JavaFX 整合 spring-boot

## 相关点

- 控制器工厂
- 自动注入耦合
- 非单例模式
- 上下文传递的时机
- 生命周期
- 委托模式

### 控制器工厂

`FXMLLoader` 类包含一个控制器工厂的设置 `setControllerFactory`, 这里我们委托 Spring, 同时每个 **loader** 只能由一个根,
即使用 `prototype`.

### 自动注入耦合

见 [控制器工厂](#控制器工厂).

### 非单例模式

参见 JavaFX 设计理念.

### 上下文传递的时机

常见的应用拿到上下文时 Spring 容器已经初始化完毕, 考虑用 `ApplicationStartedEvent` 事件传递.

### 生命周期

**JavaFX** 的 `close` 方法应该通知 DI 容器

### 委托模式

程序的启动通过 Spring 入口.

## 待考虑问题

程序应该有一个主要驻留界面, 即 `MainController`, `main-view.fxml`, 此时的 `MainController` 改为单例, `SubController`
的 `stage` 应为 新的单例 **stage2**, 如果由另一个子界面, 应该复用 **stage2**
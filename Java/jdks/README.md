# jdks

## 常规

- Lambda
    - [JSR 335](https://cr.openjdk.org/~dlsmith/jsr335/jsr335-0.6.2/index.html)
    - _相关代码_
        - [UnaryOperators.java](src/main/java/com/pancc/learn/jdks/function/UnaryOperators.java): 二元化
- URLConnection
    - _相关代码_
        - [MineTy.java](src/main/java/com/pancc/learn/jdks/web/MineTy.java): 文件 mimetype 映射表
- 系统编码
    - [JEP 400](https://openjdk.org/jeps/400)
    - _相关代码_
        - [Charsets.java](src/main/java/com/pancc/learn/jdks/platform/Charsets.java)
- 本地进程调用
    - [JEP 8263697](https://openjdk.org/jeps/8263697)
    - _相关代码_
        - [Processes.java](src/main/java/com/pancc/learn/jdks/platform/Processes.java): 打印临时文件夹下的内容
        - [ProcessCombineFiles.java](src/main/java/com/pancc/learn/jdks/platform/ProcessCombineFiles.java): 合并文件
- HTTP 客户端
    - [JEP 321](https://openjdk.org/jeps/321)
    - _相关代码_
        - [HttpClients.java](src/main/java/com/pancc/learn/jdks/web/HttpClients.java): 下载图片
- HTTP Web 服务器
    - [JEP 408](https://openjdk.org/jeps/408)
    - _相关代码_
        - [SimpleWebServer.java](src/main/java/com/pancc/learn/jdks/web/SimpleWebServer.java): 简易 web 服务器
        - [SimpleTmpFileServer.java](src/main/java/com/pancc/learn/jdks/web/SimpleTmpFileServer.java): 简易文件 web 服务器

## JDK 21

- 虚拟线程
    - [JEP 444](https://openjdk.org/jeps/444)
    - 相关代码
        - [VirtualThreads.java](src/main/java/com/pancc/learn/jdks/concurrent/VirtualThreads.java)
- 闭包\(Preview)
    - [JEP 446](https://openjdk.org/jeps/446)
    - 相关代码
        - [ScopedValues.java](src/main/java/com/pancc/learn/jdks/concurrent/ScopedValues.java): 匿名传递参数
- 结构化并发\(Preview)
    - [JEP 462](https://openjdk.org/jeps/462)
    - 相关代码
        - [StructuredTasks.java](src/main/java/com/pancc/learn/jdks/concurrent/StructuredTasks.java): 结构化并发
        - [ScopedStructuredTask.java](src/main/java/com/pancc/learn/jdks/concurrent/ScopedStructuredTask.java): 结构化并发闭包
- Switch 模式匹配
    - [JEP 441](https://openjdk.org/jeps/441)
    - _相关代码_
        - [SwitchPatterns.java](src/main/java/com/pancc/learn/jdks/switchs/SwitchPatterns.java)
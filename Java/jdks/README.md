# jdks

## 常规

- Lambda
    - [JSR 335](https://cr.openjdk.org/~dlsmith/jsr335/jsr335-0.6.2/index.html)
    - _相关代码_
        - [UnaryOperators.java](src/main/java/com/pancc/learn/jdks/function/UnaryOperators.java): 二元化
- URLConnection
    - _相关代码_
        - [MineTypes.java](src/main/java/com/pancc/learn/jdks/web/minetypes/MineTypes.java): 文件 MineTypes 映射表
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

## Web 相关

- 206 分段请求
    - _相关代码_
        - [RangeRequest.java](src/main/java/com/pancc/learn/jdks/web/ranges/RangeRequest.java): 分段下载并合并
        - [RangesGenerator.java](src/main/java/com/pancc/learn/jdks/web/ranges/RangesGenerator.java): 分段的流产生器, 避免
          OOM
        - [RangesGeneratorDemo1.java](src/main/java/com/pancc/learn/jdks/web/ranges/demo/RangesGeneratorDemo1.java):
          测试代码, 这个类创建的大量对象在主线程未释放
        - [RangesGeneratorDemo2.java](src/main/java/com/pancc/learn/jdks/web/ranges/demo/RangesGeneratorDemo2.java):
          测试代码, 这个类创建的大量对象在线程中及时释放
    - [rfc9110](https://www.rfc-editor.org/rfc/rfc9110#field.accept-ranges)
    - [IntelliJ IDEA 插件](https://plugins.jetbrains.com/plugin/253-jprofiler): JProfiler

## 虚拟机相关

- 对象内存占用
    - _相关代码_
        - [EntityMm.java](src/main/java/com/pancc/learn/jdks/vm/EntityMm.java): 内存打印
    - [IntelliJ IDEA 插件](https://plugins.jetbrains.com/plugin/10953-jol-java-object-layout)
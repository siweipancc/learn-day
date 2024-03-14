package com.pancc.learn.spring.boot.jfx;

import jakarta.annotation.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author Siweipancc
 */
@Scope("prototype")
@Service("delegateApplication")
public class DelegateApplication extends Application implements ApplicationListener<ApplicationStartedEvent>, DisposableBean {
    final static Logger log = LoggerFactory.getLogger(DelegateApplication.class);
    private static ConfigurableApplicationContext context;

    @Value("classpath:view/main-view.fxml")
    org.springframework.core.io.Resource mainView;

    @Resource
    ConfigurableListableBeanFactory configurableListableBeanFactory;
    @Resource
    FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws IOException {
        log.debug(primaryStage.toString());
        configurableListableBeanFactory.registerSingleton("primaryStage", primaryStage);
        DelegateApplication origin = configurableListableBeanFactory.getBean(getClass());
        Assert.isTrue(origin != this, "not consist bean");
        Scene scene = new Scene(fxmlLoader.load(mainView.getInputStream()), 320, 240);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void init() {
        // 重新处理注入等属性
        context.getAutowireCapableBeanFactory().autowireBean(this);
        log.debug("init");
        ConfigurableListableBeanFactory factory = context.getBeanFactory();
        factory.registerSingleton("hostServices", getHostServices());
        factory.registerSingleton("parameters", getParameters());
    }

    @Override
    public void stop() {
        log.debug("stop");
        if (context.isRunning()) {
            context.stop();
        }

    }


    /**
     * 使用 Started 拿到刚初始化的 context.
     *
     * @param event the event to respond to
     * @see #context
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.debug(event.toString());
        // 从 spring 实例唤醒
        context = event.getApplicationContext();
        // launch 由 Application 创建之后是一个新实例 见 init
        Application.launch(DelegateApplication.class, event.getArgs());

    }

    @Override
    public void destroy() {
        log.debug("destroy");
    }
}
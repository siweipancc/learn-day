package com.pancc.learn.spring.boot.jfx.view;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Siweipancc
 */
@Scope("prototype")
@Component
@Slf4j
public class SubController implements Initializable, InitializingBean {
    @Resource
    FXMLLoader fxmlLoader;

    @Resource
    private Stage primaryStage;

    @Value("classpath:view/main-view.fxml")
    org.springframework.core.io.Resource resource;

    @Value("${pancc.view.sub.title}")
    String title;


    public SubController() {
        log.debug("<init>@{}", hashCode());
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("afterPropertiesSet@{}", hashCode());
        primaryStage.setTitle(title);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("initialize@{}", hashCode());
        afterPropertiesSet();
    }

    public void back() throws IOException {
        Scene scene;
        try (InputStream inputStream = resource.getInputStream()) {
            scene = new Scene(fxmlLoader.load(inputStream), 320, 240);
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("preDestroy");
    }


}

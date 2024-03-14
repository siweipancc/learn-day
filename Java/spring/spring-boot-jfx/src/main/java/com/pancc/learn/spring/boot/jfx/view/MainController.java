package com.pancc.learn.spring.boot.jfx.view;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Siwipancc
 */
@Scope("prototype")
@Slf4j
@Controller

public class MainController implements Initializable, DisposableBean {
    @FXML
    public Label welcomeText;
    @Resource
    @SuppressWarnings("unused")
    private HostServices hostServices;

    @Resource
    @SuppressWarnings("unused")
    private Application.Parameters parameters;
    @Resource
    private Stage primaryStage;


    @Value("${pancc.greet}")
    String greet;

    @Value("${pancc.view.main.title}")
    String title;

    @Resource
    FXMLLoader fxmlLoader;

    @Value("classpath:view/sub-view.fxml")
    org.springframework.core.io.Resource subFxml;


    public MainController() {
        log.debug("<init>@{}", hashCode());
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(greet);
        primaryStage.setTitle(title);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("initialize@{}", hashCode());
        log.debug(primaryStage.toString());
    }

    public void openSubWindow() throws IOException {
        Scene scene = new Scene(fxmlLoader.load(subFxml.getInputStream()), 640, 320);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("preDestroy");
    }

    @Override
    public void destroy() {
        log.debug("destroy");
    }
}
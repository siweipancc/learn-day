package com.pancc.learn.spring.boot.jfx;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author Siweipancc
 */
@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Bean
    @Scope("prototype") // 每个 loader 只有一个根
    public FXMLLoader loader(BeanFactory beanFactory) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(beanFactory::getBean);
        return loader;
    }
}

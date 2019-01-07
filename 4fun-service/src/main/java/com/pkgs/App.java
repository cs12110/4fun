package com.pkgs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * TODO: Spring service
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 17:09
 * Since: 1.0.0
 */
@SpringBootApplication
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @PostConstruct
    public void init() {
        logger.info("PostConstruct start");
    }
}

package com.pkgs;

import com.pkgs.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * TODO: Spring service
 * <p>
 * Author: cs12110 create at: 2019/1/6 17:09
 * Since: 1.0.0
 */
@SpringBootApplication
public class ServiceApp {

    @Autowired
    private AnswerService answerService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApp.class, args);
    }


    @PostConstruct
    public void init() {
        answerService.query();
    }
}

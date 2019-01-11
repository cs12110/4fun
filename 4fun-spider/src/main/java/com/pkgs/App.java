package com.pkgs;

import com.pkgs.task.AnswerTask;
import com.pkgs.task.TopicTask;

/**
 * App
 *
 * @author cs12110 at 2018年12月10日下午9:38:34
 */
public class App {

    public static void main(String[] args) {

        new Thread(new TopicTask()).start();
        new Thread(new AnswerTask()).start();
    }


}

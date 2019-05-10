package com.pkgs;

import com.pkgs.task.AnswerTask;
import com.pkgs.task.ResetStatusTask;
import com.pkgs.task.TopicTask;
import com.pkgs.util.ThreadUtil;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * App
 *
 * @author cs12110 at 2018年12月10日下午9:38:34
 */
public class App {


    public static void main(String[] args) {

        // 定时线程池
        ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(
                3,
                ThreadUtil.buildFactory("timer"));

        // 答案爬虫定时器,定时为10s
        schedule.scheduleAtFixedRate(
                new AnswerTask(),
                10,
                10, TimeUnit.SECONDS);

        // 重设状态爬虫定时器，定时为3分钟
        schedule.scheduleAtFixedRate(
                new ResetStatusTask(),
                10,
                300, TimeUnit.SECONDS);

        // 话题定时器,定时为1小时
        schedule.scheduleAtFixedRate(
                new TopicTask(),
                10,
                3600, TimeUnit.SECONDS);

    }

}

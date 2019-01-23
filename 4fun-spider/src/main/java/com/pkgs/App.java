package com.pkgs;

import com.pkgs.task.AnswerTask;
import com.pkgs.task.ResetStatusTask;
import com.pkgs.task.TopicTask;
import com.pkgs.util.ThreadUtil;

import java.util.concurrent.ExecutorService;

/**
 * App
 *
 * @author cs12110 at 2018年12月10日下午9:38:34
 */
public class App {


    public static void main(String[] args) {

        ExecutorService sysExecutor = ThreadUtil.buildTaskPoolExecutor();

        // 启动重设爬话题取状态任务
        sysExecutor.submit(new ResetStatusTask());

        // 启动爬取话题任务
        sysExecutor.submit(new TopicTask());

        // 启动爬取话题下面精华回答任务
        sysExecutor.submit(new AnswerTask());
    }

}

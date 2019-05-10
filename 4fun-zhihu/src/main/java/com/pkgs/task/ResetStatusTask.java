package com.pkgs.task;

import com.pkgs.enums.CrawlStatusEnum;
import com.pkgs.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 检查topic是否全部被爬取完毕,如果爬取完毕,设置状态为未爬取.
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 15:33
 * <p>
 * since: 1.0.0
 */
public class ResetStatusTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ResetStatusTask.class);
    private static TopicService topicService = new TopicService();

    @Override
    public void run() {
        String log;
        int remain = topicService.countRemainTopic();

        if (0 == remain) {
            topicService.updateDoneStatus(null, CrawlStatusEnum.NOT_YET.getValue());
            log = "Reset all topic status to undone";
        } else {
            log = "Still need to steal from zhihu,remain:" + remain;
        }

        logger.info(log);
    }
}

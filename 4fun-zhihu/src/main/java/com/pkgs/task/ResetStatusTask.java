package com.pkgs.task;

import com.pkgs.entity.zhihu.TopicEntity;
import com.pkgs.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
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
        // 获取尚未爬取的话题
        List<TopicEntity> list = topicService.queryRemainTopic();
        if (null != list && list.size() == 0) {
            logger.info("Reset all status to 0");
            topicService.updateDoneStatus(null, 0);
        }
    }
}

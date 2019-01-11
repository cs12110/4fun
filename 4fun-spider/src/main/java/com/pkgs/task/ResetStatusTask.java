package com.pkgs.task;

import com.pkgs.service.TopicService;
import com.pkgs.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        int allDayLongSeconds = 60 * 60 * 24;
        while (true) {
            logger.info("Reset all status to 0");
            topicService.updateDoneStatus(null, 0);
            SysUtil.justStandingHere(allDayLongSeconds);
        }
    }
}

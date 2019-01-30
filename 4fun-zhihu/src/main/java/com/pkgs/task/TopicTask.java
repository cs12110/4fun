package com.pkgs.task;

import com.pkgs.entity.zhihu.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.SubTopicHandler;
import com.pkgs.handler.TopTopicHandler;
import com.pkgs.service.TopicService;
import com.pkgs.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 获取topic
 *
 * @author cs12110 at 2018年12月10日下午9:39:12
 */
public class TopicTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(TopicTask.class);
    private TopicService topicService = new TopicService();

    @Override
    public void run() {
        logger.info("start working at get topic from zhihu");
        long start = System.currentTimeMillis();
        execute();
        long end = System.currentTimeMillis();
        logger.info("get topic is done,spend:{}", (end - start));

    }

    private void execute() {
        // 1. 首先查询出来所有的父级话题
        getAllTopTopic();

        // 2. 根据父级话题获取所有子话题
        getChildTopic();
    }


    /**
     * 获取所有的父级话题
     */
    private void getAllTopTopic() {
        // 1. 首先查询出来所有的父级话题
        AbstractHandler<Object, List<TopicEntity>> topHandler = new TopTopicHandler();
        topHandler.get(SysUtil.TOPIC_URL).forEach(topicService::saveIfNotExists);
    }

    /**
     * 获取50页数据
     */
    private static final int PAGE_LIMIT = 50;

    /**
     * 根据父级话题获取所有子话题
     */
    private void getChildTopic() {
        AbstractHandler<Integer, List<TopicEntity>> subHandler = new SubTopicHandler();
        List<TopicEntity> topList = topicService.queryTopTopic();
        for (TopicEntity e : topList) {
            try {
                subHandler.setValue(e.getId());

                // 只获取前50的话题,大概1k个子话题
                for (int index = 0; index < PAGE_LIMIT; index++) {
                    Map<String, String> searchMap = SysUtil.buildSubTopicSearchMap(e.getDataId(), index * 20);
                    List<TopicEntity> entityList = subHandler.post(SysUtil.SUB_TOPIC_URL, searchMap);

                    Optional
                            .ofNullable(entityList)
                            .orElse(Collections.emptyList())
                            .forEach(topicService::saveIfNotExists);

                    SysUtil.justStandingHere(60);
                }
            } catch (Exception ex) {
                logger.error("{}", ex);
            }
        }

    }


}

package com.pkgs.task;

import com.alibaba.fastjson.JSON;
import com.pkgs.entity.AnswerEntity;
import com.pkgs.entity.ExecResult;
import com.pkgs.entity.TopicEntity;
import com.pkgs.enums.OperationEnum;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.service.AnswerService;
import com.pkgs.service.TopicService;
import com.pkgs.util.PropertiesUtil;
import com.pkgs.util.SysUtil;
import com.pkgs.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 获取top answer
 *
 * @author cs12110 at 2018年12月10日下午9:39:12
 */
public class AnswerTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AnswerTask.class);

    private static TopicService topicService = new TopicService();
    private static AnswerService answerService = new AnswerService();
    private static Random random = new Random();

    /**
     * 最小点赞数
     */
    private static int miniVoteNum = PropertiesUtil.getInt("upVote.minNum", 10000);
    /**
     * 创建线程池
     */
    private static final int THREAD_NUM = PropertiesUtil.getInt("spider.threadNum", 2);
    private static ExecutorService pool = ThreadUtil.buildSpiderExecutor(THREAD_NUM);

    @Override
    public void run() {
        logger.info("start working at get top answer");
        long start = System.currentTimeMillis();

        execute();

        long end = System.currentTimeMillis();
        logger.info("get top answer is done,spend:{}", (end - start));
    }

    private void execute() {
        List<TopicEntity> topicList = getRemainTopics();
        int size = topicList.size();
        int limit = getPageNum(size, THREAD_NUM);

        for (int index = 0; index < limit; index++) {
            int start = index * THREAD_NUM;
            int end = start + THREAD_NUM > size ? size : start + THREAD_NUM;

            List<TopicEntity> partList = topicList.subList(start, end);
            CountDownLatch countDownLatch = new CountDownLatch(partList.size());

            for (TopicEntity e : partList) {
                pool.submit(new MySpider(e, countDownLatch));
            }

            try {
                countDownLatch.await();
            } catch (Exception e) {
                //do nothing
            }

            int sleep = 5 + random.nextInt(5);
            SysUtil.justStandingHere(sleep);
        }
    }

    /**
     * 获取分页数量
     *
     * @param size     列表大小
     * @param pageSize 分页大小
     * @return int
     */
    private int getPageNum(int size, int pageSize) {
        int tmp = size / pageSize;
        return size % pageSize == 0 ? tmp : tmp + 1;
    }


    private List<TopicEntity> getRemainTopics() {
        // 获取需要爬取的话题
        List<TopicEntity> list = topicService.queryRemainTopic();

        return list
                .stream()
                .filter(e -> null != e.getLink() && !"".equals(e.getLink().trim()))
                .collect(Collectors.toList());
    }

    static class MySpider implements Runnable {
        private TopicEntity entity;
        private CountDownLatch latch;

        MySpider(TopicEntity entity, CountDownLatch latch) {
            this.entity = entity;
            this.latch = latch;
        }

        @Override
        public void run() {
            logger.info("Start: [{}-`{}`]", entity.getId(), entity.getName());

            AbstractHandler<List<AnswerEntity>> handler = new TopAnswerHandler();

            int page = 20;
            int insertCounter = 0;
            int updateCounter = 0;
            int failureCounter = 0;
            long start = System.currentTimeMillis();

            boolean gt = true;


            Map<String, Object> map = new HashMap<>(5);
            // 获取前200条数据
            for (int index = 0; index < page; index++) {
                handler.setValue(entity.getId());
                String url = SysUtil.getAnswersUrlOfTopic(
                        getIdFromUrl(entity.getLink()),
                        index * 10,
                        10);

                for (AnswerEntity a : handler.get(url)) {
                    // 点赞数小于10k
                    if (a.getUpvoteNum() < miniVoteNum) {
                        gt = false;
                        break;
                    }

                    ExecResult result = answerService.saveIfNotExists(a);
                    if (result.isSuccess()) {
                        if (OperationEnum.INSERT == result.getOp()) {
                            insertCounter++;
                        } else if (OperationEnum.UPDATE == result.getOp()) {
                            updateCounter++;
                        } else {
                            failureCounter++;
                        }
                    }

                    if (result.getOp() == OperationEnum.INSERT) {
                        map.put("topicId", entity.getId());
                        map.put("topicName", entity.getName());
                        map.put("author", a.getAuthor());
                        map.put("question", a.getQuestion());
                        map.put("status", result.isSuccess());
                        map.put("operation", result.getOp());
                        logger.info(JSON.toJSONString(map, true));
                    }
                }
                if (!gt) {
                    break;
                }
            }
            topicService.updateDoneStatus(entity.getId(), 1);
            latch.countDown();

            long spendMills = System.currentTimeMillis() - start;

            logger.info("[{}-`{}`] add: {}, update: {}, failure: {}, spend: {}(mills)",
                    entity.getId(),
                    entity.getName(),
                    insertCounter,
                    updateCounter,
                    failureCounter,
                    spendMills);
        }
    }

    /**
     * 从url里面获取Id
     *
     * @param url url连接
     * @return String
     */
    private static String getIdFromUrl(String url) {
        if (url == null) {
            return "";
        }
        int last = url.lastIndexOf("/");
        return url.substring(last + 1);
    }

}

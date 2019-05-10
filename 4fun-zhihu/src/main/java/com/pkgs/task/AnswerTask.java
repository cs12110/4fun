package com.pkgs.task;

import com.alibaba.fastjson.JSON;
import com.pkgs.entity.zhihu.AnswerEntity;
import com.pkgs.entity.zhihu.TopicEntity;
import com.pkgs.enums.CrawlStatusEnum;
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
     * 获取topic页数
     */
    private static int pageNum = PropertiesUtil.getInt("answer.pageNum", 20);

    /**
     * 创建线程池
     */
    private static final int THREAD_NUM = PropertiesUtil.getInt("spider.threadNum", 3);
    private static ExecutorService pool = ThreadUtil.buildSpiderExecutor(THREAD_NUM);

    @Override
    public void run() {
        logger.info("Start working at get top answer");
        long start = System.currentTimeMillis();

        execute();

        long end = System.currentTimeMillis();
        logger.info("Get top answer is done,spend:{}", (end - start));
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
                .filter(e -> SysUtil.isNotEmpy(e.getLink()))
                .collect(Collectors.toList());
    }

    static class OpCounter {
        /**
         * 插入条数
         */
        int insertCounter = 0;
        /**
         * 更新条数
         */
        int updateCounter = 0;
        /**
         * 失败条数
         */
        int failureCounter = 0;

        void count(OperationEnum operationEnum) {
            switch (operationEnum) {
                case INSERT:
                    insertCounter++;
                    break;
                case UPDATE:
                    updateCounter++;
                    break;
                default:
                    failureCounter++;
            }
        }

        @Override
        public String toString() {
            return "add:" + insertCounter + ", update:" + updateCounter + ", failure:" + failureCounter;
        }
    }

    /**
     * 爬虫爬取线程
     */
    static class MySpider implements Runnable {
        private TopicEntity topic;
        private CountDownLatch latch;

        MySpider(TopicEntity topic, CountDownLatch latch) {
            this.topic = topic;
            this.latch = latch;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();

            OpCounter counter = new OpCounter();
            AbstractHandler<Integer, List<AnswerEntity>> handler = new TopAnswerHandler();

            outside:
            for (int index = 0; index < pageNum; index++) {
                handler.setValue(topic.getId());
                String topicId = getIdFromUrl(topic.getLink());
                String url = SysUtil.getAnswersUrlOfTopic(topicId, index * 10, 10);

                for (AnswerEntity answer : handler.get(url)) {
                    // 点赞数小于10k
                    if (answer.getUpvoteNum() < miniVoteNum) {
                        logger.debug("Break up {} on page:{}", topic.getName(), index);
                        break outside;
                    }

                    // 执行存储操作
                    OperationEnum result = answerService.saveOrUpdate(answer);
                    counter.count(result);

                    if (result == OperationEnum.INSERT) {
                        Map<String, Object> map = buildLogMap(answer, result);
                        logger.info(JSON.toJSONString(map, true));
                    } else if (logger.isDebugEnabled()) {
                        Map<String, Object> map = buildLogMap(answer, result);
                        logger.debug(JSON.toJSONString(map, true));
                    }
                }
            }

            topicService.updateDoneStatus(topic.getId(), CrawlStatusEnum.ALREADY.getValue());
            latch.countDown();

            // 总体操作日志
            long spend = System.currentTimeMillis() - start;
            logger.info("[{}#{}] {}, spend:{} ms", topic.getId(), topic.getName(), counter, spend);
        }


        /**
         * 构建显示新增数据
         *
         * @param answer        answer对象
         * @param operationEnum 执行类型
         * @return Map
         */
        private Map<String, Object> buildLogMap(AnswerEntity answer, OperationEnum operationEnum) {
            Map<String, Object> map = new HashMap<>(6);
            map.put("topicId", topic.getId());
            map.put("topicName", topic.getName());
            map.put("answerId", answer.getAnswerId());
            map.put("author", answer.getAuthor());
            map.put("question", answer.getQuestion());
            map.put("operation", operationEnum);

            return map;
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

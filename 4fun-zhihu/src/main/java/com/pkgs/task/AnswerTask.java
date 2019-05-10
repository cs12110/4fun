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
        execute();
    }

    private void execute() {
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
        List<TopicEntity> topicList = topicService.queryRemainTopic(THREAD_NUM);

        for (TopicEntity e : topicList) {
            pool.submit(new MySpider(e, countDownLatch));
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            //do nothing
        }
        SysUtil.justStandingHere(random.nextInt(5));
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
        private Map<String, Object> logMap = new HashMap<>(6);

        MySpider(TopicEntity topic, CountDownLatch latch) {
            this.topic = topic;
            this.latch = latch;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();

            OpCounter counter = new OpCounter();
            AbstractHandler<Integer, List<AnswerEntity>> handler = new TopAnswerHandler();
            handler.setValue(topic.getId());

            outside:
            for (int index = 0; index < pageNum; index++) {

                String topicId = getIdFromUrl(topic.getLink());
                String url = SysUtil.getAnswersUrlOfTopic(topicId, index * 10, 10);

                for (AnswerEntity answer : handler.get(url)) {
                    // 点赞数小于10k
                    if (answer.getUpvoteNum() < miniVoteNum) {
                        logger.debug("Break up: {} on page:{}", topic.getName(), index);
                        break outside;
                    }

                    // 执行存储操作
                    OperationEnum result = answerService.saveOrUpdate(answer);
                    setLogMapValue(answer, result);

                    logger.info(JSON.toJSONString(logMap, true));

                    counter.count(result);
                }
            }

            topicService.updateAttr(topic.getId(), CrawlStatusEnum.ALREADY.getValue());
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
         */
        private void setLogMapValue(AnswerEntity answer, OperationEnum operationEnum) {
            logMap.put("topicId", topic.getId());
            logMap.put("topicName", topic.getName());
            logMap.put("answerId", answer.getAnswerId());
            logMap.put("author", answer.getAuthor());
            logMap.put("question", answer.getQuestion());
            logMap.put("operation", operationEnum);
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

package com.pkgs.task;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.entity.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.util.PropertiesUtil;
import com.pkgs.util.SysUtil;

/**
 * 获取top answer
 *
 * @author cs12110 at 2018年12月10日下午9:39:12
 */
public class AnswerTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AnswerTask.class);

    private static TopicMapper topicMapper = new TopicMapper();
    private static AnswerMapper answerMapper = new AnswerMapper();

    private static int threadNum = 2;
    private static ExecutorService pool = Executors.newFixedThreadPool(threadNum);

    private static int sleepSeconds = Integer.parseInt(PropertiesUtil.get("sleep.seconds", "5000"));
    private static long minUpvoteNum = Long.parseLong(PropertiesUtil.get("upvote.minNum", "10000"));

    @Override
    public void run() {

        while (true) {
            logger.info("start working at get top answer");
            long start = System.currentTimeMillis();

            execute();
            // 重新设置爬取
            topicMapper.updateDoneStatus(null, 0);

            long end = System.currentTimeMillis();
            logger.info("get top answer is done,spend:{}", (end - start));
            // 休息一个小时
            try {
                Thread.sleep(1000 * 3600 * 6);
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    private void execute() {
        List<TopicEntity> topicList = getRemainTopics();
        int size = topicList.size();
        int limit = size % threadNum == 0 ? size / threadNum : size / threadNum + 1;

        for (int index = 0; index < limit; index++) {
            int start = index * threadNum;
            int end = start + threadNum > size ? size : start + threadNum;

            List<TopicEntity> partList = topicList.subList(start, end);
            CountDownLatch countDownLatch = new CountDownLatch(partList.size());

            for (TopicEntity e : partList) {
                pool.submit(new MySpider(e, countDownLatch));
            }
            try {
                countDownLatch.await();
                int sleep = sleepSeconds + (int) (Math.random() * sleepSeconds);
                Thread.sleep(sleep);
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    private List<TopicEntity> getRemainTopics() {
        // 获取需要爬取的话题
        TopicEntity search = new TopicEntity();
        search.setDone(0);

        return topicMapper
                .query(search)
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
            AbstractHandler<List<AnswerEntity>> handler = new TopAnswerHandler();
            logger.info("get top answer of: {} -> {} ", entity.getId(), entity.getName());
            int page = 20;
            int count = 0;
            boolean gt = true;

            // 获取前200条数据
            for (int index = 0; index < page; index++) {
                handler.setValue(entity.getId());
                String url = SysUtil.getAnswersUrlOfTopic(getIdFromUrl(entity.getLink()), index * 10, 10);

                // 处理每一条数据
                for (AnswerEntity a : handler.get(url)) {
                    // 点赞数小于1k
                    if (a.getUpvoteNum() < minUpvoteNum) {
                        gt = false;
                        break;
                    }
                    count += answerMapper.saveIfNotExists(a) ? 1 : 0;
                }
                if (!gt) {
                    break;
                }
            }
            topicMapper.updateDoneStatus(entity.getId(), 1);
            latch.countDown();

            logger.info("get top answer of: {} -> {} is done ,add: {}", entity.getId(), entity.getName(), count);
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

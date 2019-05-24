package com.pkgs.service;

import com.pkgs.entity.zhihu.AnswerEntity;
import com.pkgs.entity.zhihu.MapTopicAnswerEntity;
import com.pkgs.enums.OperationEnum;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.mapper.MapTopicAnswerMapper;
import com.pkgs.util.CacheUtil;
import com.pkgs.util.ProxyMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 爬取精华回答service
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 9:46
 * <p>
 * since: 1.0.0
 */
public class AnswerService {


    private Logger logger = LoggerFactory.getLogger(AnswerService.class);

    /**
     * lock
     */
    private static ReentrantLock lock = new ReentrantLock();

    private static final String ANSWER_ID_KEY_PREFIX = "answerId#";
    private static final String MAPPING_KEY_PREFIX = "answerTopicMapping#";

    private AnswerMapper answerMapper = ProxyMapperUtil.wrapper(AnswerMapper.class);
    private MapTopicAnswerMapper mapTopicAnswerMapper = ProxyMapperUtil.wrapper(MapTopicAnswerMapper.class);

    /**
     * 保存数据
     *
     * @param entity entity
     * @return boolean
     */
    public OperationEnum saveOrUpdate(AnswerEntity entity) {

        lock.lock();

        OperationEnum operation;
        try {
            Integer answerId = getAnswerIdByLink(entity.getLink());
            if (answerId == null) {
                answerMapper.save(entity);
                answerId = entity.getId();
                operation = OperationEnum.INSERT;
            } else {
                // 更新点赞数
                answerMapper.updateVoteNum(answerId, entity.getUpvoteNum());
                operation = OperationEnum.UPDATE;
            }
            //处理关系
            insertTopicAnswerMappingIfNotExists(entity.getTopicId(), answerId);
        } catch (Exception e) {
            logger.error("{}", e);
            operation = OperationEnum.ERROR;
        } finally {
            lock.unlock();
        }
        return operation;
    }

    /**
     * 整合本地缓存策略,根据连接获取回答id
     *
     * @param link link
     * @return Integer
     */
    private Integer getAnswerIdByLink(String link) {
        String key = ANSWER_ID_KEY_PREFIX + link;
        Object value = CacheUtil.get(key);
        if (value == null) {
            value = answerMapper.selectIdByLink(link);
            if (value != null) {
                CacheUtil.put(key, value);
            }
        }
        return (Integer) value;
    }


    /**
     * 添加关系
     *
     * @param topicId  话题
     * @param answerId 答案
     */
    private void insertTopicAnswerMappingIfNotExists(Integer topicId, Integer answerId) {
        try {
            MapTopicAnswerEntity mapping = new MapTopicAnswerEntity();
            mapping.setTopicId(topicId);
            mapping.setAnswerId(answerId);

            int count = countMapping(mapping);
            if (count == 0) {
                mapTopicAnswerMapper.save(mapping);
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }

    /**
     * 统计关联个数
     *
     * @param search 查询参数
     * @return Integer
     */
    private Integer countMapping(MapTopicAnswerEntity search) {
        String key = MAPPING_KEY_PREFIX + search.getTopicId() + "-" + search.getAnswerId();
        Object value = CacheUtil.get(key);
        if (value == null) {
            value = mapTopicAnswerMapper.selectCount(search);
            CacheUtil.put(key, value);
        }
        return (Integer) value;
    }
}

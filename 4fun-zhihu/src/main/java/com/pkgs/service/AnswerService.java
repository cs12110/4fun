package com.pkgs.service;

import com.pkgs.entity.zhihu.AnswerEntity;
import com.pkgs.entity.zhihu.MapTopicAnswerEntity;
import com.pkgs.enums.OperationEnum;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.mapper.MapTopicAnswerMapper;
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

    /**
     * 保存数据
     *
     * @param entity entity
     * @return boolean
     */
    public OperationEnum saveOrUpdate(AnswerEntity entity) {

        lock.lock();

        OperationEnum operation = OperationEnum.ERROR;
        try {
            AnswerMapper mapper = ProxyMapperUtil.wrapper(AnswerMapper.class);
            Integer answerId = mapper.selectIdByLink(entity.getLink());

            if (answerId == null) {
                mapper.save(entity);
                answerId = entity.getId();
                operation = OperationEnum.INSERT;
            } else {
                // 更新点赞数
                int updateRecordNum = mapper.updateVoteNum(answerId, entity.getUpvoteNum());
                if (updateRecordNum > 0) {
                    operation = OperationEnum.UPDATE;
                }
            }
            //处理关系
            insertTopicAnswerMapping(entity.getTopicId(), answerId);
        } catch (Exception e) {
            logger.error("{}", e);
            operation = OperationEnum.ERROR;
        } finally {
            lock.unlock();
        }

        return operation;
    }


    /**
     * 添加关系
     *
     * @param topicId  话题
     * @param answerId 答案
     */
    private void insertTopicAnswerMapping(Integer topicId, Integer answerId) {
        try {
            MapTopicAnswerMapper mapper = ProxyMapperUtil.wrapper(MapTopicAnswerMapper.class);

            MapTopicAnswerEntity search = new MapTopicAnswerEntity();
            search.setTopicId(topicId);
            search.setAnswerId(answerId);
            int count = mapper.selectCount(search);

            if (count == 0) {
                mapper.save(search);
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }
}

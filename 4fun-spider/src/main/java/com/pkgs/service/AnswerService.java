package com.pkgs.service;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.entity.ExecResult;
import com.pkgs.entity.MapTopicAnswerEntity;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.mapper.MapTopicAnswerMapper;
import com.pkgs.util.ProxyMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
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
    public ExecResult saveIfNotExists(AnswerEntity entity) {
        lock.lock();
        ExecResult result = new ExecResult();
        result.setSuccess(false);
        try {
            AnswerMapper mapper = ProxyMapperUtil.wrapper(AnswerMapper.class);
            Integer answerId = mapper.selectIdByLink(entity.getLink());

            if (answerId == null) {
                mapper.save(entity);
                answerId = entity.getId();
                result.setSuccess(true);
            } else {
                result.setMsg("exists");
            }
            //处理关系
            mappingTopicAnswer(entity.getTopicId(), answerId);
        } catch (Exception e) {
            logger.error("{}", e);
            result.setMsg("have an error");
        } finally {
            lock.unlock();
        }
        return result;
    }


    /**
     * 添加关系
     *
     * @param topicId  话题
     * @param answerId 答案
     */
    private void mappingTopicAnswer(Integer topicId, Integer answerId) {
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

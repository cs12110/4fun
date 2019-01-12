package com.pkgs.service;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.entity.MapTopicAnswerEntity;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.mapper.MapTopicAnswerMapper;
import com.pkgs.util.ProxyMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 保存数据
     *
     * @param entity entity
     * @return boolean
     */
    public boolean saveIfNotExists(AnswerEntity entity) {
        try {
            AnswerMapper mapper = ProxyMapperUtil.wrapper(AnswerMapper.class);
            Integer answerId = mapper.selectIdByLink(entity.getLink());

            if (answerId != null) {
                logger.debug("exists:{}->{}", entity.getAuthor(), entity.getQuestion());
            } else {
                mapper.save(entity);
                answerId = entity.getId();
                logger.info("save:{}->{}", entity.getAuthor(), entity.getQuestion());
            }
            //处理关系
            mappingTopicAnswer(entity.getTopicId(), answerId);
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return true;
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

package com.pkgs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.TopicEntity;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.util.ProxyMapperUtil;
import com.pkgs.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话题
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 9:44
 * <p>
 * since: 1.0.0
 */
public class TopicService {

    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    /**
     * 保存数据
     *
     * @param entity {@link TopicEntity}
     * @return boolean
     */
    public boolean saveIfNotExists(TopicEntity entity) {
        try {
            TopicEntity search = new TopicEntity();
            search.setName(entity.getName());
            search.setLink(entity.getLink());

            TopicMapper mapper = ProxyMapperUtil.wrapper(TopicMapper.class);
            int count = mapper.selectCount(search);
            if (count == 0) {
                mapper.save(entity);
                logger.info("save:{}", entity.getName());
            } else {
                logger.debug("exists:{}", entity.getName());
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return true;
    }


    /**
     * 查询父级话题
     *
     * @return List
     */
    public List<TopicEntity> queryTopTopic() {
        try {
            TopicMapper mapper = ProxyMapperUtil.wrapper(TopicMapper.class);
            return mapper.queryTopTopics();
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return Collections.emptyList();
    }


    /**
     * 查询还没爬取的话题
     *
     * @return List
     */
    public List<TopicEntity> queryRemainTopic() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("done", 0);
        Page<TopicEntity> page = new Page<>();
        TopicMapper mapper = ProxyMapperUtil.wrapper(TopicMapper.class);
        return mapper.selectByMap(page, map);
    }


    /**
     * 更新爬取状态
     *
     * @param topicId 话题Id
     * @param status  {0:未爬取,1:已爬取}
     */
    public void updateDoneStatus(Integer topicId, int status) {
        TopicMapper mapper = ProxyMapperUtil.wrapper(TopicMapper.class);
        mapper.updateDoneStatus(topicId, status, SysUtil.getTime());
    }
}

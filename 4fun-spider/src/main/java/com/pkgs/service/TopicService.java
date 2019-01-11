package com.pkgs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.TopicEntity;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.util.JdbcUtil;
import com.pkgs.util.SqlSessionUtil;
import com.pkgs.util.SysUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

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
        // 这里好像可以用动态代理来抽取
        SqlSession sqlSession = SqlSessionUtil.openSession();
        try {
            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            int count = mapper.selectCount(entity);
            if (count == 0) {
                mapper.save(entity);
                logger.info("save:{}", entity.getName());
            } else {
                logger.debug("exists:{}", entity.getName());
            }
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            SqlSessionUtil.closeSession(sqlSession);
        }
        return true;
    }


    /**
     * 查询父级话题
     *
     * @return List
     */
    public List<TopicEntity> queryTopTopic() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        try {
            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            return mapper.queryTopTopics();
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            SqlSessionUtil.closeSession(sqlSession);
        }
        return Collections.emptyList();
    }


    /**
     * 查询还没爬取的话题
     *
     * @return List
     */
    public List<TopicEntity> queryRemainTopic() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        try {
            Map<String, Object> map = new HashMap<>(1);
            map.put("done", 0);
            Page<TopicEntity> page = new Page<>();
            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            return mapper.selectByMap(page, map);
        } finally {
            SqlSessionUtil.closeSession(sqlSession);
        }
    }


    /**
     * 更新爬取状态
     *
     * @param topicId 话题Id
     * @param status  {0:未爬取,1:已爬取}
     */
    public void updateDoneStatus(Integer topicId, int status) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        try {
            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            mapper.updateDoneStatus(topicId, status);
            sqlSession.commit();
        } finally {
            SqlSessionUtil.closeSession(sqlSession);
        }
    }
}

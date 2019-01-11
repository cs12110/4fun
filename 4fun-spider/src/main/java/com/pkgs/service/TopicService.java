package com.pkgs.service;

import com.pkgs.entity.TopicEntity;
import com.pkgs.util.JdbcUtil;
import com.pkgs.util.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
     * @param entity
     * @return
     */
    public boolean saveIfNotExists(TopicEntity entity) {
        if (null != queryOne(entity)) {
            logger.debug("exists:{}", entity.getName());
            return false;
        }
        logger.info("save:{}", entity.getName());
        save(Arrays.asList(entity));
        return true;
    }

    /**
     * 保存数据
     *
     * @param list list
     * @return int
     */
    public int save(List<TopicEntity> list) {
        int successCount = 0;
        Connection conn = JdbcUtil.getConnection();
        try {
            String sql = "INSERT INTO topic_t(`parent_id`,`data_id`,`name`,`update_time`,`link`) VALUES(?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);

            for (TopicEntity e : list) {
                stm.setObject(1, e.getParentId());
                stm.setString(2, e.getDataId());
                stm.setString(3, e.getName());
                stm.setString(4, e.getUpdateTime());
                stm.setString(5, e.getLink());
                successCount += stm.executeUpdate();
            }
            JdbcUtil.close(stm);
        } catch (Exception e) {
            logger.info("{}", e);
        }
        JdbcUtil.close(conn);
        return successCount;
    }

    /**
     * 查询一条数据
     *
     * @param search 查询条件
     * @return {@link TopicEntity}
     */
    public TopicEntity queryOne(TopicEntity search) {
        List<TopicEntity> list = query(search);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询父级话题
     *
     * @return List
     */
    public List<TopicEntity> queryTopTopic() {
        List<TopicEntity> list = query(null);
        return null == list
                ? Collections.emptyList()
                : list.stream().filter(e -> null != e.getDataId()).collect(Collectors.toList());
    }

    /**
     * 查询数据
     *
     * @param search 查询条件
     * @return {@link TopicEntity}
     */
    public List<TopicEntity> query(TopicEntity search) {
        List<TopicEntity> list = new ArrayList<>();
        Connection conn = JdbcUtil.getConnection();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM topic_t WHERE 1=1 ");
            if (null != search) {
                if (!SysUtil.isEmpty(search.getDataId())) {
                    sql.append(" AND `data_id`=").append("'").append(search.getDataId()).append("'");
                }

                if (!SysUtil.isEmpty(search.getName())) {
                    sql.append(" AND `name`=").append("'").append(search.getName()).append("'");
                }

                if (!SysUtil.isEmpty(search.getLink())) {
                    sql.append(" AND `link`=").append("'").append(search.getLink()).append("'");
                }

                if (search.getDone() != null) {
                    sql.append(" AND `done`=").append(search.getDone());
                }
            }

            PreparedStatement stm = conn.prepareStatement(sql.toString());
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                TopicEntity topic = new TopicEntity();
                topic.setId(resultSet.getInt("id"));
                topic.setParentId(resultSet.getInt("parent_id"));
                topic.setDataId(resultSet.getString("data_id"));
                topic.setName(resultSet.getString("name"));
                topic.setUpdateTime(resultSet.getString("update_time"));
                topic.setLink(resultSet.getString("link"));
                list.add(topic);
            }
            JdbcUtil.close(resultSet);
            JdbcUtil.close(stm);
        } catch (Exception e) {
            logger.info("{}", e);
        }
        JdbcUtil.close(conn);
        return list;
    }

    /**
     * 更新爬取状态
     *
     * @param topicId 话题Id
     * @param status  {0:未爬取,1:已爬取}
     */
    public void updateDoneStatus(Integer topicId, int status) {
        Connection conn = JdbcUtil.getConnection();
        try {
            String sql = "UPDATE topic_t SET done = " + status + ",update_time=\"" + SysUtil.getTime()
                    + "\" WHERE 1=1 ";
            if (topicId != null) {
                sql += " AND id = " + topicId;
            }
            Statement stm = conn.createStatement();
            stm.execute(sql);
            JdbcUtil.close(stm);
        } catch (Exception e) {
            logger.info("{}", e);
        }
        JdbcUtil.close(conn);
    }


}

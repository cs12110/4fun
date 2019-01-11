package com.pkgs.service;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.entity.MapTopicAnswerEntity;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.mapper.MapTopicAnswerMapper;
import com.pkgs.util.JdbcUtil;
import com.pkgs.util.SqlSessionUtil;
import com.pkgs.util.SysUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        SqlSession sqlSession = SqlSessionUtil.openSession();
        try {
            AnswerMapper mapper = sqlSession.getMapper(AnswerMapper.class);

            /*
             * 1. 判断answer在不在
             *
             * 2. 如果answer不存在,则新增进去,同时新增关系
             */
            Integer answerId = mapper.selectIdByLink(entity.getLink());
            if (answerId != null) {
                logger.debug("exists:{}->{}", entity.getAuthor(), entity.getQuestion());
            } else {
                mapper.save(entity);
                answerId = entity.getId();
                logger.info("save:{}->{}", entity.getAuthor(), entity.getQuestion());
            }
            sqlSession.commit();
            //处理关系
            mappingTopicAnswer(entity.getTopicId(), answerId);
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            SqlSessionUtil.closeSession(sqlSession);
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
        SqlSession sqlSession = SqlSessionUtil.openSession();
        try {
            MapTopicAnswerMapper mapper = sqlSession.getMapper(MapTopicAnswerMapper.class);

            MapTopicAnswerEntity search = new MapTopicAnswerEntity();
            search.setTopicId(topicId);
            search.setAnswerId(answerId);
            int count = mapper.selectCount(search);

            if (count == 0) {
                mapper.save(search);
                sqlSession.commit();
            }
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            SqlSessionUtil.closeSession(sqlSession);
        }
    }


    /**
     * 查询数据
     *
     * @param search 查询条件
     * @return {@link AnswerEntity}
     */
    public List<AnswerEntity> query(AnswerEntity search) {
        List<AnswerEntity> list = new ArrayList<>();
        Connection conn = JdbcUtil.getConnection();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM top_answer_t WHERE 1=1 ");
            if (null != search) {
                if (!SysUtil.isEmpty(search.getQuestionId())) {
                    sql.append(" AND `question_id`=").append("'").append(search.getQuestionId()).append("'");
                }

                if (!SysUtil.isEmpty(search.getAnswerId())) {
                    sql.append(" AND `answer_id`=").append("'").append(search.getAnswerId()).append("'");
                }

                if (!SysUtil.isEmpty(search.getLink())) {
                    sql.append(" AND `link`=").append("'").append(search.getLink()).append("'");
                }
            }

            PreparedStatement stm = conn.prepareStatement(sql.toString());
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                AnswerEntity answer = new AnswerEntity();
                answer.setId(resultSet.getInt("id"));
                answer.setLink(resultSet.getString("link"));
                answer.setAuthor(resultSet.getString("author"));
                answer.setQuestion(resultSet.getString("question"));
                answer.setUpvoteNum(resultSet.getInt("upvote_num"));
                answer.setCommentNum(resultSet.getInt("comment_num"));
                answer.setSummary(resultSet.getString("summary"));
                answer.setStealAt(resultSet.getString("steal_at"));
                list.add(answer);
            }
            JdbcUtil.close(resultSet);
            JdbcUtil.close(stm);
        } catch (Exception e) {
            logger.info("{}", e);
        }
        JdbcUtil.close(conn);
        return list;
    }
}

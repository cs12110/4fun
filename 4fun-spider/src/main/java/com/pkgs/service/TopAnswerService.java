package com.pkgs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkgs.entity.TopAnswerEntity;
import com.pkgs.util.JdbcUtil;
import com.pkgs.util.StrUtil;

/**
 * 顶级话题业务处理类
 *
 * 
 * @author cs12110 at 2018年12月10日下午10:29:08
 *
 */
public class TopAnswerService {

	private Logger logger = LoggerFactory.getLogger(TopAnswerService.class);

	/**
	 * 保存数据
	 * 
	 * @param entity
	 * @return
	 */
	public boolean saveIfNotExists(TopAnswerEntity entity) {
		if (null != queryOne(entity)) {
			logger.debug("exists:{}->{}", entity.getAuthor(), entity.getQuestion());
			return false;
		}
		logger.info("save:{}->{}", entity.getAuthor(), entity.getQuestion());
		return 1 == save(Arrays.asList(entity));
	}

	/**
	 * 保存数据
	 * 
	 * @param list
	 *            list
	 * @return int
	 */
	public int save(List<TopAnswerEntity> list) {
		int successCount = 0;
		Connection conn = JdbcUtil.getConnection();
		try {
			String sql = "INSERT INTO top_answer_t(`question`,`question_id`,`author`,`answer_id`,`link`,"
					+ "`comment_num`,`upvote_num`,`summary`,`create_at`,`update_at`,`topic_id`)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);

			for (TopAnswerEntity e : list) {

				stm.setString(1, e.getQuestion());
				stm.setString(2, e.getQuestionId());
				stm.setString(3, e.getAuthor());
				stm.setString(4, e.getAnswerId());
				stm.setString(5, e.getLink());
				stm.setInt(6, e.getCommentNum());
				stm.setInt(7, e.getUpvoteNum());
				stm.setString(8, e.getSummary());
				stm.setString(9, e.getCreateAt());
				stm.setString(10, e.getUpdateAt());
				stm.setInt(11, e.getTopicId());

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
	 * @param search
	 *            查询条件
	 * @return {@link TopAnswerEntity}
	 */
	public TopAnswerEntity queryOne(TopAnswerEntity search) {
		List<TopAnswerEntity> list = query(search);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询数据
	 * 
	 * @param search
	 *            查询条件
	 * @return {@link TopAnswerEntity}
	 */
	public List<TopAnswerEntity> query(TopAnswerEntity search) {
		List<TopAnswerEntity> list = new ArrayList<>();
		Connection conn = JdbcUtil.getConnection();
		try {
			StringBuilder sql = new StringBuilder("SELECT * FROM top_answer_t WHERE 1=1 ");
			if (null != search) {
				if (!StrUtil.isEmpty(search.getQuestionId())) {
					sql.append(" AND `question_id`=").append("'").append(search.getQuestionId()).append("'");
				}

				if (!StrUtil.isEmpty(search.getAnswerId())) {
					sql.append(" AND `answer_id`=").append("'").append(search.getAnswerId()).append("'");
				}

				if (!StrUtil.isEmpty(search.getLink())) {
					sql.append(" AND `link`=").append("'").append(search.getLink()).append("'");
				}
			}

			PreparedStatement stm = conn.prepareStatement(sql.toString());
			ResultSet resultSet = stm.executeQuery();

			while (resultSet.next()) {
				TopAnswerEntity answer = new TopAnswerEntity();
				answer.setId(resultSet.getInt("id"));
				answer.setLink(resultSet.getString("link"));
				answer.setAuthor(resultSet.getString("author"));
				answer.setQuestion(resultSet.getString("question"));
				answer.setUpvoteNum(resultSet.getInt("upvote_num"));
				answer.setCommentNum(resultSet.getInt("comment_num"));
				answer.setSummary(resultSet.getString("summary"));
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

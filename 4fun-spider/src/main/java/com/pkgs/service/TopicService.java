package com.pkgs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkgs.entity.TopicEntity;
import com.pkgs.util.JdbcUtil;
import com.pkgs.util.StrUtil;

/**
 * 顶级话题业务处理类
 *
 * 
 * @author cs12110 at 2018年12月10日下午10:29:08
 *
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
	 * @param list
	 *            list
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
	 * @param search
	 *            查询条件
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
	 * @param search
	 *            查询条件
	 * @return {@link TopicEntity}
	 */
	public List<TopicEntity> query(TopicEntity search) {
		List<TopicEntity> list = new ArrayList<>();
		Connection conn = JdbcUtil.getConnection();
		try {
			StringBuilder sql = new StringBuilder("SELECT * FROM topic_t WHERE 1=1 ");
			if (null != search) {
				if (!StrUtil.isEmpty(search.getDataId())) {
					sql.append(" AND `data_id`=").append("'").append(search.getDataId()).append("'");
				}

				if (!StrUtil.isEmpty(search.getName())) {
					sql.append(" AND `name`=").append("'").append(search.getName()).append("'");
				}

				if (!StrUtil.isEmpty(search.getLink())) {
					sql.append(" AND `link`=").append("'").append(search.getLink()).append("'");
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

}

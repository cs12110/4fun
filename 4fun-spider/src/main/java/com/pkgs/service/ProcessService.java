package com.pkgs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkgs.entity.ProcessEntity;
import com.pkgs.util.JdbcUtil;

/**
 * 顶级话题业务处理类
 *
 * 
 * @author cs12110 at 2018年12月10日下午10:29:08
 *
 */
public class ProcessService {

	private Logger logger = LoggerFactory.getLogger(ProcessService.class);

	/**
	 * 保存数据
	 * 
	 * @param entity
	 * @return
	 */
	public boolean saveIfNotExists(ProcessEntity entity) {
		if (null != queryOne(entity)) {
			logger.debug("exists:{}", entity);
			return false;
		}
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
	public int save(List<ProcessEntity> list) {
		int successCount = 0;
		Connection conn = JdbcUtil.getConnection();
		try {
			String sql = "INSERT INTO process_t(`topic_id`,`start_at`,`end_at`,`done`) VALUES(?,?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			for (ProcessEntity e : list) {
				stm.setInt(1, e.getTopicId());
				stm.setString(2, e.getStartAt());
				stm.setString(3, e.getEndAt());
				stm.setInt(4, e.getDone());
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
	 * @return {@link ProcessEntity}
	 */
	public ProcessEntity queryOne(ProcessEntity search) {
		List<ProcessEntity> list = query(search);
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
	 * @return {@link ProcessEntity}
	 */
	public List<ProcessEntity> query(ProcessEntity search) {
		List<ProcessEntity> list = new ArrayList<>();
		Connection conn = JdbcUtil.getConnection();
		try {
			StringBuilder sql = new StringBuilder("SELECT * FROM process_t WHERE 1=1 ");
			if (null != search) {
				sql.append(" AND `topic_id`=").append("'").append(search.getTopicId()).append("'");
			}

			PreparedStatement stm = conn.prepareStatement(sql.toString());
			ResultSet resultSet = stm.executeQuery();

			while (resultSet.next()) {
				ProcessEntity e = new ProcessEntity();
				e.setId(resultSet.getInt("id"));
				e.setTopicId(resultSet.getInt("topic_id"));

				list.add(e);
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
	 * 删除所有进度日志
	 */
	public void deleteAll() {
		Connection conn = JdbcUtil.getConnection();
		try {
			StringBuilder sql = new StringBuilder("DLETE FROM  process_t WHERE 1=1");
			Statement stm = conn.createStatement();
			stm.execute(sql.toString());
			JdbcUtil.close(stm);
		} catch (Exception e) {
			logger.info("{}", e);
		}
		JdbcUtil.close(conn);
	}

}

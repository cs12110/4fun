package com.pkgs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.TopicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * TODO: MapperXML.vm for Topic
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 19:51
 * Since: 1.0.0
 */
@Mapper
public interface TopicMapper extends BaseMapper<TopicEntity> {

    /**
     * 保存记录
     *
     * @param topic topic
     * @return int
     */
    int save(TopicEntity topic);

    /**
     * 更新update状态
     *
     * @param topicId topicId
     * @param status  status
     * @return int
     */
    int updateDoneStatus(@Param("topicId") Integer topicId, @Param("status") Integer status);

    /**
     * 统计符合条件的数据
     *
     * @param topic 查询条件
     * @return int
     */
    int selectCount(@Param("cm") TopicEntity topic);

    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<TopicEntity> selectByMap(Page<TopicEntity> page, @Param("cm") Map<String, Object> columnMap);


    /**
     * 获取顶级的话题
     *
     * @return List
     */
    List<TopicEntity> queryTopTopics();

    /**
     * 获取子级话题
     *
     * @param parentId parentId
     * @return List
     */
    List<TopicEntity> queryTopics(String parentId);

    /**
     * 根据父级id查询子级所有Id
     *
     * @param id id
     * @return List
     */
    List<Integer> selectChildId(@Param("id") Integer id);
}

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
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<TopicEntity> selectByMap(Page page, @Param("cm") Map<String, Object> columnMap);

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
}

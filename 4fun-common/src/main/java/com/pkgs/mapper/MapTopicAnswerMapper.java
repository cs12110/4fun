package com.pkgs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pkgs.entity.MapTopicAnswerEntity;

/**
 * TODO: MapperXML.vm for MapTopicAnswer
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 19:51
 * Since: 1.0.0
 */
@Mapper
public interface MapTopicAnswerMapper extends BaseMapper<MapTopicAnswerEntity> {

    /**
     * 新增
     *
     * @param entity entity
     * @return int
     */
    int save(MapTopicAnswerEntity entity);

    /**
     * 统计符合条件的数据
     *
     * @param entity 查询条件
     * @return int
     */
    int selectCount(@Param("obj") MapTopicAnswerEntity entity);
}

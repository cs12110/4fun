package com.pkgs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.AnswerEntity;
import com.pkgs.entity.MapTopicAnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

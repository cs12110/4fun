package com.pkgs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

import com.pkgs.entity.zhihu.SentenceEntity;

/**
 * TODO: Mapper for SentenceEntity
 * <p>
 *
 * @author cs12110 create at: 2019-01-16 09:56:29
 * Since: 1.0.0
 */
@Mapper
public interface SentenceMapper extends BaseMapper<SentenceEntity> {

    /**
     * 新增
     *
     * @param entity entity
     * @return int
     */
    int save(SentenceEntity entity);

    /**
     * 统计符合条件的数据
     *
     * @param search 查询条件
     * @return int
     */
    int selectCount(@Param("cm") SentenceEntity search);


    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<SentenceEntity> selectByMap(Page<SentenceEntity> page, @Param("cm") Map<String, Object> columnMap);
}

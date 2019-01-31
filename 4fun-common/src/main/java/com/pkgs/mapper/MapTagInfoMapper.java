package com.pkgs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.douban.MapTagInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * TODO: Mapper for MapTagInfoEntity
 * <p>
 *
 * @author cs12110 create at: 2019-01-31 13:40:33
 * Since: 1.0.0
 */
@Mapper
public interface MapTagInfoMapper extends BaseMapper<MapTagInfoEntity> {

    /**
     * 新增
     *
     * @param entity entity
     * @return int
     */
    int save(MapTagInfoEntity entity);

    /**
     * 统计符合条件的数据
     *
     * @param search 查询条件
     * @return int
     */
    int selectCount(@Param("cm") MapTagInfoEntity search);


    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<MapTagInfoEntity> selectByMap(Page<MapTagInfoEntity> page, @Param("cm") Map<String, Object> columnMap);
}

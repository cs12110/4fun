package com.pkgs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.douban.BookInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * TODO: Mapper for BookInfoEntity
 * <p>
 *
 * @author cs12110 create at: 2019-01-31 13:27:51
 * Since: 1.0.0
 */
@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfoEntity> {

    /**
     * 新增
     *
     * @param entity entity
     * @return int
     */
    int save(BookInfoEntity entity);

    /**
     * 统计符合条件的数据
     *
     * @param search 查询条件
     * @return int
     */
    int selectCount(@Param("cm") BookInfoEntity search);


    /**
     * 根据Link选取id
     *
     * @param link link
     * @return Integer
     */
    Integer selectIdByLink(@Param("link") String link);


    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<BookInfoEntity> selectByMap(Page<BookInfoEntity> page, @Param("cm") Map<String, Object> columnMap);
}
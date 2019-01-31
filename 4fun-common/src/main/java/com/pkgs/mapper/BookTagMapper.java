package com.pkgs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.douban.BookTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * TODO: Mapper for BookTagEntity
 * <p>
 *
 * @author cs12110 create at: 2019-01-31 11:50:22
 * Since: 1.0.0
 */
@Mapper
public interface BookTagMapper extends BaseMapper<BookTagEntity> {

    /**
     * 新增
     *
     * @param entity entity
     * @return int
     */
    int save(BookTagEntity entity);

    /**
     * 更新爬取页码
     *
     * @param id   id
     * @param page 页码
     * @return int
     */
    int updatePageNum(@Param("tagId") Integer id, @Param("page") Integer page);

    /**
     * 更新状态
     *
     * @param id     id
     * @param status 状态值
     * @return int
     */
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 统计符合条件的数据
     *
     * @param search 查询条件
     * @return int
     */
    int selectCount(@Param("cm") BookTagEntity search);


    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<BookTagEntity> selectByMap(Page<BookTagEntity> page, @Param("cm") Map<String, Object> columnMap);
}
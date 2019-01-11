package com.pkgs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * TODO: MapperXML.vm for Answer
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 19:51
 * Since: 1.0.0
 */
@Mapper
public interface AnswerMapper extends BaseMapper<AnswerEntity> {

    /**
     * 新增
     *
     * @param answer answer
     * @return int
     */
    int save(AnswerEntity answer);

    /**
     * 统计符合条件的数据
     *
     * @param answer 查询条件
     * @return int
     */
    int selectCount(@Param("obj") AnswerEntity answer);

    /**
     * 根据连接获取id
     *
     * @param link 连接
     * @return Integer
     */
    Integer selectIdByLink(String link);

    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param columnMap 查询条件
     * @return List
     */
    List<AnswerEntity> selectByMap(Page page, @Param("obj") Map<String, Object> columnMap);


}

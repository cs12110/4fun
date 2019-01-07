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
 * @author: cs12110 create at: 2019/1/6 19:51
 * Since: 1.0.0
 */
@Mapper
public interface AnswerMapper extends BaseMapper<AnswerEntity> {

    List<AnswerEntity> selectByMap(Page page, @Param("cm") Map<String, Object> columnMap);
}

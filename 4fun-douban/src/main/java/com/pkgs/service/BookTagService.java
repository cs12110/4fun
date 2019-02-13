package com.pkgs.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.douban.BookTagEntity;
import com.pkgs.mapper.BookTagMapper;
import com.pkgs.util.ProxyMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 书籍标签业务类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 12:00
 * <p>
 * since: 1.0.0
 */
public class BookTagService {

    private Logger logger = LoggerFactory.getLogger(BookTagService.class);
    private BookTagMapper mapper = ProxyMapperUtil.wrapper(BookTagMapper.class);


    public void saveIfNotExist(BookTagEntity entity) {
        BookTagEntity search = new BookTagEntity();
        search.setLink(entity.getLink());
        int count = mapper.selectCount(search);
        if (count == 0) {
            mapper.save(entity);
        } else {
            logger.debug("Exists: " + JSON.toJSONString(entity));
        }
    }


    public void updatePageNum(Integer tagId, Integer num) {
        mapper.updatePageNum(tagId, num);
    }


    public List<BookTagEntity> selectByMap(Page<BookTagEntity> page, Map<String, Object> search) {
        return mapper.selectByMap(page, search);
    }

    public void updateStatus(Integer id, int status) {
        mapper.updateStatus(id, status);
    }
}

package com.pkgs.service;

import com.alibaba.fastjson.JSON;
import com.pkgs.entity.douban.BookInfoEntity;
import com.pkgs.entity.douban.MapTagInfoEntity;
import com.pkgs.mapper.BookInfoMapper;
import com.pkgs.mapper.MapTagInfoMapper;
import com.pkgs.util.ProxyMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 书籍信息业务类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 13:32
 * <p>
 * since: 1.0.0
 */
public class BookInfoService {

    private static Logger logger = LoggerFactory.getLogger(BookTagService.class);

    private BookInfoMapper bookInfoMapper = ProxyMapperUtil.wrapper(BookInfoMapper.class);
    private MapTagInfoMapper mapTagInfoMapper = ProxyMapperUtil.wrapper(MapTagInfoMapper.class);

    public void saveIfNotExist(Integer tagId, BookInfoEntity entity) {
        Integer bookId = bookInfoMapper.selectIdByLink(entity.getLink());
        if (bookId == null) {
            bookInfoMapper.save(entity);
            bookId = entity.getId();
        } else {
            logger.info("Exists:{}", JSON.toJSONString(entity));
        }

        MapTagInfoEntity search = new MapTagInfoEntity();
        search.setTagId(tagId);
        search.setBookId(bookId);

        int count = mapTagInfoMapper.selectCount(search);
        if (count == 0) {
            mapTagInfoMapper.save(search);
        }
    }
}

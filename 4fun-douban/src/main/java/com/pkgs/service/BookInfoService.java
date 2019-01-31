package com.pkgs.service;

import com.pkgs.entity.douban.BookInfoEntity;
import com.pkgs.entity.douban.MapTagInfoEntity;
import com.pkgs.entity.operation.ExecResult;
import com.pkgs.enums.OperationEnum;
import com.pkgs.mapper.BookInfoMapper;
import com.pkgs.mapper.MapTagInfoMapper;
import com.pkgs.util.ProxyMapperUtil;

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
    private BookInfoMapper bookInfoMapper = ProxyMapperUtil.wrapper(BookInfoMapper.class);
    private MapTagInfoMapper mapTagInfoMapper = ProxyMapperUtil.wrapper(MapTagInfoMapper.class);


    public boolean isExists(String link) {
        Integer bookId = bookInfoMapper.selectIdByLink(link);
        return null != bookId;
    }

    public ExecResult saveIfNotExist(Integer tagId, BookInfoEntity entity) {

        ExecResult result = new ExecResult();
        result.setSuccess(false);

        Integer bookId = bookInfoMapper.selectIdByLink(entity.getLink());
        if (bookId == null) {
            bookInfoMapper.save(entity);
            bookId = entity.getId();
            result.setSuccess(true);
            result.setOp(OperationEnum.INSERT);
        }

        MapTagInfoEntity search = new MapTagInfoEntity();
        search.setTagId(tagId);
        search.setBookId(bookId);

        int count = mapTagInfoMapper.selectCount(search);
        if (count == 0) {
            mapTagInfoMapper.save(search);
            result.setSuccess(true);
            result.setOp(OperationEnum.UPDATE);
        }

        return result;
    }
}

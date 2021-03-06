package com.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.douban.BookTagEntity;
import com.pkgs.handler.BookTagHandler;
import com.pkgs.service.BookTagService;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 11:34
 * <p>
 * since: 1.0.0
 */
public class BookTagHandlerTest {

    @Test
    public void test() {
        String url = "https://book.douban.com/tag";

        BookTagHandler handler = new BookTagHandler();
        List<BookTagEntity> list = handler.get(url);

        //System.out.println(JSON.toJSONString(list, true));


        //BookTagMapper mapper = ProxyMapperUtil.wrapper(BookTagMapper.class);

        BookTagService service = new BookTagService();
        for (BookTagEntity e : list) {
            //mapper.save(e);
            service.saveIfNotExist(e);
        }

        Page<BookTagEntity> page = new Page<>();
        Map<String, Object> search = new HashMap<>();
        search.put("status", 0);
        List<BookTagEntity> list1 = service.selectByMap(page, search);

        for (BookTagEntity t : list1) {
            System.out.println(t);
        }

    }
}

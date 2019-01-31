package com.test;

import com.alibaba.fastjson.JSON;
import com.pkgs.handler.BookListHandler;
import org.junit.Test;

import java.util.List;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 13:13
 * <p>
 * since: 1.0.0
 */
public class BookListHandlerTest {

    @Test
    public void test() {
        BookListHandler handler = new BookListHandler();
        List<String> list = handler.get("https://book.douban.com/tag/小说?start=460&type=T");

        System.out.println(JSON.toJSONString(list, true));
    }
}

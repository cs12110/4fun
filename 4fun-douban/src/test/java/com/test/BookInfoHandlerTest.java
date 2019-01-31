package com.test;

import com.pkgs.entity.douban.BookInfoEntity;
import com.pkgs.handler.BookInfoHandler;
import org.junit.Test;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 8:52
 * <p>
 * since: 1.0.0
 */
public class BookInfoHandlerTest {

    @Test
    public void test() {
        String bookUrl = "https://book.douban.com/subject/6082808/";

        BookInfoHandler handler = new BookInfoHandler();
        BookInfoEntity entity = handler.get(bookUrl);
        System.out.println(entity);

    }
}

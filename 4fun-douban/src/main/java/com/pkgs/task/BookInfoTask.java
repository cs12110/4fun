package com.pkgs.task;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.douban.BookInfoEntity;
import com.pkgs.entity.douban.BookTagEntity;
import com.pkgs.handler.BookInfoHandler;
import com.pkgs.handler.BookListHandler;
import com.pkgs.service.BookInfoService;
import com.pkgs.service.BookTagService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 书籍任务整合类
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 12:40
 * <p>
 * since: 1.0.0
 */
public class BookInfoTask {

    private BookTagService tagService = new BookTagService();
    private BookInfoService infoService = new BookInfoService();
    private BookListHandler bookListHandler = new BookListHandler();
    private BookInfoHandler bookInfoHandler = new BookInfoHandler();

    private static final int PRE_PAGE_NUM = 20;


    public void execute() {
        // 查询所有还没完成爬取的书籍标签
        Page<BookTagEntity> page = new Page<>();
        Map<String, Object> search = new HashMap<>();
        search.put("status", 0);

        List<BookTagEntity> list = tagService.selectByMap(page, search);
        for (BookTagEntity t : list) {
            workout(t);
        }
    }

    private void workout(BookTagEntity entity) {
        int pageNum = getLastPageNum(entity.getBooks());
        pageNum = pageNum > 200 ? 200 : pageNum;

        for (int index = 0; index < pageNum; index++) {
            int start = index * PRE_PAGE_NUM;

            String reqUrl = entity.getLink() + "?start=" + start;

            List<String> bookList = bookListHandler.get(reqUrl);
            processList(entity.getId(), bookList);
            tagService.updatePageNum(entity.getId(), index);

            sleep(10);
        }
    }

    private int getLastPageNum(int books) {
        int temp = books / PRE_PAGE_NUM;
        return books % PRE_PAGE_NUM == 0 ? temp : temp + 1;
    }


    private void processList(Integer tagId, List<String> bookList) {
        for (String bookUrl : bookList) {
            BookInfoEntity entity = bookInfoHandler.get(bookUrl);
            infoService.saveIfNotExist(tagId, entity);
            sleep(15);
        }
    }


    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            // do nothing
        }
    }
}

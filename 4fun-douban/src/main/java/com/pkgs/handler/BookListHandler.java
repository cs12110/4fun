package com.pkgs.handler;

import com.pkgs.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 书籍列表爬虫
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 13:11
 * <p>
 * since: 1.0.0
 */
public class BookListHandler extends AbstractHandler<Object, List<String>> {

    @Override
    public List<String> parse(String html, String reqUrl) {
        if (StrUtil.isEmpty(html)) {
            return Collections.emptyList();
        }
        List<String> bookList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select(".info h2 a");
        elements.forEach(e ->
                bookList.add(e.attr("href"))
        );
        return bookList;
    }
}

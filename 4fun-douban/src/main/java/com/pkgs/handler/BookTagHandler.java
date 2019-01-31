package com.pkgs.handler;

import com.pkgs.entity.douban.BookTagEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 书籍标签爬虫
 * <p/>
 *
 * @author cs12110 created at: 2019/1/30 15:55
 * <p>
 * since: 1.0.0
 */
public class BookTagHandler extends AbstractHandler<Object, List<BookTagEntity>> {

    private String doubanUrlPrefix = "https://book.douban.com";

    @Override
    public List<BookTagEntity> parse(String html, String reqUrl) {
        Document document = Jsoup.parse(html);
        return parseToList(document);
    }

    private List<BookTagEntity> parseToList(Document document) {
        List<BookTagEntity> list = new ArrayList<>();

        Elements tdElements = document.select("td");


        tdElements.forEach(e -> {
            Element aElement = e.select("a").first();
            Element bElement = e.select("b").first();
            if (null != bElement) {
                BookTagEntity entity = new BookTagEntity();
                entity.setLink(doubanUrlPrefix + aElement.attr("href"));
                entity.setName(aElement.text());
                // 获取该标签书籍数量
                String bookNumStr = bElement.text();
                String num = bookNumStr.substring(1, bookNumStr.length() - 1);
                entity.setBooks(Integer.parseInt(num));
                entity.setPage(0);
                entity.setStatus(0);

                list.add(entity);
            }
        });
        return list;
    }
}

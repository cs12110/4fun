package com.pkgs.handler;

import com.pkgs.entity.douban.BookInfoEntity;
import com.pkgs.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 书籍信息爬虫
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 8:47
 * <p>
 * since: 1.0.0
 */
public class BookInfoHandler extends AbstractHandler<Object, BookInfoEntity> {

    @Override
    public BookInfoEntity parse(String html, String reqUrl) {
        if (StrUtil.isEmpty(html)) {
            logger.error("We can't get anything from:{}", reqUrl);
            return null;
        }

        Document document = Jsoup.parse(html);

        // 转换成实体类
        BookInfoEntity info = toEntity(document);
        info.setRatingSum(getRatingSum(document));
        info.setRating(getRating(document));
        info.setSummary(getSummary(document));
        info.setLink(reqUrl);

        return info;
    }

    /**
     * 转换成BookInfoEntity对象
     *
     * @param document document对象
     * @return BookInfoEntity
     */
    private BookInfoEntity toEntity(Document document) {

        // 获取网页基础信息
        Map<String, String> basicInfoMap = new HashMap<>(15);
        Elements elements = document.select("#info");
        elements.select(".pl").forEach(e -> {
            String key = StrUtil.abandonChar(e.text(), ':');
            String elementText = e.nextElementSibling().text();
            String elementValue = StrUtil.abandonChar(elementText, ' ');

            // 可以获取ISBN里面的值
            if (StrUtil.isEmpty(elementValue)) {
                Node sibling = e.nextSibling();
                elementValue = StrUtil.abandonChar(sibling.toString(), ' ');
            }
            basicInfoMap.put(key, elementValue);
        });

        // 转换成实体类
        BookInfoEntity entity = new BookInfoEntity();
        entity.setName(getBookName(document));
        entity.setAuthor(basicInfoMap.get("作者"));
        entity.setIsbn(basicInfoMap.get("ISBN"));
        entity.setPublish(basicInfoMap.get("出版年"));
        entity.setPaperNum(basicInfoMap.get("页数"));
        entity.setTranslator(basicInfoMap.get("译者"));
        return entity;
    }

    private String getBookName(Document document) {
        String bookName = null;
        try {
            Elements nameElements = document.select("span[property=v:itemreviewed]");
            bookName = nameElements.text();
        } catch (Exception e) {
            //do nothing
        }
        return bookName;
    }


    private int getRatingSum(Document document) {
        try {
            Elements ratingSumElements = document.select("span[property=v:votes]");
            return Integer.parseInt(ratingSumElements.text());
        } catch (Exception e) {
            //do nothing
        }
        return 0;
    }


    private float getRating(Document document) {
        try {
            Elements rating = document.select("strong[property=v:average]");
            return Float.parseFloat(rating.text());
        } catch (Exception e) {
            //do nothing
        }
        return 0;
    }

    private String getSummary(Document document) {
        Elements introElements = document.select(".intro");
        if (null == introElements) {
            return null;
        }
        String text = introElements.text();
        return text.trim();
    }
}

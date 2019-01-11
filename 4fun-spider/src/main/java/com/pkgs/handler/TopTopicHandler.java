package com.pkgs.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pkgs.util.SysUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pkgs.entity.TopicEntity;

/**
 * 话题转换类
 *
 * @author cs12110 at 2018年12月10日下午9:54:47
 */
public class TopTopicHandler extends AbstractHandler<List<TopicEntity>> {

    @Override
    public List<TopicEntity> parse(String html) {
        if (null == html) {
            return Collections.emptyList();
        }
        List<TopicEntity> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(html);
            Elements topics = doc.select(".zm-topic-cat-item");
            for (Element e : topics) {
                String dataId = e.attr("data-id");
                String name = e.text();
                TopicEntity topic = new TopicEntity();
                topic.setDataId(dataId);
                topic.setName(name);
                topic.setUpdateTime(SysUtil.getTime());
                list.add(topic);
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return list;
    }

}

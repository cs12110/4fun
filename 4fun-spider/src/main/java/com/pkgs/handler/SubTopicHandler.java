package com.pkgs.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pkgs.entity.TopicEntity;
import com.pkgs.util.SysUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 话题转换类
 *
 * @author cs12110 at 2018年12月10日下午9:54:47
 */
public class SubTopicHandler extends AbstractHandler<List<TopicEntity>> {

    private Integer parentId;

    @Override
    public void setValue(Object value) {
        this.parentId = Integer.parseInt(String.valueOf(value));
    }

    @Override
    public List<TopicEntity> parse(String html) {
        if (null == html) {
            return Collections.emptyList();
        }
        try {
            JSONObject json = JSON.parseObject(html);
            JSONArray arr = (JSONArray) json.get("msg");

            if (arr != null) {
                return arr.stream()
                        .map(obj -> toEntity(String.valueOf(obj)))
                        .filter(Objects::nonNull)
                        .peek(e -> {
                            e.setDone(0);
                        })
                        .collect(Collectors.toList());

            }
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return Collections.emptyList();
    }

    /**
     * 转换成topic对象
     *
     * @param text html内容
     * @return {@link TopicEntity}
     */
    private TopicEntity toEntity(String text) {
        TopicEntity entity = new TopicEntity();
        try {
            Document doc = Jsoup.parse(text);
            Element aTag = doc.select(".blk a").first();
            String link = aTag.attr("href");
            String name = aTag.select("strong").text();
            entity.setParentId(parentId);
            entity.setName(name);
            entity.setLink(link);
            entity.setUpdateTime(SysUtil.getTime());
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

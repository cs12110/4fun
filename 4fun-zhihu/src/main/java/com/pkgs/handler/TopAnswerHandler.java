package com.pkgs.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pkgs.entity.zhihu.AnswerEntity;
import com.pkgs.util.SysUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 话题转换类
 *
 * @author cs12110 at 2018年12月10日下午9:54:47
 */
public class TopAnswerHandler extends AbstractHandler<Integer, List<AnswerEntity>> {

    private Integer topicId;

    @Override
    public void setValue(Integer value) {
        this.topicId = value;
    }

    @Override
    public List<AnswerEntity> parse(String html, String reqUrl) {
        if (null == html) {
            return Collections.emptyList();
        }

        List<AnswerEntity> list = new ArrayList<>();
        try {
            JSONObject json = JSON.parseObject(html);
            JSONArray data = (JSONArray) json.get("data");

            Optional.ofNullable(data).ifPresent(arr -> {
                for (Object each : arr) {
                    AnswerEntity entity = toEntity((JSONObject) each);
                    if (null != entity) {
                        list.add(entity);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return list;
    }

    private AnswerEntity toEntity(JSONObject each) {
        try {
            JSONObject target = (JSONObject) each.get("target");
            // 可能存在专栏,返回null
            JSONObject questionInfo = (JSONObject) target.get("question");
            if (null == questionInfo) {
                return null;
            }

            AnswerEntity answer = new AnswerEntity();
            answer.setTopicId(topicId);
            answer.setSummary(target.getString("excerpt"));
            answer.setUpvoteNum(target.getInteger("voteup_count"));
            answer.setCommentNum(target.getInteger("comment_count"));
            answer.setUpdateAt(target.getString("updated_time"));
            answer.setCreateAt(target.getString("created_time"));
            answer.setAnswerId(getIdFromUrl(target.getString("url")));

            JSONObject authorInfo = (JSONObject) target.get("author");
            answer.setAuthor(authorInfo.getString("name"));
            answer.setAuthorImg(authorInfo.getString("avatar_url"));

            answer.setQuestion(questionInfo.getString("title"));
            answer.setQuestionId(getIdFromUrl(questionInfo.getString("url")));

            String link = SysUtil.getAnswerLink(answer.getQuestionId(), answer.getAnswerId());
            answer.setLink(link);
            answer.setStealAt(SysUtil.getTime());

            return answer;
        } catch (Exception e) {
            logger.error("{}", e);
            return null;
        }

    }

    /**
     * 从url里面获取Id
     *
     * @param url url连接
     * @return String
     */
    private String getIdFromUrl(String url) {
        if (url == null) {
            return "";
        }
        int last = url.lastIndexOf("/");
        return url.substring(last + 1);
    }

}

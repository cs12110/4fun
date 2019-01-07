package com.pkgs.ctrl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.conf.anno.AntiResubmitAnno;
import com.pkgs.entity.AnswerEntity;
import com.pkgs.service.AnswerService;
import com.pkgs.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: controller
 * <p>
 * Author: cs12110 create at: 2019/1/6 17:10
 * Since: 1.0.0
 */
@Controller
@RequestMapping("/rest")
public class RestCtrl {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private TopicService topicService;

    /**
     * 获取回答
     *
     * @param topicId   话题Id
     * @param pageIndex 分页下标
     * @param pageSize  分页大小
     * @return String
     */
    @RequestMapping("/answers")
    @ResponseBody
    @AntiResubmitAnno
    public String answers(String topicId, Integer pageIndex, Integer pageSize) {

        Page<AnswerEntity> page = new Page<>(pageIndex, pageSize);
        List<AnswerEntity> list = answerService.query(topicId, page);

        Map<String, Object> map = new HashMap<>(2);
        map.put("page", page);
        map.put("list", list);

        return JSON.toJSONString(map);
    }


    /**
     * 获取顶级的话题
     *
     * @return String
     */
    @RequestMapping("/topTopics")
    @ResponseBody
    @AntiResubmitAnno
    public String topTopic() {
        return JSON.toJSONString(topicService.queryTopTopics());
    }

    /**
     * 获取父级下面的所有有答案的类型
     *
     * @param parentId 父级话题Id
     * @return List
     */
    @RequestMapping("/topics")
    @ResponseBody
    @AntiResubmitAnno
    public String topic(String parentId) {
        return JSON.toJSONString(topicService.queryTopics(parentId));
    }

}

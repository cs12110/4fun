package com.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.TopicEntity;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.service.TopicService;
import com.pkgs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 10:55
 * <p>
 * since: 1.0.0
 */
public class TopicServiceTest {
    @Test
    public void testSave() {
        TopicService topicService = new TopicService();

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName("topic");
        topicEntity.setDataId(null);
        topicEntity.setLink(null);

        topicService.saveIfNotExists(topicEntity);
        topicService.saveIfNotExists(topicEntity);
    }


    @Test
    public void testUpdate() {
        TopicService topicService = new TopicService();
        topicService.updateDoneStatus(null, 0);
        topicService.updateDoneStatus(1, 1);
    }
}

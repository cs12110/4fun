package com.test;

import com.pkgs.entity.zhihu.TopicEntity;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.service.TopicService;
import com.pkgs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

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

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName("topic");
        topicEntity.setDataId(null);
        topicEntity.setLink(null);

        SqlSession session = SqlSessionUtil.openSession();
        TopicMapper mapper = session.getMapper(TopicMapper.class);
        System.out.println(topicEntity.getId());

        int save = mapper.save(topicEntity);

        System.out.println(topicEntity.getId());
        session.commit();

        //        TopicMapper mapper = SqlSessionUtil.getProxyMapper(TopicMapper.class);
        //
        //        int count = mapper.selectCount(topicEntity);
        //        if (count == 0) {
        //            mapper.save(topicEntity);
        //        }
        //
        //        TopicService topicService = new TopicService();
        //        topicService.saveIfNotExists(topicEntity);
        //        topicService.saveIfNotExists(topicEntity);
    }


    @Test
    public void testUpdate() {
        TopicService topicService = new TopicService();
        topicService.updateAttr(null, 0);
        topicService.updateAttr(1, 1);
    }


    @Test
    public void testProxyMapper() {
        TopicService topicService = new TopicService();
        topicService.updateAttr(null, 0);
        topicService.updateAttr(1, 1);
    }


}

package com.pkgs;

import com.pkgs.entity.TopicEntity;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * App
 *
 * @author cs12110 at 2018年12月10日下午9:38:34
 */
public class App {

    public static void main(String[] args) {
        SqlSession session = SqlSessionUtil.openSession();
        TopicMapper mapper = session.getMapper(TopicMapper.class);

        List<TopicEntity> topicEntities = mapper.queryTopTopics();
        topicEntities.forEach(System.out::println);

        session.close();
    }
    

}

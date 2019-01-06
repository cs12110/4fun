package com.test;

import com.pkgs.entity.TopicEntity;
import com.pkgs.util.AutoGenUtil;
import org.junit.Test;

/**
 * TODO:
 * <p>
 * Author: cs12110 create at: 2019/1/6 21:03
 * Since: 1.0.0
 */
public class AutoGenTest {

    @Test
    public void test() {
        String mapperXml = AutoGenUtil.genMapperXml(TopicEntity.class);

        System.out.println(mapperXml);
    }
}

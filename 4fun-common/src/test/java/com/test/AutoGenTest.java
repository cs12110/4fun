package com.test;

import com.pkgs.entity.zhihu.SentenceEntity;
import com.pkgs.util.AutoGenUtil;
import org.junit.Test;

/**
 * TODO:
 * <p>
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 21:03
 * Since: 1.0.0
 */
public class AutoGenTest {

    @Test
    public void testMapperXml() {
        String mapperXml = AutoGenUtil.genMapperXml(SentenceEntity.class);
        System.out.println(mapperXml);
    }


    @Test
    public void testMapper() {
        String mapper = AutoGenUtil.genMapperInterface(SentenceEntity.class);
        System.out.println(mapper);
    }


    @Test
    public void testSql() {
        String sql = AutoGenUtil.genSql(SentenceEntity.class);
        System.out.println(sql);
    }
}

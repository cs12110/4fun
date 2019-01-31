package com.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.pkgs.entity.douban.MapTagInfoEntity;
import com.pkgs.util.AutoGenUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * TODO:
 * <p>
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 21:03
 * Since: 1.0.0
 */
public class AutoGenTest {

    @Before
    public void before() {
        Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("root");
        root.setLevel(Level.ERROR);
    }

    @Test
    public void testMapperXml() {
        String mapperXml = AutoGenUtil.genMapperXml(MapTagInfoEntity.class);
        System.out.println(mapperXml);
    }


    @Test
    public void testMapper() {
        String mapper = AutoGenUtil.genMapperInterface(MapTagInfoEntity.class);
        System.out.println(mapper);
    }


    @Test
    public void testSql() {
        String sql = AutoGenUtil.genSql(MapTagInfoEntity.class);
        System.out.println(sql);
    }
}

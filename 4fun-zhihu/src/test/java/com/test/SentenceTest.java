package com.test;

import com.pkgs.entity.zhihu.SentenceEntity;
import com.pkgs.mapper.SentenceMapper;
import org.junit.Test;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/16 10:00
 * <p>
 * since: 1.0.0
 */
public class SentenceTest {

    @Test
    public void test() {

        SentenceEntity entity = new SentenceEntity();
        entity.setAuthor("haiyan");
        entity.setSentence("I don't care at all");
        entity.setIdx(1);
        entity.setWallpaper(null);

        SentenceMapper wrapper = ProxyMapperUtil.wrapper(SentenceMapper.class);
        wrapper.save(entity);
    }
}

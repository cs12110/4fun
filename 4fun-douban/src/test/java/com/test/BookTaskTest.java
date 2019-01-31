package com.test;

import com.pkgs.task.BookInfoTask;
import org.junit.Test;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 13:06
 * <p>
 * since: 1.0.0
 */
public class BookTaskTest {

    @Test
    public void test() {
        BookInfoTask task = new BookInfoTask();
        task.execute();
    }
}

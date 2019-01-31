package com.pkgs;

import com.pkgs.task.BookInfoTask;

/**
 * Book App
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 13:50
 * <p>
 * since: 1.0.0
 */
public class BookApp {

    public static void main(String[] args) {
        BookInfoTask task = new BookInfoTask();
        task.execute();
    }
}

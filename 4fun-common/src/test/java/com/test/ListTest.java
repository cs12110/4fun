package com.test;

import java.util.Arrays;
import java.util.List;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/16 11:16
 * <p>
 * since: 1.0.0
 */
public class ListTest {

    public void test() {
        int[] arr = {1, 2, 3, 4};

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        List<int[]> ints = Arrays.asList(arr);
    }
}

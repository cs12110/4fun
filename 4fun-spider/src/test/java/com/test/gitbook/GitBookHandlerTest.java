package com.test.gitbook;

import java.util.List;

import org.junit.Test;

/**
 * 哈哈收到货
 *  
 * 
 *
 * <p>
 *
 * @author cs12110 2018年12月24日下午4:44:50
 * @see
 * @since 1.0
 */
public class GitBookHandlerTest {

	@Test
	public void test() {
		GitBookHandler handler = new GitBookHandler();

		String url = "https://www.baidu.com/s?wd=Spring%20site:gitbook.com";
		List<GitBookAdvice> list = handler.get(url);

		for (GitBookAdvice each : list) {
			System.out.println(each);
		}

	}
}

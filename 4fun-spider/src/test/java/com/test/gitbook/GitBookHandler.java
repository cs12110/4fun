package com.test.gitbook;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pkgs.handler.AbstractHandler;

public class GitBookHandler extends AbstractHandler<List<GitBookAdvice>> {

	@Override
	public List<GitBookAdvice> parse(String html) {

		Document doc = Jsoup.parse(html);
		Elements topAdvices = doc.select(".t a");

		List<GitBookAdvice> list = new ArrayList<>();
		for (Element e : topAdvices) {
			// list.add(getRealLink(e.attr("href")));
			GitBookAdvice book = new GitBookAdvice();
			book.setUrl(getRealLink(e.attr("href")));
			book.setDesc(e.text());

			list.add(book);
		}

		return list;
	}

	/**
	 * 获取真实的连接
	 * 
	 * @param baiduLink
	 *            百度连接
	 * @return String
	 */
	private static String getRealLink(String baiduLink) {
		try {
			Connection conn = Jsoup.connect(baiduLink).timeout(2000).method(Method.GET).followRedirects(false);
			Response execute = conn.execute();
			String readLink = execute.header("Location");
			return readLink;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}

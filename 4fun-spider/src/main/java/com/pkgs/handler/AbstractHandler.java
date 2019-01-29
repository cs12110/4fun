package com.pkgs.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 * <p>
 * FYI:
 * <pre>
 * P: parameter type
 * R: result type
 * </pre>
 *
 * @author cs12110 at 2018年12月10日下午9:40:14
 */
public abstract class AbstractHandler<P, R> {

    /**
     * 日志类
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * User Agent
     */
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36";


    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(P value) {
    }

    /**
     * By get
     *
     * @param url url
     */
    public R get(String url) {
        String resultStr = null;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        setUserAgent(get);

        // 使用 try...resource
        try (CloseableHttpResponse result = client.execute(get)) {
            int code = result.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity entity = result.getEntity();
                resultStr = EntityUtils.toString(entity, "utf-8");
            } else {
                logger.info("failure to get:{},{}", url, result.getStatusLine());
            }

            closeHttpClient(client);
        } catch (Exception e) {
            logger.error("{}", e);
        }

        return parse(resultStr, url);
    }


    /**
     * By post
     *
     * @param url    url
     * @param params 查询参数
     * @return R
     */
    public R post(String url, Map<String, String> params) {
        String resultStr = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        setUserAgent(post);

        // 设置请求参数
        if (null != params && params.size() > 0) {
            List<BasicNameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                post.setEntity(entity);
            } catch (Exception e) {
                logger.error("{}", e);
            }
        }

        // 请求
        try (CloseableHttpResponse result = client.execute(post)) {
            int code = result.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity entity = result.getEntity();
                resultStr = EntityUtils.toString(entity, "utf-8");
            } else {
                logger.info("failure to post:{},{}", url, result.getStatusLine());
            }
            closeHttpClient(client);
        } catch (Exception e) {
            logger.error("{}", e);
        }

        return parse(resultStr, url);
    }

    /**
     * 设置user-agent
     *
     * @param req {@link HttpUriRequest}
     */
    private void setUserAgent(HttpUriRequest req) {
        req.setHeader("User-Agent", USER_AGENT);
    }

    /**
     * 关闭http client
     *
     * @param client client
     */
    private static void closeHttpClient(CloseableHttpClient client) {
        if (null != client) {
            try {
                client.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    /**
     * 转换成实体类
     *
     * @param html   html
     * @param reqUrl 请求地址
     * @return R
     */
    public abstract R parse(String html, String reqUrl);


}

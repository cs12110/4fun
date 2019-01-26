package com.pkgs.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
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
 *
 * @author cs12110 at 2018年12月10日下午9:40:14
 */
public abstract class AbstractHandler<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36";


    public void setValue(Object value) {
    }

    /**
     * By get
     *
     * @param url url
     */
    public T get(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        String resultStr = null;

        Registry<CookieSpecProvider> cookieSpecProviderRegistry = RegistryBuilder.<CookieSpecProvider>create()
                .register("CookieHandler", context -> new CookiesHandler()).build();

        //注册自定义CookieSpec
        HttpClientContext context = HttpClientContext.create();
        context.setCookieSpecRegistry(cookieSpecProviderRegistry);
        try {
            HttpGet get = new HttpGet(url);
            get.setConfig(RequestConfig.custom().setCookieSpec("CookieHandler").build());
            setUserAgent(get);
            CloseableHttpResponse result = client.execute(get, context);
            int code = result.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity entity = result.getEntity();
                resultStr = EntityUtils.toString(entity, "utf-8");
            } else {
                logger.info("failure to get:{},{}", url, result.getStatusLine());
            }
            result.close();
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                //do nothing
            }
        }
        return parse(resultStr);
    }

    /**
     * By post
     *
     * @param url    url
     * @param params 查询参数
     * @return T
     */
    public T post(String url, Map<String, String> params) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String resultStr = null;
        try {
            HttpPost post = new HttpPost(url);
            setUserAgent(post);

            if (null != params && params.size() > 0) {
                List<BasicNameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                post.setEntity(entity);
            }

            CloseableHttpResponse result = client.execute(post);
            int code = result.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity entity = result.getEntity();
                resultStr = EntityUtils.toString(entity, "utf-8");
            } else {
                logger.info("failure to post:{},{}", url, result.getStatusLine());
            }
            result.close();
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                //do nothing
            }
        }
        return parse(resultStr);
    }

    /**
     * 转换成实体类
     *
     * @param html html
     * @return T
     */
    public abstract T parse(String html);

    /**
     * 设置user-agent
     *
     * @param req {@link HttpUriRequest}
     */
    private void setUserAgent(HttpUriRequest req) {
        req.setHeader("User-Agent", USER_AGENT);

    }
}

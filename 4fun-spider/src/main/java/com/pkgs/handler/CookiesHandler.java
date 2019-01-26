package com.pkgs.handler;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicHeader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Cookies handler for
 * <p>
 * 并没有什么卵用......
 *
 * <pre>
 *  一月 26, 2019 9:58:55 上午 org.apache.http.client.protocol.ResponseProcessCookies processCookies
 * 警告: Invalid cookie header: "Set-Cookie: l_cap_id="MGFlY2FlNTAxMzUyNGI1YzliOGNmNmI5ZGFmNGYwNWM=|1548468219|1e0694d62e6d2b0efeeebc1188f3ff2e928033b1"; Domain=zhihu.com; expires=Mon, 25 Feb 2019 02:03:39 GMT; Path=/". Invalid 'expires' attribute: Mon, 25 Feb 2019 02:03:39 GMT
 * </pre>
 *
 * @author cs12110 created at: 2019/1/26 10:00
 * <p>
 * since: 1.0.0
 */
public class CookiesHandler extends DefaultCookieSpec {

    @Override
    public List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException {
        String value = header.getValue();
        String prefix = "expires=";
        if (value.contains(prefix)) {
            // 获取过期日期
            String expires = value.substring(value.indexOf(prefix) + prefix.length());
            int endIndex = expires.indexOf(";");
            if (endIndex == -1) {
                endIndex = expires.length();
            }
            expires = expires.substring(0, endIndex);

            // 替换过期日期
            String date = formatDate(expires);
            value = value.replaceAll(expires, date);
        }

        // 重新构造header
        header = new BasicHeader(header.getName(), value);
        return super.parse(header, origin);
    }

    /**
     * 转换cookies过期日期
     *
     * @param gmtDateStr gmt日期格式字符串
     * @return String
     */
    private static String formatDate(String gmtDateStr) {
        long timestamp;
        SimpleDateFormat standardFormat = new SimpleDateFormat("EEE, dd-MMM-yy HH:mm:ss z", Locale.US);
        try {
            SimpleDateFormat gmtDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            Date date = gmtDateFormat.parse(gmtDateStr);
            timestamp = date.getTime();
        } catch (Exception e) {
            Date now = new Date();
            timestamp = now.getTime();
        }
        timestamp += 1000 * 60 * 60 * 24;
        return standardFormat.format(timestamp);
    }
}

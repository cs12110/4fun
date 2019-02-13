package com.pkgs.conf;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * mysql conf
 * <p/>
 *
 * @author cs12110 created at: 2019/2/13 14:41
 * <p>
 * since: 1.0.0
 */
@Data
public class DbConf {
    /**
     * url
     */
    private String url;

    /**
     * user
     */
    private String user;

    /**
     * password
     */
    private String password;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

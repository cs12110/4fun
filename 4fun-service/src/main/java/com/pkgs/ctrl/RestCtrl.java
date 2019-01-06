package com.pkgs.ctrl;

import com.alibaba.fastjson.JSON;
import com.pkgs.entity.AnswerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 * <p>
 * Author: cs12110 create at: 2019/1/6 17:10
 * Since: 1.0.0
 */
@Controller
@RequestMapping("/rest")
public class RestCtrl {


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        Map<String, Object> map = new HashMap<>();
        map.put("clazz", this.getClass().getName());
        map.put("timestamp", System.currentTimeMillis());
        map.put("obj", new AnswerEntity());
        return JSON.toJSONString(map);
    }
}

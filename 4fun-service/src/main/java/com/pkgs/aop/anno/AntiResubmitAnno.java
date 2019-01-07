package com.pkgs.aop.anno;

import java.lang.annotation.*;

/**
 * 防止重复提交注解
 * <p/>
 *
 * @author cs12110 created at: 2019/1/7 8:32
 * <p>
 * since: 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiResubmitAnno {
}

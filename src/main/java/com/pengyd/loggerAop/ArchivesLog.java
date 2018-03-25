package com.pengyd.loggerAop;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: TODO - 
 * @author: pengyd
 * @createTime: 2018年3月9日 下午12:11:04
 *
 */
@Target({ java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ArchivesLog {
    public abstract String operationType();

    public abstract String operationName();
}

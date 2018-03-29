package com.pengyd.loggerAop;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:
 */
@Target({ java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ArchivesLog {
    public abstract String operationType();

    public abstract String operationName();
}

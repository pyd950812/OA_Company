package com.pengyd.util;

import java.util.logging.Logger;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    private static Logger logger = Logger.getLogger(SpringContextUtil.class.getName());

    /** 
     * 取得存储在静态变量中的ApplicationContext. 
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /** 
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型. 
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /** 
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型. 
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /** 
     * 清除SpringContextHolder中的ApplicationContext为Null. 
     */
    public static void clearHolder() {
        logger.info("清除SpringContextUtil中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    /** 
     * 实现ApplicationContextAware接口, 注入Context到静态变量中. 
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext != null) {
            logger.info("SpringContextUtil中的ApplicationContext被覆盖, 原有ApplicationContext为:" + applicationContext);
        }

        setApplicationContextValue(applicationContext);
    }

    public static void setApplicationContextValue(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /** 
     * 实现DisposableBean接口, 在Context关闭时清理静态变量. 
     */
    @Override
    public void destroy() throws Exception {
        clearHolder();
    }

    /** 
     * 检查ApplicationContext不为空. 
     */
    private static void assertContextInjected() {
        if (applicationContext == null)
            logger.info("applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextUtil.");
    }
}
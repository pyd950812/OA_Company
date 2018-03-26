package com.pengyd.loggerAop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.pengyd.bean.Employee;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * @description: TODO - 
 * @author: pengyd
 * @createTime: 2018年3月9日 下午12:11:47
 */
@Aspect
public class ArchivesLogAspect {
    private final Logger logger = Logger.getLogger(getClass());

    private StringBuffer requestURL = null;

    private String requestQuery = null;

    private long startTimeMillis = 0L;

    private long endTimeMillis = 0L;

    private Employee current_emp = null;

    private HttpServletRequest request = null;

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    private Properties properties = new Properties();

    private InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");

    public void before(JoinPoint joinPoint) {
        this.request = getHttpservletRequest();

        this.current_emp = ((Employee) this.request.getSession().getAttribute("current_emp"));
        this.startTimeMillis = System.currentTimeMillis();
    }

    public void after(JoinPoint joinPoint) {
        this.request = getHttpservletRequest();
        this.requestURL = this.request.getRequestURL();

        this.requestQuery = this.request.getQueryString();
        try {
            this.properties.load(this.inputStream);

            String operatingLogStr = this.properties.getProperty("operatingLogStr");

            if (this.requestQuery != null) {
                this.requestURL.append("?").append(this.requestQuery);
            }

            String targetName = joinPoint.getTarget().getClass().getName();

            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = null;

            targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if ((clazzs != null) && (clazzs.length == arguments.length)
                            && (method.getAnnotation(ArchivesLog.class) != null)) {
                        operationName = ((ArchivesLog) method.getAnnotation(ArchivesLog.class)).operationName();
                        break;
                    }
                }
            }
            this.endTimeMillis = System.currentTimeMillis();
            if (this.current_emp != null) {
                this.logger.info("操作人： " + this.current_emp.getLoginname() + " 操作方法： " + operationName + " 耗时 "
                        + (this.startTimeMillis - this.endTimeMillis) + " ms");

                File exFile = new File(operatingLogStr + this.current_emp.getLoginname() + "/"
                        + this.sdf2.format(new Date()) + methodName + ".log");
                if (!exFile.getParentFile().exists()) {
                    boolean result = exFile.getParentFile().mkdirs();
                    exFile.createNewFile();
                    if (!result) {
                        System.out.println("创建失败");
                    }
                }
                FileOutputStream out = new FileOutputStream(exFile, true);
                StringBuffer sb = new StringBuffer();

                sb.append("=========" + this.sdf1.format(new Date()) + "=========\r\n");
                sb.append("操作人:【" + this.current_emp.getLoginname() + "】\r\n");
                sb.append("请求URL:【" + this.requestURL + "】\r\n");
                sb.append("接口方法:【" + targetName + "." + methodName + "】\r\n");
                sb.append("操作耗时:【" + (this.startTimeMillis - this.endTimeMillis) + " ms】\r\n");
                out.write(sb.toString().getBytes("utf-8"));
                out.close();
                //System.out.println("Done");
            }
            else {
                this.logger.info("操作人： null操作人IP： " + getIp() + " 操作方法： " + operationName + " 耗时 "
                        + (this.startTimeMillis - this.endTimeMillis) + " ms");

                File exFile = new File(operatingLogStr + "操作人IP： " + getIp() + "/" + this.sdf2.format(new Date())
                        + methodName + ".log");
                if (!exFile.getParentFile().exists()) {
                    boolean result = exFile.getParentFile().mkdirs();
                    exFile.createNewFile();
                    if (!result) {
                        System.out.println("创建失败");
                    }
                }
                FileOutputStream out = new FileOutputStream(exFile, true);
                StringBuffer sb = new StringBuffer();

                sb.append("=========" + this.sdf1.format(new Date()) + "=========\r\n");
                sb.append("操作人:【操作人： null-IP:" + getIp() + "】\r\n");
                sb.append("请求URL:【" + this.requestURL + "】\r\n");
                sb.append("接口方法:【" + targetName + "." + methodName + "】\r\n");
                sb.append("操作耗时:【" + (this.startTimeMillis - this.endTimeMillis) + " ms】\r\n");
                out.write(sb.toString().getBytes("utf-8"));
                out.close();
                //System.out.println("Done");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getIp() {
        String ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public void afterThrow(JoinPoint joinPoint, Exception ex) {
        System.out.println("进入切面方法异常日志");

        if (this.logger.isInfoEnabled()) {
            this.logger.info("afterThrow" + joinPoint + "\t" + ex.getMessage());
        }

        String errorMsg = "";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement se : trace) {
            errorMsg = errorMsg + "\tat " + se + "\r\n";
        }
        System.out.println("具体异常信息" + errorMsg);
        System.out.println("afterThrow异常方法名" + joinPoint + "\t" + ex.getMessage());
        System.out.println("进入切面方法异常日志结束");
        writeLog(errorMsg, joinPoint, ex);
    }

    public void writeLog(String detailErrorMsg, JoinPoint joinPoint, Exception ex) {
        this.request = getHttpservletRequest();
        StringBuffer requestURL = this.request.getRequestURL();

        String requestQuery = this.request.getQueryString();

        if (requestQuery != null) {
            requestURL.append("?").append(requestQuery);
        }

        String targetName = joinPoint.getTarget().getClass().getName();

        String methodName = joinPoint.getSignature().getName();
        try {
            this.properties.load(this.inputStream);
            String errorLogStr = this.properties.getProperty("errorLogStr");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            File exFile = null;

            if (this.current_emp == null)
                exFile = new File(errorLogStr + getIp() + "/" + sdf2.format(new Date()) + methodName + ".log");
            else {
                exFile = new File(errorLogStr + this.current_emp.getLoginname() + "/" + sdf2.format(new Date())
                        + methodName + ".log");
            }
            if (!exFile.getParentFile().exists()) {
                boolean result = exFile.getParentFile().mkdirs();
                exFile.createNewFile();
                if (!result) {
                    System.out.println("创建失败");
                }
            }

            FileOutputStream out = new FileOutputStream(exFile, true);
            StringBuffer sb = new StringBuffer();
            sb.append("=========" + sdf1.format(new Date()) + "=========\r\n");
            sb.append("请求URL:【" + requestURL + "】\r\n");
            sb.append("接口方法:【" + targetName + "." + methodName + "】\r\n");
            sb.append("详细错误信息:" + ex + "\r\n");
            sb.append(detailErrorMsg + "\r\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
            System.out.println("Done");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getHttpservletRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
}

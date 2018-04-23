package com.pengyd.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;


public class CommonUtils {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();//获取开始时间

        System.out.println(CommonUtils.timeUUID());
        System.out.println(CommonUtils.timeID());
        System.out.println(CommonUtils.timeMinID());

        long endTime = System.nanoTime();//获取结束时间
        long runTimeNS = endTime - startTime;
        System.out.println("程序运行时间： " + runTimeNS + "ns");//毫微秒 - 纳秒 - 
        int runTimeS = (int) (runTimeNS / 1000000000);
        System.out.println("程序运行时间： " + runTimeS + "s");//秒 - 
        System.out.println("程序运行时间： " + runTimeS / 60 + "min：" + runTimeS % 60 + "s");//分
    }

    //流水号采用时间流水-唯一号采用UUID
    public static String timeUUID() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + System.currentTimeMillis();
    }

    /**
     * 返回一个时间+5位随机数
     * @return
     */
    public static String timeID() {
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
        return new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + rannum;
    }

    /**
     * 中原银行的订单号不超过20位 - 给他一个20位的
     * @return
     */
    public static String timeMinID() {
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (999 - 100 + 1)) + 100;// 获取3位随机数  
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + rannum;
    }

    /**
     * 返回一个不重复的字符串
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 把map转换成Bean对象
     * @param map
     * @param clazz
     * @return
     * 
     * 把Map转换成指定类型
     */
    @SuppressWarnings("rawtypes")
    public static <T> T toBean(Map map, Class<T> clazz) {
        try {
            /*
             * 1. 通过参数clazz创建实例
             * 2. 使用BeanUtils.populate把map的数据封闭到bean中
             */
            T bean = clazz.newInstance();
            BeanUtils.populate(bean, map);
            return bean;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换字符串为java.util.Date
     * @return
     */
    public static Date strToDate(String datetime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换字符串为java.util.Date
     * @return
     */
    public static Date strToTicketDate(String datetime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换字符串为java.util.Date
     * @return
     */
    public static Date strToTicketDateNoDate(String datetime) {
        try {
            return new SimpleDateFormat("HH:mm").parse(datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换字符串为java.util.Date - Birthday - yyyy-MM-dd - 数据库获取的是字符串，没有这个date类型
     * @return
     */
    public static Date strToBirthday(String datetime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDutyDate(String datetime) {
        try {
            String dateStr = getNowDateStrByDate();//yyyy-MM-dd HH:mm:ss.SSS
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr + " " + datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNowDateStrByDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 转换字符串为java.sql.Timestamp
     * @return
     */
    public static Timestamp strToTimestamp(String datetime) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(datetime);
            return new Timestamp(d.getTime());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换字符串为java.sql.Timestamp
     * @return
     */
    public static String timestampToStr(Timestamp datetime) {
        Date d = new Date(datetime.getTime());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(d);
    }

    public static String timestampToDateStr(Timestamp datetime) {
        Date d = new Date(datetime.getTime());
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    /**
     * 转换yyyy-MM-dd字符串为 M月d日
     * @return
     */
    public static String strToMonthDate(String datetime) {
        Date Date = null;
        try {
            Date = new SimpleDateFormat("yyyy-MM-dd").parse(datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date);
        return calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
    }

    /**
     * 转换hh:mm - h:m
     * @return
     */
    public static String strToHourMinute(String datetime) {
        Date Date = null;
        try {
            Date = new SimpleDateFormat("hh:mm").parse(datetime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date);
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    /**
     * 火车票出发和结束日期历时计算 - 形如29小时58分钟 - 29:58
     * @return
     */
    public static String diffDate(Date arrivalTime, Date departTime) {
        Long msDiff = arrivalTime.getTime() - departTime.getTime();
        int minute = (int) (msDiff / 1000 / 60);
        int hour = minute / 60;
        minute = minute % 60;
        return hour + ":" + minute;
    }

    /**
     * 转换字符串为int类型
     * @return
     * @throws Exception 
     */
    public static int strToInteger(String str) throws Exception {
        if (str == null) {
            throw new Exception("字符串为null");
        }
        else if (str.trim().equals("")) {
            throw new Exception("为空字符串");
        }
        else {
            return Integer.valueOf(str);
        }
    }

    /**
     * 转换字符串为Double类型
     * @return
     * @throws Exception 
     */
    public static Double strToDouble(String str) throws Exception {
        if (str == null) {
            throw new Exception("字符串为null");
        }
        else if (str.trim().equals("")) {
            throw new Exception("为空字符串");
        }
        else {
            return Double.valueOf(str);
        }
    }

    /**
     * 返回一个带毫秒值的日期修饰之后不重复的值 - 20170303140125020019202284435644 - 32
     *  加上当前系统的纳秒值，同样保证了不重复
     * @return
     */
    public static String msDateNaNoID() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + System.nanoTime();
    }

    /**
     * 转换字符串为java.util.Date
     * @return
     */
    public static Date getNowDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toStrByDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
    }

    public static String toStrByDateNoms(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String toStrByNowDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 12车厢,005座 转换为 12车5号
     * @return
     */
    public static String getCoachSeatNo(String CoachSeatNo) {
        return CoachSeatNo.replace("厢,", "").replace("0", "").replace("座", "号");
    }

    /** 字符串是否为空 */
    public static boolean StringIsNull(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

}

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.httpclient.HttpConstants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author pengyd
 * @Date 2018/3/20 15:27
 * @function:
 */
@ContextConfiguration(locations = {"classpath:application.properties"})
public class MapTest {

    @Value("${errorLogStr}")
    private String sss;

    public static void main(String[] args) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("执行结束时间："+s.format(new Date())+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public String  s (){

        return sss;
    }


}

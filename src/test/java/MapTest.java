
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author pengyd
 * @Date 2018/3/20 15:27
 * @function:
 */
@ContextConfiguration(locations = "classpath:application.properties")
public class MapTest {
    private static Logger logger = LoggerFactory.getLogger(MapTest.class);
    @Value("${errorLogStr}")
    private static String sss;

    public static void main(String[] args) {
/*        System.out.println(sss);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("执行结束时间：" + s.format(new Date()) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        logger.info("执行结束时间：" + s.format(new Date()) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        logger.debug("debug test");
        logger.info("info test");
        logger.error("error test");
        teetete(new ModelMap());*/
        xyz();
    }

    public static void teetete(ModelMap model){
        model.addAttribute("aaaa","bbbbb");

        System.out.println(model.containsAttribute("aaaa"));

    }

    public static void xyz(){
        Properties properties = new Properties();
        InputStream resourceAsStream = Object.class.getResourceAsStream("/test.properties");
        try {
            properties.load(resourceAsStream);
            String a = properties.getProperty("a");
            System.out.println(a);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

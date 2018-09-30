package ThreadPoolExecutor;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author pengyd
 * @Date 2018/9/30 15:41
 * @function: 开十个线程跑一个Excle中的数据
 */
public class ThreadToOneList {

    public static void main(String[] args) throws Exception {
        List<User> listAll = new ArrayList<>();
        String fileName = "D:/3320.txt";
        //构造一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String str = "";
        while ((str = br.readLine()) != null) {
            String[] split = str.split(",");
            User user = new User();
            user.setPname(split[0]);
            user.setPphone(split[1]);
            user.setPdocument_no(split[2]);
            listAll.add(user);
        }

        int length = listAll.size(); //所有的数据
        int num = 10; //线程数
        int listSize = length / num;  //子list的长度

        List<User> list;  //每个线程需要跑的list
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                list = listAll.subList(i * listSize, length);
            } else {
                list = listAll.subList(i * listSize, (i + 1) * listSize);
            }
            try {
                threadPoolExecutor.execute(new ThreadTest(list));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

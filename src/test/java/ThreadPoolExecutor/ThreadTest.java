package ThreadPoolExecutor;

import PdopTest.HttpClientUtil;
import PdopTest.sign.SignBundle;
import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author pengyd
 * @Date 2018/9/30 15:44
 * @function:
 */

public class ThreadTest implements Runnable {
    private List<User> list;
    public ThreadTest(List<User> list) {
        this.list = list;
    }
    public ThreadTest() {
    }
    public void test(List<User> list) {
        //商户MD5签名冗余串，用于MD5的加签验签，需要配置为聚泛分配给商户的MD5冗余串
        //小象优品
        String redundantStr = "redundant";
        //商户API的TOKEN，需要配置为聚泛分配给商户的APIToken
        //聚泛生产token
        String merchantApiToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXN0ZW0iLCJqdGkiOiIzOTA2ZmYyMS03ZjFkLTQ3ZTctOGM2OS05MTcyNmExMTFlY2YiLCJtZXJjaGFudElkIjoiOTk5OTkiLCJ1c2VySWQiOiJzeXN0ZW0iLCJpYXQiOjE0OTY4OTAyOTIsImlzcyI6Imp1ZmFuLmNvbSIsIm9yaWdpbiI6ImFwaSJ9.iVK3eBNpPU81ISDJTGZbT_B_1RAOfT3o6ALuBc8OF-M";
        //查询URL，由聚泛分配
        String queryURL = "http://app6.jufandev.com:8080/ParticleDataOpenPlatform/product/query.action";  //生产
//        String queryURL = "http://t.jufandev.com:8022/ParticleDataOpenPlatform/product/query.action";  //测试环境
        ////////////////////////////////////////////////////////////////以下开始构造测试数据//////////////////////////////////////////////////////////////////////
        Map<String, String> paramMap = new HashMap<>();
        for (User user : list) {
            paramMap.put("pname", user.getPname());
            paramMap.put("pphone", user.getPphone());
            paramMap.put("pdocument_no", user.getPdocument_no());

            paramMap.put("codeNumber", "P1068");//产品编号

            //设置签名方法，目前只支持RSA方式和MD5方式
            String signType = SignBundle.SIGN_TYPE_MD5;
            paramMap.put("signType", signType);

            switch (signType.toUpperCase()) {
                //RSA签名方式
                case SignBundle.SIGN_TYPE_RSA:
//                    paramMap.put("signature", SignBundle.signWithRSA(paramMap, merchantRSAPrivateKey, SignBundle.CHARSET_UTF_8));
                    break;
                //MD5签名方式
                case SignBundle.SIGN_TYPE_MD5:
                    paramMap.put("signature", SignBundle.signWithMD5(paramMap, redundantStr));
            }

            Header[] headers = {new BasicHeader("X-AUTH-TOKEN", merchantApiToken), new BasicHeader("Content-type", "application/json")};
            String respJson = HttpClientUtil.sendPostMessage(queryURL, JSON.toJSONString(paramMap), headers);
            System.out.println(user.getPdocument_no() + "返回数据：" + respJson);

        }


    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "=====时间=====" + new Date());
            test(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

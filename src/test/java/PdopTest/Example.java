package PdopTest;

import PdopTest.sign.SignBundle;
import com.alibaba.fastjson.JSON;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gala on 5/26/2017.<br>
 *     测试用例
 */
public class Example {
    public static void main(String[] args) throws Exception{
        //商户RSA私钥，用于对报文进行RSA加密，需要与商户交付于聚泛的RSA公钥对应
        String merchantRSAPrivateKey = "";
        //商户MD5签名冗余串，用于MD5的加签验签，需要配置为聚泛分配给商户的MD5冗余串

        //聚泛
        String redundantStr = "redundant";
        //聚泛
//        String redundantStr = "asdfasdfsadghfgsdfjkwdg";

        //商户API的TOKEN，需要配置为聚泛分配给商户的APIToken
        //聚泛
        String merchantApiToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXN0ZW0iLCJqdGkiOiIzOTA2ZmYyMS03ZjFkLTQ3ZTctOGM2OS05MTcyNmExMTFlY2YiLCJtZXJjaGFudElkIjoiOTk5OTkiLCJ1c2VySWQiOiJzeXN0ZW0iLCJpYXQiOjE0OTY4OTAyOTIsImlzcyI6Imp1ZmFuLmNvbSIsIm9yaWdpbiI6ImFwaSJ9.iVK3eBNpPU81ISDJTGZbT_B_1RAOfT3o6ALuBc8OF-M";
        //现金分期
//        String merchantApiToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXN0ZW0iLCJqdGkiOiIwMjkwOWU3YS02ZjNhLTQ0YzItOWJmYi1mYzM1ODhlYjE3ZWUiLCJtZXJjaGFudElkIjoiZGJlMzk4MzFkMmJmNDM3MWFmNzU0MmFlMjhkMjgxNzMiLCJ1c2VySWQiOiJzeXN0ZW0iLCJpYXQiOjE1MTU3MzIyMzMsImlzcyI6Imp1ZmFuLmNvbSIsIm9yaWdpbiI6ImFwaSJ9.8fm5jNseYToan25N6CSAmCmNJ_xA-hIXozZu9QNGmcE";

        //聚泛RSA公钥，用于验证聚泛服务器返回报文的签名（此处是测试公钥，如果是生产环境，请申请获取生产公钥）
        String jfRSAPublicKey = "";


//        //咖啡金科
//        String redundantStr = "1qazxsw23edcvfr45tgb";
//        //商户API的TOKEN，需要配置为聚泛分配给商户的APIToken
//        //咖啡金科
//        String merchantApiToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXN0ZW0iLCJqdGkiOiIyOTNmNjM4MS02ZDE4LTQ1OWMtYTJiOS0yZjU4ZmU1NjgxOTYiLCJtZXJjaGFudElkIjoiZTY0M2FjMGUwYjg0NDYwMDgwMjUwODkyMTNjODZjZjQiLCJ1c2VySWQiOiJzeXN0ZW0iLCJpYXQiOjE1MTQzNDM0NTksImlzcyI6Imp1ZmFuLmNvbSIsIm9yaWdpbiI6ImFwaSJ9.gG1RhjU1qANBvN4Cc0MX13fm4bYIwuZS9OeJKabVUMo";


        //查询URL，由聚泛分配
//        String queryURL = "http://app6.jufandev.com:8080/ParticleDataOpenPlatform/product/query.action";
        String queryURL = "http://pdop.juxiangfen.com/ParticleDataOpenPlatform/product/query.action";
        ////////////////////////////////////////////////////////////////以下开始构造测试数据///////////////////////////////////////////////////////////////////


        Map<String, String> paramMap = new HashMap<>();


//        paramMap.put("pname", "沈家玉");    //姓名
//        paramMap.put("pdocument_no", "340104198911105029");   //证件号码
//        paramMap.put("busiDesc", "1111");  //用户手机号
//        paramMap.put("idType", "1");  //用户手机号

        paramMap.put("pname", "韩寅钢");
        paramMap.put("pdocument_no", "339005199812148519");
//        paramMap.put("queryReasonID", "101");
        paramMap.put("paccount_no", "6222020200053771891");
        paramMap.put("pphone", "18268196904");
//        paramMap.put("pname", "李小刚");//姓名
//        paramMap.put("pdocument_no", "341221198805014433");//身份证号
//        paramMap.put("busiDesc", "小象优品");   //身份证号
//        paramMap.put("pmonths", "2");  //手机号码
//        paramMap.put("pphone", "13686287438");//手机号码
//        paramMap.put("paccount_no", "6217000010015122693");//银行卡号
//        paramMap.put("index", "S0060,S0687");//银行卡号
//        paramMap.put("keyNo", "497914952c03046492b9098c60ad2f45");
//        paramMap.put("pbizname", "上海聚泛信息科技有限公司");
//        paramMap.put("type", "ktgg");
//        paramMap.put("pcertNo", "上海聚泛信息科技有限公司");
//        paramMap.put("pvalue", "");
//        paramMap.put("ptype", "1");
//        paramMap.put("pdocument_no", "532101198112070921");
//        paramMap.put("pphone", "13759588397");
//        paramMap.put("reasonNo", "01");
//        paramMap.put("pname", "樊红红");
        paramMap.put("codeNumber", "P1075");//产品编号
//        paramMap.put("pname", "黄伟杰");//姓名
//        paramMap.put("pdocument_no", "350583199201061838");//身份证号
//        paramMap.put("pphone", "15859585929");//手机号码
//        paramMap.put("paccount_no", "6217002980102825598");//银行卡号



/*        paramMap.put("pname", "谭天晨");    //姓名
        paramMap.put("idType", "1");    //证件类型
        paramMap.put("pdocument_no", "230103198709084832");   //证件号码
        paramMap.put("pphone", "15776677440");  //用户手机号
        paramMap.put("home_city","黑龙江省|哈尔滨市|南岗区");   //家庭所在城市
        paramMap.put("home_address", "81号1单元谭天晨502");  //家庭地址
        paramMap.put("company_city","黑龙江省|哈尔滨市|南岗区"); // 公司所在城市
        paramMap.put("company_address","黑龙江省哈尔滨市南岗区松花江街139号锦秀科技大厦1-6层");  //公司地址
        paramMap.put("bus_shop_city","");  //门店所在城市
        paramMap.put("bus_shop_address","");  //业务门店地址
        paramMap.put("recevier_city","");  //收货地址城市
        paramMap.put("recevier_address","");  //收货地址*/







        //设置签名方法，目前只支持RSA方式和MD5方式
        String signType = SignBundle.SIGN_TYPE_MD5;
        paramMap.put("signType", signType);

        switch (signType.toUpperCase()) {
            //RSA签名方式
            case SignBundle.SIGN_TYPE_RSA:
                paramMap.put("signature", SignBundle.signWithRSA(paramMap, merchantRSAPrivateKey, SignBundle.CHARSET_UTF_8));
                break;
            //MD5签名方式
            case SignBundle.SIGN_TYPE_MD5:
                paramMap.put("signature", SignBundle.signWithMD5(paramMap, redundantStr));

        }

        ////////////////////////////////////////////////////////////////以下开始发送请求//////////////////////////////////////////////////////////////////////
        Header[] headers = {new BasicHeader("X-AUTH-TOKEN", merchantApiToken), new BasicHeader("Content-type", "application/json")};
        String respJson = HttpClientUtil.sendPostMessage(queryURL, JSON.toJSONString(paramMap), headers);

//        JSONObject data = (JSONObject)JSONObject.parseObject(respJson).get("data");
//        JSONObject resp = (JSONObject)data.get("resp");
//        JSONObject data1 = (JSONObject)resp.get("data");
//        Object message = data1.get("message").toString();
//        System.out.println(message);

        System.out.println("<======================响应报文部分======================>");
        System.out.println(respJson);
        System.out.println("<======================响应报文结束======================>");
//
//        System.out.println();
//        System.out.println();
//        ////////////////////////////////////////////////////////////////以下开始验证返回报文//////////////////////////////////////////////////////////////////////
//        JSONObject respJsonObject = JSONObject.parseObject(respJson);
//        Map<String, Object> data = JSON.parseObject(respJsonObject.getString("data"), new TypeReference<Map<String, Object>>() {
//        }, Feature.AllowSingleQuotes);
//
//        String result_signature = (String) data.get("signature");
//        String result_signType = (String) data.get("signType");
//
//        System.out.println("<======================验签开始======================>");
//        System.out.println("服务端签名:" + result_signature);
//        Map<String, String> paramsFromServer = SignBundle.covertMap(data);
//        String preStr = SignBundle.createLinkString(paramsFromServer);
//        System.out.println("待验证串:" + preStr);
//
//        boolean verify = false;
//        switch (result_signType.toUpperCase()) {
//            //验证RSA
//            case SignBundle.SIGN_TYPE_RSA:
//                verify = SignBundle.verifyRSASign(paramsFromServer, result_signature, jfRSAPublicKey, SignBundle.CHARSET_UTF_8);
//                break;
//            //验证MD5签名
//            case SignBundle.SIGN_TYPE_MD5:
//                verify = SignBundle.verifyMD5Sign(SignBundle.covertMap(data), result_signature, redundantStr);
//        }
//        System.out.println("验证结果:" + (verify ? "成功" : "失败"));
//        System.out.println("<======================验签结束======================>");


    }




}

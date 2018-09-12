package com.pengyd.util.HttpsQingQiu;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author wangwenli
 * 自定义httpclient工具类，跳过ssl验证访问rest请求
 */
public class MyHttpClient {
    private static Logger log = LoggerFactory.getLogger("CATALINA");

    /**
     */
    public static String doPost(String urlstr, Map<String, String> map) {

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String responseStr = null;
        try {
            httpClient = (HttpClient) new SSLClient();
            httpPost = new HttpPost(urlstr);
//            if(authorization!=null&&!"".equals(authorization))
//            httpPost.addHeader("Authorization", authorization);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            //设置参数
            if (map != null && map.size() > 0) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                    list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
                }
                if (list.size() > 0) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                    httpPost.setEntity(entity);
                }
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    responseStr = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return responseStr;
    }

    /**
     * @return
     */
    public static String doGet(String urlstr, Map<String, String> map) {
        log.info("before:" + urlstr);
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String responseStr = null;
        StringBuffer sbf = new StringBuffer();
        if (map != null && map.size() > 0) {
            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = map.get(key);
                sbf.append(key + "=" + value + "&");
            }
        }
        if (sbf.length() > 0 && urlstr.indexOf("?") == -1) {
            urlstr += "?" + sbf.substring(0, sbf.length() - 1);
        } else if (sbf.length() > 0 && urlstr.indexOf("?") > -1) {
            urlstr += sbf.substring(0, sbf.length() - 1);
        }
        log.info("after:" + urlstr);
        try {
            httpGet = new HttpGet(urlstr);
            httpClient = (HttpClient) new SSLClient();
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    responseStr = EntityUtils.toString(resEntity, "UTF-8");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return responseStr;
    }

    /**
     * 发送post.，跳过ssl认证
     *
     * @param urlStr        地址
     * @param params        请求参数
     * @param charset       编码
     * @param authorization tokenID
     * @return 调用结果信息
     * @throws Exception
     */

    public static String doPostJson(String urlStr, Map params) {
        log.info("url:" + urlStr);
        CloseableHttpClient httpClient = null;
        String responseStr = null;
        try {
            httpClient = (CloseableHttpClient) new SSLClient();
            //if(httpClient==null)httpClient = HttpClients.createDefault();
            HttpPost request = new HttpPost(urlStr);
//			if(authorization!=null&&!"".equals(authorization))
//				request.addHeader("Authorization", authorization);
            request.setHeader(HttpHeaders.REFERER, "referer");
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
            request.setEntity(new StringEntity(new Gson().toJson(params), "UTF-8"));
            CloseableHttpResponse execute = httpClient.execute(request);
            responseStr = EntityUtils.toString(execute.getEntity());
            log.info("response is:" + responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseStr;

    }


}

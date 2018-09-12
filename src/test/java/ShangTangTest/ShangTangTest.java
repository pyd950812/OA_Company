package ShangTangTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Author pengyd
 * @Date 2018/8/17 10:25
 * @function:
 */
public class ShangTangTest {

    private static String API_KEY = "ef1dbfab6b934f3896d3fc52efd049eb";
    private static String API_SECRET = "e9334cfd13d842c6bf5682381c62af2b";

    @Test
    public void test() throws Exception {

        Map<String, Object> map = Maps.newHashMap();
        map.put("auto_rotate", true);

        HttpPost httpPost = new HttpPost("https://v2-auth-api.visioncloudapi.com/identity/handhold_idcard_verification/stateless");
        httpPost.addHeader("Authorization", genAuthorization());

        HttpClient httpClient = HttpClientBuilder.create().build();
        List<NameValuePair> list = new ArrayList<>();

        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                list.add(new BasicNameValuePair(key, map.get(key).toString()));
            }
        }

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for (NameValuePair nameValuePair : list) {
            multipartEntityBuilder.addPart(nameValuePair.getName(),
                    new StringBody(nameValuePair.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
        }

//
//        FileBody fileBody = new FileBody(new File("G:\\手持身份证图片.png"));
//        multipartEntityBuilder.addPart("image_file", fileBody);

        InputStream inputStream = new FileInputStream(new File("G:\\手持身份证图片.png"));

        if (inputStream != null) {
            InputStreamBody inputStreamBody =
                    new InputStreamBody(inputStream, "image_file");
            multipartEntityBuilder.addPart("image_file", inputStreamBody);
        }

        HttpEntity entity = multipartEntityBuilder.build();
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity1 = response.getEntity();
            if (entity1 != null) {
                String value = EntityUtils.toString(entity1, "UTF-8");
                System.out.println(value);
            }

        }
    }

    public static String genAuthorization() {
        // 获得随机nonce，返回nonce
        UUID uuid = UUID.randomUUID();
        String nonce = uuid.toString().replace("-", "");

        // 将timestamp、nonce、API_KEY 这三个字符串进行升序排列（依据字符串首位字符的ASCII码)，并join成一个字符串，返回
        String timestamp = System.currentTimeMillis() + "";
        ArrayList<String> beforesort = new ArrayList<String>();
        beforesort.add(API_KEY);
        beforesort.add(System.currentTimeMillis() + "");
        beforesort.add(nonce);

        Collections.sort(beforesort, new SpellComparator());

        StringBuffer aftersort = new StringBuffer();
        for (int i = 0; i < beforesort.size(); i++) {
            aftersort.append(beforesort.get(i));
        }
        String join_str = aftersort.toString();

        //用API_SECRET对join_str做hamc-sha256签名，且以16进制编码，返回
        Key sk = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance(sk.getAlgorithm());
            mac.init(sk);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        final byte[] hmac = mac.doFinal(join_str.getBytes());//完成hamc-sha256签名
        StringBuilder sb = new StringBuilder(hmac.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : hmac) {
            formatter.format("%02x", b);
        }
        String signature = sb.toString();//完成16进制编码

        String authorization = "key=" + API_KEY
                + ",timestamp=" + timestamp
                + ",nonce=" + nonce
                + ",signature=" + signature;
        return authorization;
    }


//    /**
//     *  营业执照
//     */
//    @Override
//    public Map<String, Object> businessLicenseDiscern(MultipartFile license_image) {
//        int trueResponse = 200;
//        int successCode = 1000;
//        Map<String, Object> result = new HashMap<>(16);
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost(postUrl);
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        InputStream stream = null;
//        try {
//            stream = license_image.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        builder.addBinaryBody("license_image", stream, ContentType.MULTIPART_FORM_DATA, license_image.getOriginalFilename());
//
//        HttpEntity entity = builder.build();
//        post.setHeader("Authorization", generateSignature.signature());
//        post.setEntity(entity);
//        try {
//            HttpResponse response = client.execute(post);
//            if (response.getStatusLine().getStatusCode() == trueResponse) {
//                String resp = EntityUtils.toString(response.getEntity());
//                License license = JSON.parseObject(resp, License.class);
//                if (license.getCode() == successCode) {
//                    result.put("code", license.getData().getCode());
//                    result.put("data", license.getData().getDate());
//                    result.put("regi", license.getData().getRegi());
//                    result.put("time", license.getData().getTime());
//                }
//                return result;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//
//    }
}

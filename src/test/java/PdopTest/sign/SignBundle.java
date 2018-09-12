package PdopTest.sign;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * Created by Gala on 3/8/2017.<br>
 * 签名工具类
 */
public class SignBundle {
    /**
     * 签名方式 - RSA
     */
    public static final String SIGN_TYPE_RSA = "RSA";
    /**
     * 签名方式 - MD5
     */
    public static final String SIGN_TYPE_MD5 = "MD5";
    /**
     * 字符编码 - UTF-8
     */
    public static final String CHARSET_UTF_8 = "UTF-8";


    /**
     * RSA签名
     *
     * @param sMap         需要签名的MAP
     * @param privateKey   RSA私钥
     * @param inputCharset 签名编码
     * @return 签名结果
     */
    public static String signWithRSA(Map<String, String> sMap, String privateKey, String inputCharset) {
        String content = createLinkString(sMap);
        return RSA.sign(content, privateKey, inputCharset);
    }

    /**
     * RSA验签
     *
     * @param sMap         需要签名的MAP
     * @param sign         需要验证的签名
     * @param publicKey    RSA公钥
     * @param inputCharset 签名编码
     * @return 验签结果
     */
    public static boolean verifyRSASign(Map<String, String> sMap, String sign, String publicKey, String inputCharset) {
        String content = createLinkString(sMap);
        return RSA.verify(content, sign, publicKey, inputCharset);
    }

    /**
     * MD5签名，最后摘要大写
     *
     * @param sMap         需要签名的MAP
     * @param redundantStr 参与签名的冗余字符串
     * @return 签名结果
     */
    public static String signWithMD5(Map<String, String> sMap, String redundantStr) {
        return MD5.createSign(sMap, redundantStr);
    }

    /**
     * MD5签名
     *
     * @param originalString 原始字符串
     * @return MD5摘要签名结果
     */
    public static String signWithMD5(String originalString) {
        return MD5.MD5Encode(originalString);
    }

    /**
     * MD5验签
     *
     * @param sMap         需要签名的MAP
     * @param sign         需要验证的签名
     * @param redundantStr 参与签名的冗余字符串
     * @return 验签结果
     */
    public static boolean verifyMD5Sign(Map<String, String> sMap, String sign, String redundantStr) {
        String signature = MD5.createSign(sMap, redundantStr);
        return signature.equals(sign);
    }


    /**
     * 把参与签名的所有字段按A-Z顺序排序，按照“参数=参数值”的模式用“&”字符拼接成字符串,并过滤<b>空值</b>和<b>signature</b>字段
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (null == value || value.length() == 0)
                continue;
            if (key.equals("signature"))
                continue;

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr.append(key).append("=").append(value);
            } else {
                prestr.append(key).append("=").append(value).append("&");
            }


        }

        //考虑到可能出现排序后尾串出现多个空值或者多个需要过滤key值的情况
        if (prestr.toString().endsWith("&"))
            prestr = new StringBuilder(prestr.substring(0, prestr.length() - 1));

        return prestr.toString();
    }

    /**
     * 将Map&lt;String,Object&gt;转换为Map&lt;String,String&gt;，会把Object(非String类型的)直接做toJSONString操作
     *
     * @param params Map&lt;String,Object&gt;
     * @return Map&lt;String,String&gt;
     * @see com.alibaba.fastjson.JSON#toJSONString(Object)
     */
    public static Map<String, String> covertMap(Map<String, Object> params) {
        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, Object> next : params.entrySet()) {
            String key = next.getKey();
            Object value = next.getValue();
            if (value != null) {
                if (value instanceof String)
                    map.put(key, (String) value);
                else
                    map.put(key, JSON.toJSONString(value));
            }
        }
        return map;
    }





}

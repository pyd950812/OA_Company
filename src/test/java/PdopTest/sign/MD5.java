package PdopTest.sign;

import java.security.MessageDigest;
import java.util.Map;


/* *
 * MD5加密工具
 * 
 */
class MD5 {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }


    /**
     * 把参与签名的所有字段按A-Z顺序排序，按照“参数=参数值”的模式用“&”字符拼接成字符串,并过滤<b>空值</b>和<b>signature</b>字段
     */
    @SuppressWarnings("rawtypes")
    static String createSign(Map<String, String> packageParams, String redundantStr) {
        packageParams.put("key", redundantStr);
        String sPar = SignBundle.createLinkString(packageParams);
//        System.out.println("md5 original:" + sPar);
        return MD5.MD5Encode(sPar);

    }
}

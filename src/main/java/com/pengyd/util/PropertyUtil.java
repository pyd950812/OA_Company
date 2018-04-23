package com.pengyd.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Properties;


public class PropertyUtil {
    public static String getValue(String key, String filename) {
        Properties p = new Properties();
        String value = "";
        InputStream in = null;
        try {
            Class Class1 = PropertyUtil.class;
            in = Class1.getResourceAsStream("/" + filename);
            p.load(in);
            value = p.getProperty(key);
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException e2) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static String MD5(String input) throws Exception {
        if (StringIsNull(input)) {
            return "";
        }
        byte[] buf = input.getBytes("utf-8");
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(buf);
        byte[] md = m.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md.length; i++) {
            int val = md[i] & 0xFF;
            if (val < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    public static boolean StringIsNull(String str) {
        if ((str == null) || ("".equals(str.trim()))) {
            return true;
        }
        return false;
    }
}
/**
 * 
 */
package PdopTest;

import java.util.UUID;

/**
 * @author Administrator
 * 
 */
public class StringUtil {

  public static String getUuid(){
    UUID uuid = UUID.randomUUID();
    String str = uuid.toString();   
    return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);   
  }
  
  public static String getString(Object obj){
    return obj==null?"":obj.toString();
  }
  
  public static String getStringTrim(Object obj){
	  return obj==null?"":obj.toString().trim();
  }
  /**
   * 判断是否为空 true 为空，false不为空
   */
  public static boolean isEmpty(String obj){
	  return obj==null||obj.trim().length()==0;
  }
  
  /**
   * 判断是否不为空， true不为空，false 为空
   */
  public static boolean isNotEmpty(String obj){
	  return obj!=null && obj.trim().length()>0;
  }

}

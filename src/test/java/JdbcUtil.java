import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtil {
    public static void main(String[] args) {

        Connection connection = getConnection();
    }

    public static Connection getConnection() {
        Connection con = null;

        String driverName = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/mytest?autoReconnect=true&amp;autoReconnectForPools=true&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull&amp;useSSL=false&amp;serverTimezone=Etc/GMT-8";
        String userName = "root";
        String userPwd = "";
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println(con);
        } catch (Exception e) {
            System.out.println("获取连接失败." + e.getMessage());
        }
        return con;
    }


}
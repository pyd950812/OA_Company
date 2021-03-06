import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Author pengyd
 * @Date 2018/1/3 14:23
 * @function:
 */
public class test extends AuthorizingRealm{

    public static void main(String[] args) {
        System.out.println(printHeart("*"));

    }
    private static String printHeart(String input){

        int[] array = {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 4, 5, 2, 3, 4, 1, 0, 1,0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {

            if(i % 7 == 0)

                sb.append("\n");

            if(array[i] == 0)

                sb.append("   ");

            else if(array[i] == 4)

                sb.append("  ");

            else if(array[i] == 5)

                sb.append(" I ");

            else if(array[i] == 2)

                sb.append("Lvoe ");

            else if(array[i] == 3)

                sb.append("You");

            else

                sb.append("  "+input);

        }

        return sb.toString();

    }

    public String print(String input){

        int[] array = {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 4, 5, 2, 3, 4, 1, 0, 1,0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {

            if(i % 7 == 0)

                sb.append("\n");

            if(array[i] == 0)

                sb.append("   ");

            else if(array[i] == 4)

                sb.append("  ");

            else if(array[i] == 5)

                sb.append(" I ");

            else if(array[i] == 2)

                sb.append("Lvoe ");

            else if(array[i] == 3)

                sb.append("You");

            else

                sb.append("  "+input);

        }

        return sb.toString();

    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}

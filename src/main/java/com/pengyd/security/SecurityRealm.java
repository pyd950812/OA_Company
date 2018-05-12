package com.pengyd.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.pengyd.bean.DeptPerm;
import com.pengyd.bean.Employee;
import com.pengyd.bean.Permission;
import com.pengyd.service.DeptPermService;
import com.pengyd.service.EmployeeService;
import com.pengyd.service.JobposService;
import com.pengyd.service.PermissionService;
import com.pengyd.util.ReturnData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;


/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function: shiro  一般自定义的realm都实现AuthorizingRealm 需要实现两个方法一个认证，一个授权
 */
@Component("securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    @Resource
    private EhCacheManager cacheManager;

    //@Resource
    //private HashedCredentialsMatcher credentialsMatcher;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private JobposService jobposService;

    @Resource
    private DeptPermService deptPermService;

    @Resource
    private PermissionService permissionService;

    /**
     *  登录校验   认证器  主体进行认证最终是通过authenticator来进行的
     *  shiro认证流程：（掌握）
     1、subject(主体)请求认证，调用subject.login(token)
     2、SecurityManager (安全管理器)执行认证
     3、SecurityManager通过ModularRealmAuthenticator进行认证。
     4、ModularRealmAuthenticator将token传给realm，realm根据token中用户信息从数据库查询用户信息（包括身份和凭证）
     5、realm如果查询不到用户给ModularRealmAuthenticator返回null，ModularRealmAuthenticator抛出异常（用户不存在）
     6、realm如果查询到用户给ModularRealmAuthenticator返回AuthenticationInfo(认证信息)
     7、ModularRealmAuthenticator拿着AuthenticationInfo(认证信息)去进行凭证（密码 ）比对。如果一致则认证通过，如果不致抛出异常（凭证错误）。
     */
    //登录subject.login()方法的时候，会来调用doGetAuthenticationInfo认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中取出用户信息
        String loginname = String.valueOf(token.getPrincipal());
        String password = new String((char[]) token.getCredentials());

        Employee employee = new Employee();
        employee.setLoginname(loginname);
        employee.setPassword(password);

        ReturnData rd = employeeService.selectByParam(null, employee);
        Employee currentEmp = null;
        if ("OK".equals(rd.getCode())) {
            List<Employee> emps = (List<Employee>) rd.getData().get("data");
            //员工只能有一个，不能重复，否则登录失败
            if (emps.size() == 1) {
                currentEmp = emps.get(0);
            }
        }

        if (currentEmp == null) {
            throw new AuthenticationException("用户名或密码错误.");
        }

        // WEB应用中一般是用web容器对session来进行管理，而shiro也提供一套session管理的方式 sessionManager
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("current_emp", currentEmp);

        //如果认证通过则返回认证信息authenticationInfo
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(currentEmp, currentEmp.getPassword(),
                getClass().getName());

        return authenticationInfo;
    }

    /**
     *    authorizer  授权器，主体进行授权最终通过authorizer进行的 基于资源的权限管理
     *
     *     Controller中的注解RequiresPermissions，
     *     就是在调用SecurityUtils.getSubject().isPermitted。shiro会调用doGetAuthorizationInfo这个方法
     *
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = String.valueOf(principals.getPrimaryPrincipal());

        //使用shiro cache名字随便取，有就拿，没有就新建
        Cache<String, SimpleAuthorizationInfo> userAuthori = this.cacheManager.getCache("MyAuthorizationInfo");

        //获取权限 名字随便取，有就拿，没有就新建
        SimpleAuthorizationInfo authorizationInfo = userAuthori.get(username + "_permissions");
        //如果缓存中有这个的时候直接从缓存中拿数据，否则进行查询
        if (authorizationInfo != null) {
            return authorizationInfo;
        }

        authorizationInfo = new SimpleAuthorizationInfo();
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //从shiro中的session中获取认证通过的那个员工对象信息
        Employee currentEmp = (Employee) subject.getSession().getAttribute("current_emp");
        //获取到员工的职位ID
        int jobposId = currentEmp.getJobposId();
        //通过员工的职位ID知道对应的部门ID
        int deptId = jobposService.selectDeptIdById(jobposId);
        //权限部门关联表中，通过部门ID找到对应的权限ID
        DeptPerm deptPerm = new DeptPerm();
        deptPerm.setDeptId(deptId);
        ReturnData rdDeptPerm = deptPermService.selectByParam(null, deptPerm);
        List<DeptPerm> deptPermList = (List<DeptPerm>) rdDeptPerm.getData().get("data");

        List<Integer> permIdList = new ArrayList<Integer>();
        //将所有的权限ID放到list中
        for (int i = 0; i < deptPermList.size(); i++) {
            permIdList.add(deptPermList.get(i).getPermId());
        }

        if (permIdList.size() > 0) {
            String permIdStr = permIdList.toString();
                permIdStr = permIdStr.substring(1, permIdStr.length() - 1);
            //找到该员工能操作的所有功能
            ReturnData rdPermission = permissionService.selectByPermIds(permIdStr);
            List<Permission> permList = (List<Permission>) rdPermission.getData().get("data");

            for (int i = 0; i < permList.size(); i++) {
                //获取权限名称
                String percode = permList.get(i).getPercode();

                //只有部门经理以上的级别的职位拥有任务分配的权限
                // 10  任务分配    1   jobs_manage_show    jobs_manage/show    6   1
                if ("jobs_manage_show".equals(percode)) {
                    //通过职位ID找到职位编码
                    String jobposCode = jobposService.selectCodeById(jobposId);
                    //根据职位编码的长度，来判断该用户是否具有任务分配的权限，如果小于六位，则有任务分配的权限
                    if (jobposCode.length() <= 6) {
                        //将授权信息填充到SimpleAuthorizationInfo对象中
                        authorizationInfo.addStringPermission(percode);
                    }
                }
                else {
                    authorizationInfo.addStringPermission(percode);
                }
            }
        }

        userAuthori.put(username + "_permissions", authorizationInfo);

        return authorizationInfo;
    }

    //清除缓存
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}

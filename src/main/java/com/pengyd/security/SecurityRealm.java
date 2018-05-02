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
 * @function: shiro
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

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginname = String.valueOf(token.getPrincipal());
        String password = new String((char[]) token.getCredentials());

        Employee employee = new Employee();
        employee.setLoginname(loginname);
        employee.setPassword(password);

        ReturnData rd = employeeService.selectByParam(null, employee);
        Employee currentEmp = null;
        if ("OK".equals(rd.getCode())) {
            List<Employee> emps = (List<Employee>) rd.getData().get("data");
            if (emps.size() == 1) {
                currentEmp = emps.get(0);
            }
        }

        if (currentEmp == null) {
            throw new AuthenticationException("用户名或密码错误.");
        }

        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("current_emp", currentEmp);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(currentEmp, currentEmp.getPassword(),
                getClass().getName());

        return authenticationInfo;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = String.valueOf(principals.getPrimaryPrincipal());

        //使用shiro cache
        Cache<String, SimpleAuthorizationInfo> userAuthori = this.cacheManager.getCache("MyAuthorizationInfo");

        //获取权限
        SimpleAuthorizationInfo authorizationInfo = userAuthori.get(username + "_permissions");

        //不为空的时候直接返回，否则进行查询
        if (authorizationInfo != null) {
            return authorizationInfo;
        }

        authorizationInfo = new SimpleAuthorizationInfo();

        //Subject subject = SecurityUtils.getSubject();
        //Map<?, ?> user = (Map<?, ?>) subject.getSession().getAttribute("AccountInfo");

        //根据账号ID查出账号对于的角色
        /*List<Map<?, ?>> permInfos = new ArrayList<Map<?, ?>>();
        Set<String> perms = new HashSet<String>();
        for (Map<?, ?> perm : permInfos) {
            perms.add(perm.get("MENUCODE").toString());
        }*/

        Subject subject = SecurityUtils.getSubject();

        Employee currentEmp = (Employee) subject.getSession().getAttribute("current_emp");

        int jobposId = currentEmp.getJobposId();

        int deptId = jobposService.selectDeptIdById(jobposId);

        DeptPerm deptPerm = new DeptPerm();
        deptPerm.setDeptId(deptId);
        ReturnData rdDeptPerm = deptPermService.selectByParam(null, deptPerm);
        List<DeptPerm> deptPermList = (List<DeptPerm>) rdDeptPerm.getData().get("data");

        List<Integer> permIdList = new ArrayList<Integer>();

        for (int i = 0; i < deptPermList.size(); i++) {
            permIdList.add(deptPermList.get(i).getPermId());
        }

        if (permIdList.size() > 0) {
            String permIdStr = permIdList.toString();
            permIdStr = permIdStr.substring(1, permIdStr.length() - 1);

            ReturnData rdPermission = permissionService.selectByPermIds(permIdStr);
            List<Permission> permList = (List<Permission>) rdPermission.getData().get("data");

            for (int i = 0; i < permList.size(); i++) {
                String percode = permList.get(i).getPercode();
                //System.out.println(percode);

                //只有部门经理以上的级别的职位拥有任务分配的权限 - 10  任务分配    1   jobs_manage_show    jobs_manage/show    6   1
                if ("jobs_manage_show".equals(percode)) {
                    String jobposCode = jobposService.selectCodeById(jobposId);
                    if (jobposCode.length() <= 6) {
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
}

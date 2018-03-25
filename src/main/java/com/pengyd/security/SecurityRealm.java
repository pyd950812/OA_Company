package com.pengyd.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.pengyd.bean.Employee;
import com.pengyd.service.EmployeeService;
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
 * @description: TODO - 
 * @author:  pengyd
 * @createTime: 2018年3月9日 上午11:34:27
 *
 */
@Component("securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    @Resource
    private EhCacheManager cacheManager;

    @Resource
    private EmployeeService employeeService;

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
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(currentEmp, currentEmp.getPassword(),
                getClass().getName());

        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("current_emp", currentEmp);
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
        List<Map<?, ?>> permInfos = new ArrayList<Map<?, ?>>();
        Set<String> perms = new HashSet<String>();
        for (Map<?, ?> perm : permInfos) {
            perms.add(perm.get("MENUCODE").toString());
        }
        authorizationInfo.addStringPermissions(perms);
        userAuthori.put(username + "_permissions", authorizationInfo);
        return authorizationInfo;
    }
}

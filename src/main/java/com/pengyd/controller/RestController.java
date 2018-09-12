package com.pengyd.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pengyd.bean.Employee;
import com.pengyd.security.SecurityRealm;
import com.pengyd.service.EmployeeService;
import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:
 */
@Controller
@RequestMapping({ "/rest" })
public class RestController {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SecurityRealm securityRealm;


    /**
     *  项目刚启动时，shiro中配置了，首先会进入到这里进行校验
     *  判断是否认证通过，没有就跳到登录界面
     */
    @RequestMapping({ "/login" })
    public String login(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()) {
            return "redirect:/rest/main";
        }
        return "rest/login";
    }

    @RequestMapping({ "/404" })
    public String error404() {
        return "rest/404";
    }

    @RequestMapping({ "/401" })
    public String error401() {
        return "rest/401";
    }

    @RequestMapping({ "/500" })
    public String error500() {
        return "rest/500";
    }

    @RequestMapping({ "/main" })
    public String main() {
        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return "rest/login";
        }
        return "index";
    }

    @RequestMapping({ "/povertyAnalysis" })
    public String povertyAnalysis() {
        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return "rest/login";
        }
        return "/povertyAnalysis";
    }

    @RequestMapping({ "/welcome" })
    public String welcome() {
        return "rest/welcome";
    }

    @RequestMapping(value = { "/loginAction" }, method = RequestMethod.POST )
    public String loginAction(Model model, HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Subject subject = SecurityUtils.getSubject();

            subject.login(new UsernamePasswordToken(username, password));

            return "redirect:/rest/main";
        }
        catch (AuthenticationException e) {
            model.addAttribute("errormessage", e.getMessage());
        }
        return "/rest/login";
    }

    @RequestMapping({ "/loginByAjax" })
    @ResponseBody
    public ReturnData loginByAjax(HttpServletRequest request,HttpSession session) {
        String username = request.getParameter("loginname");
        String password = request.getParameter("password");
//        redisTemplate.opsForValue().set("pdop:P1082","test");
        ReturnData rd = new ReturnData();
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();



            //判断是否认证通过
            if (subject.isAuthenticated()) {
                rd.setCode("OK");
                rd.setMsg("登录成功");
            }
            //执行认证提交    token 在认证提交前准备token(令牌)
            subject.login(new UsernamePasswordToken(username, password));

            Employee employee = employeeService.selectByLoginName(username);
            session.setAttribute("name",username);
            session.setAttribute("employee",employee);

            rd.setCode("OK");
            rd.setMsg("登录成功");
        }
        catch (AuthenticationException e) {
            this.logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    /**
     *  登出操作
     */
    @RequestMapping(value = { "/logoutAction" }, method = RequestMethod.GET )
    public String logoutAction(HttpSession session) {
        session.removeAttribute("userInfo");
        //清除缓存
//        securityRealm.clearCached();

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "rest/login";
    }
}

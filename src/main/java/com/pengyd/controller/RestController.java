package com.pengyd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @description: TODO - 登录
 * @author: pengyd
 * @createTime: 2018年3月9日 上午11:32:10
 *
 */
@Controller
@RequestMapping({ "/rest" })
public class RestController {
    private Logger logger = Logger.getLogger(getClass().getName());

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

        ReturnData rd = new ReturnData();
        try {
            Subject subject = SecurityUtils.getSubject();

            if (subject.isAuthenticated()) {
                rd.setCode("OK");
                rd.setMsg("登录成功");
            }

            subject.login(new UsernamePasswordToken(username, password));

            session.setAttribute("name",username);

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

    @RequestMapping(value = { "/logoutAction" }, method = RequestMethod.GET )
    public String logoutAction(HttpSession session) {
        session.removeAttribute("userInfo");

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "rest/login";
    }
}

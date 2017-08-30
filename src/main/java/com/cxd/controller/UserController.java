package com.cxd.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cxd.pojo.User;
import com.cxd.service.UserService;
import com.cxd.service.impl.UserOperationsServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/night")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private UserOperationsServiceImpl userOperationsService;//redis操作类
    @Autowired
    private UserService userService;//mysql数据库操作类
    private User user;
    private String sessionkey="";

    @RequestMapping(value = "/login.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object getUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("sessionkey")) {
                    try {
                        sessionkey = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }
            if (sessionkey.equals("")) {
                user = new User();
                user.setSname(username);
                user.setSpassword(password);
                User temp = userService.login(user);
                if (temp != null) {
                    Cookie sessionkey = new Cookie("sessionkey", temp.getKey());
                    sessionkey.setPath("/");
                    sessionkey.setMaxAge(24 * 60 * 60);
                    response.addCookie(sessionkey);
                    userOperationsService.add(temp);
                    logger.info("--------mysql登陆成功！");
                    return "1";
                } else {
                    logger.error("--------mysql登陆失败！");
                    return "0";
                }
            } else {
                User redisTemp=userOperationsService.getUser(sessionkey);
                if(redisTemp!=null){
                    if (username.equals(redisTemp.getSname().trim())&&password.equals(redisTemp.getSpassword().trim())) {
                        logger.info("--------redis登陆成功！");
                        return "1";
                    }
                    else {
                        user = new User();
                        user.setSname(username);
                        user.setSpassword(password);
                        User temp = userService.login(user);
                        if (temp != null) {
                            Cookie sessionkey = new Cookie("sessionkey", temp.getKey());
                            sessionkey.setPath("/");
                            sessionkey.setMaxAge(24 * 60 * 60);
                            response.addCookie(sessionkey);
                            userOperationsService.add(temp);
                            logger.info("--------mysql登陆成功！");
                            return "1";
                        } else {
                            logger.error("--------mysql登陆失败！");
                            return "0";
                        }
                    }
                }
                else {
                    logger.error("--------redis登陆失败！");
                    return "0";
                }
            }
        } else {
            user = new User();
            user.setSname(username);
            user.setSpassword(password);
            User temp = userService.login(user);
            if (temp != null) {
                Cookie sessionkey = new Cookie("sessionkey", temp.getKey());
                sessionkey.setPath("/");
                sessionkey.setMaxAge(24 * 60 * 60);
                response.addCookie(sessionkey);
                userOperationsService.add(temp);
                logger.info("--------mysql登陆成功！");
                return "1";
            } else {
                logger.error("--------mysql登陆失败！");
                return "0";
            }
        }

    }

    @RequestMapping(value = "/loginnew.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Object login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        User redisTemp = userOperationsService.getUser(username);
        if (redisTemp != null) {
            if (username.equals(redisTemp.getSname().trim()) && password.equals(redisTemp.getSpassword().trim())) {
                logger.info("--------redis登陆成功！");
                return "1";
            }
            else {
                user = new User();
                user.setSname(username);
                user.setSpassword(password);
                User temp = userService.login(user);
                if (temp != null) {
                    userOperationsService.add(temp);
                    logger.info("--------mysql登陆成功！");
                    return "1";
                } else {
                    logger.error("--------mysql登陆失败！");
                    return "0";
                }
            }
        }
        else {
            user = new User();
            user.setSname(username);
            user.setSpassword(password);
            User temp = userService.login(user);
            if (temp != null) {
                userOperationsService.add(temp);
                logger.info("--------mysql登陆成功！");
                return "1";
            } else {
                logger.error("--------mysql登陆失败！");
                return "0";
            }
        }
    }
}

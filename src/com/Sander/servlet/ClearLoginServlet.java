package com.Sander.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
     * @Description:  清楚登录状态，退出自动登录，对应my_page中的退出登录功能
     * @Param
     * @return
     * @Author Sander
     * @Date 14:12 2021/10/13
    */
@WebServlet("/ClearLoginServlet")
public class ClearLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private  final String LOGIN_VIEW="/jsp/login.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies!= null){
            for (Cookie cookie : cookies) {
                //处理get请求的中文乱码问题，找到和用户名相等的cookie
                if (URLDecoder.decode(cookie.getName(), "UTF-8").equals("uname"))//表示已经登录过了，直接跳转就好
                {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                //找到和密码相对应的cookie
                if (URLDecoder.decode(cookie.getName(), "UTF-8").equals("upwd"))
                {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                //找到auto值
                if (URLDecoder.decode(cookie.getName(), "UTF-8").equals("auto")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        //重定向
        response.sendRedirect(request.getContextPath()+LOGIN_VIEW);
    }
}

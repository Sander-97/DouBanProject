package com.Sander.servlet;

import com.Sander.bean.Msg;
import com.Sander.pojo.User;
import com.Sander.service.UserService;
import com.Sander.util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet( "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USERNAME_EMPTY = "用户名为空";
    private static final String PASSWORD_EMPTY = "密码为空";
    private static final String USERNAME_REPETITION = "用户名重复";
    private static final String REGISTER_SUCCESS = "注册成功";


    /**
     * 注册成功后跳转到登录页面
     */
    private static final String LOGIN_VIEW = "/jsp/login.jsp";
    private static final String FAILED_VIEW = "/jsp/register.jsp";

    /**
     * 调用service层的方法
     */

    UserService userService = new UserService();
    User user = new User();
    Msg msg = new Msg();

    /**
     * 用户注册
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //拿到request域中，表单中的用户名和密码
        String name = request.getParameter("uname");
        String pwd1 = request.getParameter("upwd");

        //密码用MD5加密
        String pwd = MD5Util.MD5Encode(pwd1, "utf-8");
        //调用service层的注册
        msg = userService.register(name, pwd);

        //对调用的结果分析

        if (USERNAME_EMPTY.equals(msg.getResult())) {
            request.setAttribute("errorMsg", "用户名为空");
            request.getRequestDispatcher(FAILED_VIEW).forward(request, response);
        } else if (PASSWORD_EMPTY.equals(msg.getResult())) {
            request.setAttribute("errorMsg", "密码为空");
            request.getRequestDispatcher(FAILED_VIEW).forward(request, response);
        } else if (USERNAME_REPETITION.equals(msg.getResult())) {
            request.setAttribute("errorMsg", "用户名重复");
            request.getRequestDispatcher(FAILED_VIEW).forward(request, response);
        } else if (REGISTER_SUCCESS.equals(msg.getResult())) {
            System.out.println("在registerServlet中注册成功");
            request.setAttribute("successMsg", "注册成功");
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
        }

    }
}

package com.Sander.servlet;

import com.Sander.pojo.User;
import com.Sander.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MY_PAGE_VIEW = "/jsp/my_page.jsp";
    UserService userService = new UserService();
    User user = new User();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        user = (User)session.getAttribute("userInfo");

        user.setNickName(request.getParameter("nickName"));
        user.setSelfIntroduction(request.getParameter("selfIntroduction"));
        user.setAddress(request.getParameter("address"));
        user.setSignature(request.getParameter("signature"));

        session.setAttribute("userInfo", user);
        //将要更新的数据放在user对象中，更新数据库
        boolean ret = userService.personage(user);
        //如果更新成功，就重定向到个人主页
        if (ret){
            request.getRequestDispatcher(MY_PAGE_VIEW).forward(request, response);
            return;
        }
    }
}

package com.Sander.servlet;

import com.Sander.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @Description:  找回密码,邮箱验证
 * @Param
 * @return 
 * @Author Sander
 * @Date 15:45 2021/10/13
*/
@WebServlet("/findBackPassword")
public class findBackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        User user = new User();
        user = (User)session.getAttribute("userInfo");

        int userId = 0;

        String userName = "";

        if (user!=null){
            userId = user.getUserId();
            userName = user.getUserName();
        }

        System.out.println(userId + userName);
    }
}

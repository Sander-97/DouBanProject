package com.Sander.servlet;

import com.Sander.bean.Msg;
import com.Sander.pojo.User;
import com.Sander.service.UserService;
import com.Sander.util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:  用于用户的登录
 * @Author Sander
 * @Date 10:09 2021/10/11
*/
@WebServlet( "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final Long serialVersionUId = 1L;
    static final String LOGIN_USER_SUCCESS = "用户登录成功";
    static final String LOGIN_ADMIN_SUCCESS = "管理员登录成功";
    static final String PASSWORD_WRONG = "密码错误";
    static final String USERNAME_INEXISTENCE = "用户名不存在";
    /**
     * 留在当前的登录页面
     */
    private final String LOGIN_VIEW = "/jsp/login.jsp";
    /**
     *  跳到个人主页界面
     */
    private final String SUCCESS_VIEW = "/jsp/alter.jsp";

    /**
     * 调用service方法
     */
    UserService userService = new UserService();
    User user = new User();
    Msg msg = new Msg();

/**
 * @Description: 相当于controller层的作用，负责调用service层的方法，并对返回的结果进行处理
 * @Param  [request, response]
 * @return void
 * @Author Sander
 * @Date 10:20 2021/10/11
*/
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取用户信息
         */
        String name = request.getParameter("uname");
        String password1 = request.getParameter("upwd");

        if (name == null || name.equals("")) {
            //保存错误的信息到request域中，然后转发到login.jsp 提醒登录
            request.setAttribute("errorMsg", "用户名为空");
            //转发到登录页面
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
            return;
        }

        String password = MD5Util.MD5Encode(password1, "utf8");
        /**
         * 获取请求参数
         * 拿到页面传过来的手动输入的验证码
         * 该验证码要和生成图片上的文本验证码比较
         * 如果相同，验证码输入成功
         */
        String imgText = request.getParameter("image");
        //图片的验证码
        String text = (String) request.getSession().getAttribute("text");

        if (!(text.equalsIgnoreCase(imgText))) {
            request.setAttribute("errorMsg", "验证码输入错误");
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
            return;
        }
        //访问数据库，把返回结果传送给msg
        msg = userService.login(name, password);
        //对不同的结果进行分析，并进行相对应的页面跳转处理
        if (LOGIN_USER_SUCCESS.equals(msg.getResult())) {

            //只有登陆成功才会出现在session域中，一次对话有效
            request.getSession().setAttribute("uname", name);
            request.getSession().setAttribute("upwd", password);
            //把查询到的所有信息userInfo传递给session
            User userInfo = new User();
            userInfo = userService.UserInfo(name);
            request.getSession().setAttribute("userInfo", userInfo);

            //记住密码放在cookie中
            if (request.getParameter("remember") != null) {

                //密码为未加密前的
                Cookie cookie1 = new Cookie("upwd", password1);
                //七天时长
                cookie1.setMaxAge(7 * 60 * 60 * 24);
                response.addCookie(cookie1);
            } else if (request.getParameter("remember") == null) {
                Cookie cookie1 = new Cookie("upwd", "");
                cookie1.setMaxAge(7 * 60 * 60 * 24);
                //放在响应中
                response.addCookie(cookie1);
            }
            //如果是选择的自动登录
            if (request.getParameter("auto") != null) {
                Cookie cookie2 = new Cookie("auto", "auto");
                cookie2.setMaxAge(7 * 60 * 60 * 24);
                response.addCookie(cookie2);
            }

            //用cookie实现回写用户名
            Cookie cookie = new Cookie("uname", name);

            cookie.setMaxAge(7 * 60 * 60 * 24);

            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + SUCCESS_VIEW);
            return;
        } else if (LOGIN_ADMIN_SUCCESS.equals(msg.getResult())) {
            //转发到success页面---管理员界面的跳转
            request.getRequestDispatcher(SUCCESS_VIEW).forward(request, response);
            return;
        }
        else if (PASSWORD_WRONG.equals(msg.getResult())){
            request.setAttribute("errorMsg","密码错误");
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
            return;
        }
        else if (USERNAME_INEXISTENCE.equals(msg.getResult())){
            request.setAttribute("errorMsg","该用户名不存在");
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
            return;
        }
        request.setAttribute("errorMsg", "无效操作，请重新登录");
        request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
        return;

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

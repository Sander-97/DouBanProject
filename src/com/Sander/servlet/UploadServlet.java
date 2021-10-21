package com.Sander.servlet;

import com.Sander.pojo.User;
import com.Sander.service.UserService;
import com.Sander.util.ImageUrl;

import javax.imageio.ImageIO;
import javax.naming.ldap.SortResponseControl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 头像的上传
 */
@MultipartConfig(location="/home/tomcat/DouBanProject")
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MY_PAGE_VIEW = "/jsp/alter.jsp";

    UserService userService = new UserService();
    User user = new User();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //photo对应上传图片对应的name属性----key值
        Part part = request.getPart("photo");
        //放置两个字符串，键--文件名，值--图片上传后的地址
        Map<String,String> map = new HashMap<>();

        //清空map集合
        map.clear();
        //原来的文件名可传入数据库
        String fileName = getFileName(part);

        //调用util包中拼接成绝对地址的方法
        map = ImageUrl.imgUrl(fileName);


        //键--先拿到一个装有所有键的集合
        Set<String> lastFileNameAll = map.keySet();

        String lastFileName = null;

        for (String string : lastFileNameAll) {
            lastFileName = string;
        }

        Collection<String> imageUrlAll = map.values();

        String imageUrl = null;

        for (String str : imageUrlAll) {
            imageUrl = str;
        }

        System.out.println("开始写入磁盘");
        //写入磁盘中
        String method = request.getParameter("method");

        if (method == null || "".equals(method)){

            HttpSession session = request.getSession();
            User userInfo = new User();
            //获得sess中的userInfo对象
            userInfo = (User)session.getAttribute("userInfo");

            user.setPortrait(imageUrl);

            System.out.println("upload----imgUrl---" + user.getPortrait());

            user.setUserName((String)session.getAttribute("uname"));

            session.setAttribute("user",user);

            session.setAttribute("portrait", imageUrl);

            userInfo.setPortrait(user.getPortrait());

            session.setAttribute("userInfo", userInfo);

            userService.portrait(userInfo);

            part.write(lastFileName);

            System.out.println("图片转发到My_page页面");

            request.getRequestDispatcher(MY_PAGE_VIEW).forward(request, response);
            return;
        }
        else {
            //写入磁盘中
            part.write(lastFileName);

            System.out.println("图片属于拿回地址的操作----异步处理");
            PrintWriter writer = response.getWriter();
            writer.write(imageUrl);
            writer.flush();
        }


    }

    /**
     *   头像上传
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 取得上传的文件名
     * @param part filename（文件名）
     * @return
     */
    private String getFileName(Part part){
        String header = part.getHeader("Content-Disposition");
        String fileName = header.substring(header.indexOf("fileName=\"") + 10,header.lastIndexOf("\""));
        return fileName;
    }
}

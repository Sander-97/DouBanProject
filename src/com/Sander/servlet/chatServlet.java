package com.Sander.servlet;

import com.Sander.dao.UserDao;
import com.Sander.daoImpl.UserDaoImpl;
import com.Sander.pojo.User;
import com.Sander.pojo.chat;
import com.Sander.pojo.page;
import com.Sander.service.FriendOperationService;
import com.Sander.service.chatService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description:  用于聊天的数据处理和页面跳转
 * @Param
 * @return
 * @Author Sander
 * @Date 14:27 2021/10/13
*/
@WebServlet( "/chatServlet")
public class chatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CHAT_PAGE = "/jsp/chat.jsp";
    private static final String CHAT_SHOW_PAGE= "/jsp/chat_show.jsp";
    /**
     * h黑名单页面，提醒用户不可以进行聊天操作
     */
    private static final String BLACKLIST_PAGE = "/jsp/blackList.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FriendOperationService friendOperationService = new FriendOperationService();
        chatService chatService = new chatService();
        UserDao userDao = new UserDaoImpl();
        //设为中文编码
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = new User();
        user = (User)request.getAttribute("userInfo");

        int userId = 0;
        int currentPage = 0;
        int pageSize = 10;
        int toUserId = 0;

        page page = new page();
        String currentPageStr = request.getParameter("currentPage");
        String toUserIdStr = request.getParameter("to_user_id");

        if (user!=null){
            userId = user.getUserId();
        }

        if (currentPageStr==null||"".equals(currentPageStr))
            currentPage = 1;
        else {
            currentPage = Integer.parseInt(currentPageStr);
        }

        if (toUserIdStr!=null&&!"".equals(toUserIdStr)){
            toUserId = Integer.parseInt(toUserIdStr);
            request.setAttribute("toUserId", toUserId);
            session.setAttribute("nickname", userDao.userInfoByUserId(userId).getNickName());
        }
        //对双方的好友关系进行判断
        int status1 = friendOperationService.friendQuery(userId, toUserId);
        int status2 = friendOperationService.friendQuery(toUserId, userId);
        //被拉黑的情况
        if (status1==3 || status2==3){
            request.setAttribute("msg","黑名单限制");
            request.getRequestDispatcher(BLACKLIST_PAGE).forward(request, response);
            return;
        }

        String preMethod = request.getParameter("pre_method");
        if (preMethod!=null){

            request.setAttribute("pre_method", preMethod);

            if (preMethod.equals("chat_content")){
                String content = request.getParameter("content");
                if (content != null && !"".equals(content)){

                    request.setAttribute("content",content);

                    String chatMsg = request.getParameter("content");


                    chat chat = new chat();

                    chat.setFromUserId(userId);
                    chat.setToUserId(toUserId);
                    chat.setChatMessage(chatMsg);

                    if (chatService.chatInsert(chat)){
                        System.out.println("聊天信息增加成功");
                    }
                }

            }


            String method = request.getParameter("method");
            request.setAttribute("method", method);

            switch (method){
                case "my_chat_list":
                {
                    int totalCount = chatService.getTotalCount(userId);

                    page.setTotalCount(totalCount);
                    page.setCurrentPage(currentPage);
                    page.setPageSize(pageSize);


                    List<chat> chats = new ArrayList<>();

                    chats = chatService.queryChatByPage(currentPage, pageSize, userId);

                    page.setObjects(chats);

                    request.setAttribute("p",page);
                    request.setAttribute("msg", "我的聊天");

                    request.getRequestDispatcher(CHAT_PAGE).forward(request, response);
                    return;

                }
                case "chat_show":
                {
                    request.getRequestDispatcher(CHAT_SHOW_PAGE).forward(request, response);
                    return;
                }
                case "chat_show_details":
                {
                    //总数据数
                    int totalCount = chatService.getShowCount(userId,toUserId);
                    //数据封装到page对象
                    page.setTotalCount(totalCount);
                    page.setCurrentPage(currentPage);
                    page.setPageSize(pageSize);

                    List<chat> chat = new ArrayList<>();

                    page.setObjects(chat);

                    chat = chatService.queryShowByPage(currentPage, pageSize, userId, toUserId);

                    page.setObjects(chat);

                    JSONObject json = new JSONObject(page);

                    String strJson = json.toString();

                    System.out.println("聊天对话的展示 + strJson" + strJson);

                    response.setContentType("text/html;charset=UTF-8");

                    PrintWriter writer = response.getWriter();

                    writer.write(strJson);

                    writer.flush();

                    break;

                }

                default:break;
            }
        }

    }
}

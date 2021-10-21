package com.Sander.servlet;

import com.Sander.pojo.User;
import com.Sander.pojo.page;
import com.Sander.service.EveryoneService;
import com.Sander.service.FriendOperationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListDataEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/***
 * @Description: 查询所有人的信息，得到列表
 * @Param
 * @return
 * @Author Sander
 * @Date 15:12 2021/10/13
*/
@WebServlet("/EveryoneListServlet")
public class EveryoneListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String EVERYONE_VIEW = "/jsp/everyone.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EveryoneService everyoneService = new EveryoneService();

        FriendOperationService friendOperationService = new FriendOperationService();

        request.setCharacterEncoding("UTF-8");

        int status = -1;
        int currentPage = 0;
        int pageSize = 10;
        int userId = 0;
        int toUserId = 0;
        page page = new page();

        String currentPageStr = request.getParameter("currentPage");
        String toUserIdStr = request.getParameter("toUserId");

        HttpSession session = request.getSession();

        if (currentPageStr!=null&&!"".equals(currentPageStr)){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        User user = new User();

        if (user!=null)
            userId = user.getUserId();

        if (toUserIdStr!=null&&!"".equals(toUserIdStr)){
            toUserId = Integer.parseInt(toUserIdStr);
        }

        String method = request.getParameter("method");
        request.setAttribute("method", method);

        String preMethod = request.getParameter("pre_method");

        if (preMethod!=null){
            request.setAttribute("pre_method", preMethod);

            switch (preMethod){
                case "attention":
                {
                    friendOperationService.friendInsert(userId, toUserId, "无分组");
                }
                break;
                case "unfollow":
                {
                    friendOperationService.friendDelete(userId, toUserId);
                }
                break;
                case "black_on_record":
                {
                    status = 3;
                    friendOperationService.friendUpdate(userId, toUserId, status);
                }
                case "black_without_record":
                {
                    status = 3;
                    friendOperationService.friendInsert(userId, toUserId, "无分组");
                    friendOperationService.friendUpdate(userId, toUserId, status);

                }
                break;
                case "cancel_black":
                {
                    status = 2;
                    friendOperationService.friendUpdate(userId, toUserId, status);
                }
                break;
                default:break;
            }

        }

       switch (method)
       {
           case "search_users":
           {
           String searchContent = request.getParameter("search_content");
           if (searchContent != null && !"".equals(searchContent)) {
               session.setAttribute("searchContent", searchContent);
           }

           String searchContents = (String) session.getAttribute("searchContent");

           int totalCount = everyoneService.getSearchCount(searchContents);

           page.setTotalCount(totalCount);
           page.setCurrentPage(currentPage);
           page.setPageSize(pageSize);

           List<User> users = new ArrayList<>();
           users = everyoneService.queryEveryoneByPageAndSearchContent(currentPage, pageSize, searchContents);

           for (User user1 :
                   users) {
               status = friendOperationService.friendQuery(userId, toUserId);
               user1.setStatus(status);
           }

           page.setObjects(users);
           request.setAttribute("p", page);

           request.getRequestDispatcher(EVERYONE_VIEW).forward(request, response);
           return;
           }

           case "everyone_list":
           {
               int totalCount = everyoneService.getTotalCount();

               page.setTotalCount(totalCount);
               page.setCurrentPage(currentPage);
               page.setPageSize(pageSize);


               List<User> users1 = new ArrayList<>();
               users1 = everyoneService.queryEveryoneByPage(currentPage, pageSize);

               List<User> users = new ArrayList<>();

               for (User user1 : users1) {
                   //设置关注和拉黑的两个按钮属性
                   status = friendOperationService.friendQuery(userId, user1.getUserId());

                   user1.setStatus(status);
                   users.add(user);
               }

               page.setObjects(users);

               request.setAttribute("p",page);
               //设置分组名，返回集合
               List<String> groupNames = new ArrayList<String>();

               request.setAttribute("groupNames", groupNames);

               request.getRequestDispatcher(EVERYONE_VIEW).forward(request, response);

               return;
           }


           default:break;
        }

    }
}

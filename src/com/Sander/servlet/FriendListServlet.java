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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示好友信息----我的关注、好友、黑名单
 */
@WebServlet("/FriendListServlet")
public class FriendListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * 我关注的人jsp界面
     */
    private static final String ATTENTION_VIEW = "/jsp/attention.jsp";
    /**
     * 我的好友jsp界面
     */
    private static final String FRIEND_VIEW = "/jsp/friend.jsp";
    /**
     * 黑名单jsp界面
     */
    private static final String BLACK_VIEW = "/jsp/blacklist.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置好友状态量为无状态
        int status = -1;

        EveryoneService everyoneService = new EveryoneService();

        FriendOperationService friendOperationService = new FriendOperationService();
        //改成中文编码
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        User user = new User();

        user = (User)session.getAttribute("userInfo");

        int userId = 0;
        if (user != null){
            userId = user.getUserId();
        }

        page page = new page();
        int currentPage = 0;
        int pageSize = 10;
        int toUserId = 0;
        String toUserIdStr = request.getParameter("toUserId");
        String currentPageStr = request.getParameter("currentPage");
        if (currentPageStr == null||"".equals(currentPage)){
            currentPage = 1;
        }
        else {
            currentPage = Integer.parseInt(currentPageStr);
        }

        if (toUserIdStr != null && !"".equals(toUserIdStr)){
            toUserId = Integer.parseInt(toUserIdStr);
        }

        String preMethod = request.getParameter("preMethod");

        if (preMethod!= null&&"".equals(preMethod)){
            request.setAttribute("preMethod", preMethod);

            switch (preMethod) {
                //删除好友
                case "remove_black": {

                    friendOperationService.friendDelete(userId, toUserId);
                }
                //在我关注的人中取消关注
                case "unfollow": {
                    friendOperationService.friendDelete(userId, toUserId);
                }
                //在我的关注中拉黑
                case "black_on_record":
                {
                    status = 3;
                    friendOperationService.friendUpdate(userId,toUserId,status);

                }
                default:break;
            }
        }

        String method = request.getParameter("method");

        request.setAttribute("method", method);

        switch (method){
            //关注的人
            case "attention_list":
            {
                status = 1;
                int totalCount = everyoneService.getTotalCount();

                page.setTotalCount(totalCount);
                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);

                List<User> users = new ArrayList<>();

                users = everyoneService.queryFriendByPage(userId, status, currentPage, pageSize);

                page.setObjects(users);

                request.setAttribute("p", page);

                request.getRequestDispatcher(ATTENTION_VIEW).forward(request, response);
                return;
            }
            //搜索框
            case "search_attention":
            {
                status = 1;
                String search_content = request.getParameter("search_content");

                if (search_content!=null&&"".equals(search_content)){
                    session.setAttribute("searchContent", search_content);
                }

                String searchContent = (String) session.getAttribute("searchContent");

                int totalCount = everyoneService.getFriendTotalCount(userId, status, searchContent);

                page.setTotalCount(totalCount);
                page.setPageSize(pageSize);
                page.setCurrentPage(currentPage);

                List<User> users = new ArrayList<User>();
                users = everyoneService.queryFriendByPageAndSearchContent(searchContent, userId, status, currentPage,pageSize );
                page.setObjects(users);
                request.setAttribute("p",page);
                request.getRequestDispatcher(ATTENTION_VIEW).forward(request, response);
                return;
            }
            //好友功能中的查询好友信息列表
            case "friend_list":
            {

                status = 2;
                int totalCount = everyoneService.getFriendTotalCount(userId, status);

                page.setTotalCount(totalCount);
                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);

                List<User> users = new ArrayList<>();
                users = everyoneService.queryFriendByPage(userId, status, currentPage, pageSize);
                page.setObjects(users);

                request.setAttribute("p", page);

                List<String> groupNames = new ArrayList<>();

                groupNames = friendOperationService.friendGroupNameQuery(userId);

                request.setAttribute("groupNames", groupNames);

                request.getRequestDispatcher(FRIEND_VIEW).forward(request, response);
                return;
            }
            //好友搜索功能
            case "search_friend":
            {
                status = 2;

                String search_content = request.getParameter("search_content");
                if (search_content!=null&&"".equals(search_content)){
                    session.setAttribute("searchContent", search_content);
                }

                String searchContent = (String) session.getAttribute("searchContent");

                int totalCount = everyoneService.getFriendTotalCount(userId, status, searchContent);

                page.setTotalCount(totalCount);
                page.setPageSize(pageSize);
                page.setCurrentPage(currentPage);

                List<User> users = new ArrayList<>();
                users = everyoneService.queryFriendByPageAndSearchContent(searchContent, userId, status, currentPage, pageSize);
                page.setObjects(users);

                request.setAttribute("p", page);
                request.getRequestDispatcher(FRIEND_VIEW).forward(request, response);
                return;
            }
            //好友分组搜索功能
            case "search_friend_group":
            {
                status = 2;
                String search_content = request.getParameter("search_content");
                if (search_content!=null&&"".equals(search_content)){
                    session.setAttribute("searchContent", search_content);
                }

                String searchContent = (String) session.getAttribute("searchContent");

                if (friendOperationService.friendGroupAlter(userId, toUserId, search_content)){
                    System.out.println("好友分组更改成功");
                }

                int totalCount = everyoneService.getFriendTotalCount(userId, status, searchContent);

                page.setTotalCount(totalCount);
                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);

                List<User> users = new ArrayList<>();
                users = everyoneService.queryFriendGroupByPage(searchContent, userId, status, currentPage, pageSize);
                page.setObjects(users);
                request.setAttribute("p", page);

                List<String> groupNames = new ArrayList<>();
                groupNames = friendOperationService.friendGroupNameQuery(userId);
                request.setAttribute("groupNames", groupNames);
                request.getRequestDispatcher(FRIEND_VIEW).forward(request, response);
                return;
            }
            //黑名单信息列表
            case "blacklist_list":
            {
                status = 3;
                int totalCount = everyoneService.getFriendTotalCount(userId, status);
                page.setTotalCount(totalCount);
                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);

                List<User> users = new ArrayList<>();
                users = everyoneService.queryFriendByPage(userId, status, currentPage, pageSize);
                page.setObjects(users);
                request.setAttribute("p", page);
                request.getRequestDispatcher(BLACK_VIEW).forward(request, response);
                return;
            }
            //查询黑名单信息
            case "search_blacklist":
            {
                status = 3;
                String search_content = request.getParameter("search_content");
                if (search_content!=null&&"".equals(search_content)){
                    session.setAttribute("searchContent", search_content);
                }
                String searchContent =(String)session.getAttribute("searchContent");

                int totalCount = everyoneService.getFriendTotalCount(userId, status, searchContent);
                page.setTotalCount(totalCount);
                page.setPageSize(pageSize);
                page.setCurrentPage(currentPage);

                List<User> users = new ArrayList<>();
                users = everyoneService.queryFriendByPageAndSearchContent(searchContent, userId, status, currentPage, pageSize);
                page.setObjects(users);

                request.setAttribute("p", page);
                request.getRequestDispatcher(BLACK_VIEW).forward(request,response);
                return;

            }
            default:break;
        }


    }
}

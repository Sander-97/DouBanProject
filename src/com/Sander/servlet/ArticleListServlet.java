package com.Sander.servlet;

import com.Sander.pojo.ArticleList;
import com.Sander.pojo.User;
import com.Sander.pojo.page;
import com.Sander.service.ArticleEditService;
import com.Sander.service.ArticleListService;

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
 * @Description: 
 * @Param  对文章的列表进行处理
 * @return
 * @Author Sander
 * @Date 15:10 2021/10/12
*/

@WebServlet("/ArticleListServlet")
public class ArticleListServlet extends HttpServlet {
    private static final Long serialVersionUID = 1L;

    static final String ARTICLE_LIST_VIEW = "/jsp/article_list.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * 分页模糊搜索，返回数据集合
     * @param request 请求
     * @param response 响应
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //调用所需的数据库操作方法
        ArticleListService articleListService = new ArticleListService();
        ArticleEditService articleEditService = new ArticleEditService();

        //得到session，拿到当前用户的id
        HttpSession session = request.getSession();
        User user = new User();
        user = (User) request.getAttribute("userInfo");
        int userId = 0;
        //设置当前用户的id值
        if (user != null){
            userId = user.getUserId();
        }
        //把分页所需字段封装到page对象中
        page page = new page();
        int currentPage = 1;
        //当前要显示的页面---超链接里面的请求数据
        String currentPageStr = request.getParameter("currentPage");

        //第一页--默认的页面
        if (currentPageStr == null || "".equals(currentPageStr)){
            currentPage = 1;
        }
        else {
            //当前页，用户选择的页面数
            currentPage = Integer.parseInt(currentPageStr);
        }
        //页面大小
        int pageSize = 10;

        //预处理的功能：删除文章
        //当前要显示的页面---用户的好友操作----超链接里面的请求所对应的实现方式
        String preMethod = request.getParameter("pre_method");
        if (preMethod!=null){
            request.setAttribute("pre_method", preMethod);
            String articleIdStr = request.getParameter("article_id");
            if (articleIdStr != null && "".equals(articleIdStr)){
                int articleId = Integer.parseInt(articleIdStr);
                if (preMethod.equals("delete_article")){
                    articleEditService.articleDelete(articleId);
                }
            }
        }
        //当前要显示的页面----超链接里面的请求所对应的实现方式
        String method = request.getParameter("method");
        //请求的方法
        request.setAttribute("method",method);
        switch (method){
            //所有文章列表
            case "article_list":
            {
                int totalCount = articleListService.getTotalCount();

                page.setTotalCount(totalCount);
                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);

                //拿到数据集合
                List<ArticleList> articleLists = new ArrayList<>();
                articleLists = articleListService.queryArticleListByPage(currentPage, pageSize);

                page.setObjects(articleLists);
                //把数据传给request
                request.setAttribute("p", page);
                request.setAttribute("msg", "所有文章");

                //跳转到article_list界面
                request.getRequestDispatcher(ARTICLE_LIST_VIEW).forward(request, response);
                return;
            }

            case "search_article_list":
            {
                //用户要搜索的内容
                String search_content = request.getParameter("search_content");
                if (!(search_content == null && "".equals(search_content))){
                    session.setAttribute("searchContent", search_content);
                }

                String searchContent = (String)session.getAttribute("searchContent");
                //获取符合搜素结果的数据数
                int totalCount = articleListService.getArticleSearchContent(searchContent);
                page.setTotalCount(totalCount);
                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);

                List<ArticleList> articleLists = new ArrayList<>();
                articleLists = articleListService.queryArticleSearchContentByPage(currentPage, pageSize, searchContent);
                page.setObjects(articleLists);
                //把数据传给request
                request.setAttribute("p",page);
                request.setAttribute("msg", "在所有文章中的搜索结果");
                //跳转到article_list.jsp页面
                request.getRequestDispatcher(ARTICLE_LIST_VIEW).forward(request, response);
                return;

            }

            case "my_article_list":
            {
                int totalCount = articleListService.getMyTotalCount(userId);
                page.setTotalCount(totalCount);
                page.setPageSize(pageSize);
                page.setCurrentPage(currentPage);

                List<ArticleList> articleLists = new ArrayList<>();
                articleLists = articleListService.queryMyArticleListByPage(currentPage, pageSize, userId);

                page.setObjects(articleLists);

                //把数据传输给request
                request.setAttribute("p", page);
                request.setAttribute("msg", "我的文章");

                //跳转到article_list.jsp页面
                request.getRequestDispatcher(ARTICLE_LIST_VIEW).forward(request, response);
                return;
            }

//            case "search_my_article_list":{
//                String searchContent1 = request.getParameter("search_content");
//                if (!(searchContent1 != null && "".equals(searchContent1))){
//                    session.setAttribute("searchContent", searchContent1);
//                }
//                String searchContent = (String) session.getAttribute("searchContent");
//                int totalCount = articleListService.getTotalCount();
//
//                page.setTotalCount(totalCount);
//                page.setPageSize(pageSize);
//                page.setCurrentPage(currentPage);
//
//                List<ArticleList> articleLists = new ArrayList<ArticleList>();
//                //模糊搜索的功能---DAO
//                articleLists = articleListService.queryMyArticleSearchContentByPage(currentPage, pageSize, searchContent, userId) ;
//
//                page.setObjects(articleLists);
//                //将数据传给request
//                request.setAttribute("p", page);
//                request.setAttribute("msg", "在我的文章中的搜索结果");
//                // 跳转到article_list.jsp的页面
//                request.getRequestDispatcher(ARTICLE_LIST_VIEW).forward(request, response);
//                return;
//            }

            case "my_collection_list":
            {
                int totalCount = articleListService.getTotalCount();

                page.setCurrentPage(currentPage);
                page.setPageSize(pageSize);
                page.setTotalCount(totalCount);

                List<ArticleList> articleLists = new ArrayList<>();
                articleLists = articleListService.queryMyCollectionByPage(currentPage, pageSize, userId) ;

                page.setObjects(articleLists);
                // 将数据传给request
                request.setAttribute("p", page);
                request.setAttribute("msg", "我的收藏");
                // 跳转到article_list.jsp的页面
                request.getRequestDispatcher(ARTICLE_LIST_VIEW).forward(request, response);
                return;
            }

            default:break;
        }


    }
}

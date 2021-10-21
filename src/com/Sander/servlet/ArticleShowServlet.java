package com.Sander.servlet;

import com.Sander.pojo.*;
import com.Sander.service.ArticleInteractionService;
import com.Sander.service.ArticleShowService;
import com.sun.mail.imap.protocol.INTERNALDATE;
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
 * @Description: 文章的展示--文章主体----评论区--回复
 * @Param
 * @return
 * @Author Sander
 * @Date 19:49 2021/10/12
*/
@WebServlet( "/ArticleShowServlet")
public class ArticleShowServlet extends HttpServlet {
    private static final Long serialVersionUID = 1L;
    private static final String ARTICLE_SHOW_VIEW = "/jsp/article_show.jsp";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleShowService articleShowService = new ArticleShowService();
        ArticleInteractionService articleInteractionService = new ArticleInteractionService();

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = new User();

        user = (User)request.getAttribute("userInfo");
        int userId = 0;

        if (user!=null){
            userId=user.getUserId();
        }
        page page = new page();

        int currentPage = 1;

        String currentPageStr = request.getParameter("currentPage");
        //如果没有获取到用户想搜索的页面就默认第一页
        if (currentPageStr == null ||"".equals(currentPageStr)){
            currentPage = 1;
        }else {
            currentPage = Integer.parseInt(currentPageStr);
        }
        //设置页面大小
        int pageSize = 10;
            int articleId = 0;
                //获取当前要显示的页面数据
            String articleIdStr = request.getParameter("article_id");

            if (!(articleIdStr == null ||"".equals(articleIdStr))){
                //当前页，用户选择的页面
                articleId = Integer.parseInt(articleIdStr);
            }

            int commentId = 0;
            String commentIdStr = request.getParameter("comment_id");
            if (!(commentIdStr == null || "".equals(commentIdStr))){
                commentId = Integer.parseInt("comment_id");
            }

            String preMethod = request.getParameter("pre_method");
        if (preMethod!=null){
            //设置请求的方法
            request.setAttribute("pre_method", preMethod);

            //对文章的消息--回复、评论、点赞进行预处理
            switch (preMethod){
                //评论内容
                case "reply_content":{
                    String replyMsg = request.getParameter("content");
                    //把信息组装到ArticleReply中
                    ArticleReply articleReply = new ArticleReply();
                    articleReply.setUserReplyFromId(userId);
                    articleReply.setCommentId(commentId);
                    articleReply.setReplyMessage(replyMsg);
                    //定义被回复的用户的id
                    int toUserId = 0;
                    String toUserIdStr = request.getParameter("reply_to_user_id");

                    if (toUserIdStr!=null&&!"".equals(toUserIdStr)){
                        toUserId = Integer.parseInt(toUserIdStr);
                        articleReply.setUserReplyToId(toUserId);

                        if (articleShowService.reply_replyInsert(articleReply))
                            System.out.println("回复的回复消息增加成功");
                    }
                    break;
                }
                case "comment_content":{
                    String commentMsg = request.getParameter("content");
                    //数据组装到对象中
                    ArticleComment articleComment = new ArticleComment();
                    articleComment.setCommentMessage(commentMsg);
                    articleComment.setUserCommentId(userId);
                    articleComment.setArticleId(articleId);

                    if (articleShowService.commentInsert(articleComment)){
                        System.out.println("评论消息增加成功");
                    }
                    break;
                }
                case "article_star":{
                    articleInteractionService.starInsert(1,articleId,userId);
                }
                break;
                case "comment_star":{
                    articleInteractionService.starInsert(2, articleId, userId);

                }
                break;
                case "reply_star":{
                    articleInteractionService.starInsert(3,articleId,userId);
                }
                break;
                case "article_star_cancel":{
                    articleInteractionService.starDelete(articleId,1,userId);

                }
                break;
                case "comment_star_cancel":{
                    articleInteractionService.starDelete(articleId, 2, userId);
                }
                break;
                case "reply_star_cancel":{
                    articleInteractionService.starDelete(articleId, 3, userId);
                }
                break;

                default:break;
            }
            //文章的收藏功能
            switch (preMethod){
                case "article_collection":
                {
                    articleInteractionService.collectionInsert(articleId, userId);
                }
                break;

                case "article_collection_cancel":
                {
                    articleInteractionService.collectionDelete(articleId, userId);
                }
                break;
                default:break;
            }

            //文章的转发功能
            switch (preMethod){
                case "article_share":{
                    articleInteractionService.shareInsert(articleId, userId);
                }
                break;
                case "article_share_cancel":
                {
                    articleInteractionService.shareDelete(articleId, userId);
                }
                break;
                default:break;

            }

            String method = request.getParameter("method");

            request.setAttribute("method", method);

            switch (method){
                case "article_show":
                 {
                     Article article = new Article();
                     article = articleShowService.getArticleInfo(articleId);

                     request.setAttribute("article", article);

                     if (articleInteractionService.starIsExist(1, articleId, userId) == 1)
                     {
                         request.setAttribute("starStatus", "已经点赞文章");
                     }
                     else {
                         request.setAttribute("starStatus", "还没有点赞文章");
                     }
                     if (articleInteractionService.collectionIsExist(articleId, userId))
                     {
                         request.setAttribute("collectionStatus", "已经收藏文章");
                     }
                     else {
                         request.setAttribute("collectionStatus", "还没有收藏文章");
                     }
                     if (articleInteractionService.shareIsExist( articleId, userId))
                     {
                         request.setAttribute("shareStatus", "已经转发文章");
                     }
                     else {
                         request.setAttribute("shareStatus", "还没有转发文章");
                     }
                     request.getRequestDispatcher(ARTICLE_SHOW_VIEW).forward(request, response);
                     return;
                 }
                case "comment_show":
                 {
                     int totalCount = articleShowService.getCommentTotalCount(articleId);

                     page.setTotalCount(totalCount);
                     page.setCurrentPage(currentPage);
                     page.setPageSize(pageSize);

                     List<ArticleComment> articleComments = new ArrayList<>();

                     articleComments = articleShowService.queryCommentByPage(currentPage, pageSize, userId);

                     page.setObjects(articleComments);

                     JSONObject jsonObject = new JSONObject(page);
                     //把json对象转化为字符串
                     String strJson =jsonObject.toString();

                     System.out.println("json:" + jsonObject);

                     response.setContentType("text/html;charset=UTF-8");
                     //用输出流把数据传输给article_show.jsp页面
                     PrintWriter writer = response.getWriter();

                     writer.write(strJson);
                     //在数据量比较大的情况下用flush
                     writer.flush();

                     return;

                 }
                case "reply_show":
                 {
                     int totalCount = articleShowService.getCommentTotalCount(articleId);

                     page.setTotalCount(totalCount);
                     page.setCurrentPage(currentPage);
                     page.setPageSize(pageSize);

                     List<ArticleReply> articleReplies = new ArrayList<>();

                     articleReplies = articleShowService.queryReplyByComment(currentPage, pageSize, articleId);

                     page.setObjects(articleReplies);

                     JSONObject jsonObject = new JSONObject(page);

                     String strJson = jsonObject.toString();

                     System.out.println("articleReplies---strJson" + strJson);

                     response.setContentType("text/html;charset=UTF-8");

                     PrintWriter writer = response.getWriter();

                     writer.write(strJson);
                     writer.flush();

                     return;

                 }

                default:break;
            }

        }


        




    }
}

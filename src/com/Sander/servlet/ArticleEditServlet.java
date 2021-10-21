package com.Sander.servlet;

import com.Sander.pojo.Article;
import com.Sander.pojo.User;
import com.Sander.service.ArticleEditService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description: 用于文章的编辑
 * @Param
 * @return
 * @Author Sander
 * @Date 9:36 2021/9/27
*/
@WebServlet("/ArticleEditServlet")
public class ArticleEditServlet extends HttpServlet {
    private static final long serialVersionUid = 1L;
    private static final String ARTICLE_EDIT_VIEW = "/jsp/article_edit.jsp";

    ArticleEditService articleEditService = new ArticleEditService();
    Article article = new Article();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //传输div中的html代码
        String transfer = (String) request.getParameter("transfer");

        //获得文章标题
        String title = (String) request.getParameter("title");
        HttpSession session = request.getSession();
        User user = new User();
        user = (User) session.getAttribute("userInfo");
        //获取当前用户的id值
        int userId = 0;
        if (user!=null)
            userId = user.getUserId();
        //把相关信息一起放到实体类中
        article.setContent(transfer);
        article.setTitle(title);
        article.setAuthorId(userId);
        //获取文章id
        String articleIdStr = request.getParameter("article-id");
        /**
         *  文章的id
         */
        int articleId = 0;

        //当前要显示的页面---超链接中请求对应的实现方式
        String method = request.getParameter("method");

        //设置请求的方法
        request.setAttribute("method", method);

        //选择相对应的文章编辑功能
        switch (method){
            //编辑文章，写完直接上传
            case "edit_article":
                {
                    //调用service方法新增文章
                    articleId = articleEditService.articleInsert(article);

                    //对四个可能为空的用户选择的分类名进行有判断性的插入操作
                    String a = request.getParameter("a");
                    String b = request.getParameter("b");
                    String c = request.getParameter("c");
                    String s = request.getParameter("s");
                    //判断如果非空就添加文章的分类标签
                    if (a == null || "".equals(a)){

                    }else {
                        articleEditService.articleNewTag(a , articleId);
                    }
                    if (b == null || "".equals(b)){

                    }else {
                        articleEditService.articleNewTag(b , articleId);
                    }
                    if (c == null || "".equals(c)){

                    }else {
                        articleEditService.articleNewTag(c , articleId);
                    }
                    if (s == null || "".equals(s)){

                    }else {
                        articleEditService.articleNewTag(s , articleId);
                    }
                    //跳转到article_list.jsp页面
                    request.getRequestDispatcher("/ArticleListServlet?method=my_article_list").forward(request, response);
                    return;
                }
                //修改完文章以后的提交，提交的方式是update数据
            case "alter_article":
            {

                articleId = Integer.parseInt(articleIdStr);
                article.setArticleId(articleId);
                boolean r = false;
                r = articleEditService.articleModify(article);
                if (r){
                    System.out.println("文章修改成功");
                }
                request.setAttribute("edit_msg", "修改文章");
                request.getRequestDispatcher(ARTICLE_EDIT_VIEW).forward(request, response);
                return;
            }

            default: break;
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

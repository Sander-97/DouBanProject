package com.Sander.daoImpl;


import com.Sander.dao.ArticleShowDao;
import com.Sander.pojo.Article;
import com.Sander.pojo.ArticleComment;
import com.Sander.pojo.ArticleReply;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ArticleShowDaoImpl
 * @Direction: 与文章的显示相关的所有数据库操作----具体实现类
 * @Author: Sander
 * @Date 2021/9/23 10:40
 * @Version 1.0
 **/
public class ArticleShowDaoImpl implements ArticleShowDao {

    private Connection conn;

    private PreparedStatement stm;

    private ResultSet rs;


    JdbcPool pool = new JdbcPool();

    /**
     * 通过文章id查找文章
     * @param articleId 文章id
     * @return 文章的article对象
     */
    @Override
    public Article queryArticleByArticleId(int articleId) {
        Article article = new Article();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM a_article WHERE article_id=?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, articleId);
             rs = stm.executeQuery();
            if (rs.next()){
                article.setArticleId(rs.getInt("article_id"));
                article.setTitle(rs.getString("title"));
                article.setAuthorId(rs.getInt("author"));
                article.setPublishedTime(rs.getTimestamp("published_time"));
                article.setContent(rs.getString("content"));
                article.setCollectionNum(rs.getInt("collection"));
                article.setSharNum(rs.getInt("share"));
                article.setCommentNum(rs.getInt("comment"));
                article.setStarNum(rs.getInt("star"));
                article.setStick(rs.getInt("stick"));
                article.setPageView(rs.getInt("page_view"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---queryArticleByArticleId--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }

        return article;
    }

    /**
     *  insert--新建一条评论的信息
     * @param articleComment 文章评论（ArticleComment）对象
     * @return 操作成功与否
     */
    @Override
    public boolean commentInsert(ArticleComment articleComment) {
        boolean judge = false;

        try {
            conn = pool.getConnection();
            String sql = "INSERT INTO a_comment (article_id, user_id, article_msg) values (?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, articleComment.getArticleId());
            stm.setInt(1, articleComment.getCommentId());
            stm.setString(1, articleComment.getCommentMessage());
            int rs = stm.executeUpdate();
            if (rs>0){
                judge = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---commentInsert--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return judge;
    }

    /**
     *  通过文章id，查询文章评论的所有信息
     * @param articleId 文章id
     * @return articleComment的对象
     */
    @Override
    public ArticleComment getArticleCommentInfo(int articleId) {
        ArticleComment articleComment = new ArticleComment();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM a_comment WHERE article_id=?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, articleId);
             rs = stm.executeQuery();
            if (rs.next()){

                articleComment.setArticleId(rs.getInt("article_id"));
                articleComment.setCommentId(rs.getInt("comment_id"));
                articleComment.setUserCommentId(rs.getInt("user_id"));

                articleComment.setCommentMessage(rs.getString("comment_msg"));
                articleComment.setCommentTime(rs.getTimestamp("comment_time"));
                articleComment.setCommentStarNum(rs.getInt("comment_star"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---getArticleCommentInfo--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }

        return articleComment;
    }

    /**
     *  通过文章id查询列表的记录总数
     * @param articleId 文章id
     * @return 数据记录总数
     */
    @Override
    public int getCommentTotalCount(int articleId) {
        int count = -1;
        try {
            conn = pool.getConnection();
                String sql = "select count(1) FROM a_comment WHERE article_id=?" ;
                stm = conn.prepareStatement(sql);
            stm.setInt(1,articleId);

             rs = stm.executeQuery();
            //如果查询结果不为空
            if (rs.next()){
                //获取查询的总数据量
               count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---getCommentTotalCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return count;
    }

    /**
     *  通过用户定义的页面数和页面大小，进行分页查询  排序 article表
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param articleId 文章id
     * @return 所有评论的集合
     */
    @Override
    public List<ArticleComment> queryCommentByPage(int currentPage, int pageSize, int articleId) {
        List<ArticleComment> list = new ArrayList<ArticleComment>();
        try {
            conn = pool.getConnection();
            String sql = "select * FROM a_comment WHERE article_id=? ORDER BY chat_time DESC limit ? offset ?" ;
            int begin = (currentPage -1)*pageSize;
            stm = conn.prepareStatement(sql);
            stm.setInt(1,articleId);
            stm.setInt(2, pageSize);
            stm.setInt(3, begin);

             rs = stm.executeQuery();

            while (rs.next()){

                ArticleComment articleComment = new ArticleComment();
                articleComment.setArticleId(rs.getInt("article_id"));
                articleComment.setCommentId(rs.getInt("comment_id"));
                articleComment.setCommentStarNum(rs.getInt("comment_star"));
                articleComment.setCommentMessage(rs.getString("comment_msg"));
                articleComment.setCommentTime(rs.getTimestamp("comment_time"));
               articleComment.setUserCommentId(rs.getInt("user_id"));
               list.add(articleComment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---queryCommentByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return list;
    }

    /**
     *  通过评论的id查找到该评论的用户id
     * @param articleReply 文章评论
     * @return 返回用户id
     */
    @Override
    public int getUserReplyToId(ArticleReply articleReply) {
        int userReplyToId = -1;
        try {
            conn = pool.getConnection();
            String sql = "select user_id FROM a_comment WHERE comment_id=?" ;
            stm = conn.prepareStatement(sql);
            stm.setInt(1,articleReply.getCommentId());

             rs = stm.executeQuery();
            //如果查询结果不为空
            if (rs.next()){
                //获取查询的总数据量
                userReplyToId = rs.getInt("user_id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---getUserReplyToId--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return userReplyToId;
    }

    /**
     *  新增一条文章的评论回复
     * @param articleReply 文章回复对象
     * @return 操作成功与否
     */
    @Override
    public boolean replyInsert(ArticleReply articleReply) {
        boolean judge = false;
        try {
            conn = pool.getConnection();
            String sql = "INSERT INTO a_reply (comment_id, from_user_id, reply_msg,to_user_id) values (?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, articleReply.getCommentId());
            stm.setInt(2, articleReply.getUserReplyFromId());
            stm.setString(1, articleReply.getReplyMessage());
            stm.setInt(1, articleReply.getUserReplyToId());
            int rs = stm.executeUpdate();
            if (rs>0){
                judge = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---replyInsert--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return judge;
    }

    /**
     * 通过评论的id找到该评论的所有回复信息
     * @param articleComment 评论对象
     * @return articleReply对象
     */
    @Override
    public ArticleReply getReplyInfo(ArticleComment articleComment) {
        ArticleReply articleReply = new ArticleReply();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM a_reply WHERE comment_id=?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, articleComment.getCommentId());
            rs = stm.executeQuery();
            if (rs.next()){
                articleReply.setCommentId(rs.getInt("comment_id"));
                articleReply.setReplyId(rs.getInt("reply_id"));
                articleReply.setReplyMessage(rs.getString("reply_msg"));
                articleReply.setReplyTime(rs.getTimestamp("reply_time"));
                articleReply.setUserReplyFromId(rs.getInt("from_user_id"));
                articleReply.setUserReplyToId(rs.getInt("to_user_id"));
                articleReply.setReplyStarNum(rs.getInt("reply_star"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---getReplyInfo--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }

        return articleReply;
    }

    /**
     *  通过文章评论的id得到articleReply的对象集合
     * @param articleComment 评论对象
     * @return articleReply的对象集合
     */
    @Override
    public List<ArticleReply> queryReplyByComment(ArticleComment articleComment) {
        List<ArticleReply> articleReplies = new ArrayList<ArticleReply>();

        try {
            conn = pool.getConnection();
            String sql = "select * FROM a_reply WHERE comment_id=?" ;
            stm = conn.prepareStatement(sql);
            stm.setInt(1,articleComment.getCommentId());

            rs = stm.executeQuery();

            while (rs.next()){

                ArticleReply articleReply = new ArticleReply();

                articleReply.setCommentId(rs.getInt("comment_id"));
                articleReply.setReplyId(rs.getInt("reply_id"));
                articleReply.setUserReplyFromId(rs.getInt("from_user_id"));
                articleReply.setUserReplyToId(rs.getInt("to_user_id"));
                articleReply.setReplyStarNum(rs.getInt("reply_star"));
                articleReply.setReplyMessage(rs.getString("reply_msg"));
                articleReply.setReplyTime(rs.getTimestamp("reply_time"));
                articleReplies.add(articleReply);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleShowDaoImpl---queryReplyByComment--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return articleReplies;
    }
}

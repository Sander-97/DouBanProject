package com.Sander.daoImpl;

import com.Sander.dao.ArticleListDao;
import com.Sander.pojo.ArticleComment;
import com.Sander.pojo.ArticleList;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ArticleListDaoImpl
 * @Direction: 对文章列表所需信息进行数据库操作
 * @Author: Sander
 * @Date 2021/9/23 10:40
 * @Version 1.0
 **/
public class ArticleListDaoImpl implements ArticleListDao {
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    JdbcPool pool = new JdbcPool();

    /**
     *  查询列表的记录总数 article表
     * @return
     */
    @Override
    public int getTotalCount() {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_article";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            System.out.println("ArticleListDaoImpl---getTotalCount--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  分页查询文章列表 article表
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回查询的文章article表集合
     */
    @Override
    public List<ArticleList> queryArticleListByPage(int currentPage, int pageSize) {
        List<ArticleList> articleLists = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "select * FROM a_article ORDER BY stick DESC ,published_time DESC LIMIT ? OFFSET ?" ;
            stm = con.prepareStatement(sql);
            stm.setInt(1, pageSize);
            stm.setInt(2, (currentPage - 1)*pageSize);
            rs = stm.executeQuery();

            while (rs.next()){
                ArticleList articleList = new ArticleList();
                articleList.setArticleId(rs.getInt("article_id"));
                articleList.setAuthorId(rs.getInt("author"));
                articleList.setTitle(rs.getString("title"));
                articleList.setCollectionNum(rs.getInt("collection"));
                articleList.setCommentNum(rs.getInt("comment"));
                articleList.setContent(rs.getString("content"));
                articleList.setShareNum(rs.getInt("share"));
                articleList.setPageViewer(rs.getInt("page_view"));
                articleList.setStarNum(rs.getInt("star"));
                articleList.setPublishedTime(rs.getTimestamp("published_time"));
                //是否置顶
                articleList.setStick(rs.getInt("stick"));

                articleLists.add(articleList);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleListDaoImpl---queryArticleListByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return articleLists;

    }

    /**
     *  根据用户id查询文章的记录总数
     * @param userId 用户id
     * @return 返回查询到的记录总数
     */
    @Override
    public int getMyTotalCount(int userId) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_article WHERE author=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            System.out.println("ArticleListDaoImpl---getMyTotalCount--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  分页查询我的文章列表信息，根据我的用户id
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return 返回查询到的记录集合
     */
    @Override
    public List<ArticleList> queryMyArticleListByPage(int currentPage, int pageSize, int userId) {

        List<ArticleList> articleLists = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "select * FROM a_article WHERE author=? ORDER BY stick DESC,published_time DESC LIMIT ? OFFSET ?" ;
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, pageSize);
            stm.setInt(3, (currentPage - 1)*pageSize);

            rs = stm.executeQuery();

            while (rs.next()){
                ArticleList articleList = new ArticleList();
                articleList.setArticleId(rs.getInt("article_id"));
                articleList.setAuthorId(rs.getInt("author"));
                articleList.setTitle(rs.getString("title"));
                articleList.setCollectionNum(rs.getInt("collection"));
                articleList.setCommentNum(rs.getInt("comment"));
                articleList.setContent(rs.getString("content"));
                articleList.setShareNum(rs.getInt("share"));
                articleList.setPageViewer(rs.getInt("page_view"));
                articleList.setStarNum(rs.getInt("star"));
                articleList.setPublishedTime(rs.getTimestamp("published_time"));
                //是否置顶
                articleList.setStick(rs.getInt("stick"));

                articleLists.add(articleList);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleListDaoImpl---queryMyArticleListByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return articleLists;
    }

    /**
     *  模糊搜索文章的标题，查询到符合条件的文章记录总数
     * @param searchContent 模糊搜索的字段内容
     * @return 返回查询到的记录数量
     */
    @Override
    public int getArticleSearchCount(String searchContent) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_article WHERE title LIKE ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%"+searchContent+"%");
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            System.out.println("ArticleListDaoImpl---getArticleSearchCount--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     * 分页查询---模糊搜索文章的标题，查询到符合条件的文章对象集合
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 模糊搜索字段内容
     * @return 返回查询到的数据记录集合
     */
    @Override
    public List<ArticleList> queryArticleByPage(int currentPage, int pageSize, String searchContent) {

        List<ArticleList> articleLists = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "select * FROM a_article WHERE title LIKE ?  LIMIT ? OFFSET ?" ;
            stm = con.prepareStatement(sql);
            stm.setInt(1, pageSize);
            stm.setInt(2, (currentPage - 1)*pageSize);

            rs = stm.executeQuery();

            while (rs.next()){
                ArticleList articleList = new ArticleList();
                articleList.setArticleId(rs.getInt("article_id"));
                articleList.setAuthorId(rs.getInt("author"));
                articleList.setTitle(rs.getString("title"));
                articleList.setCollectionNum(rs.getInt("collection"));
                articleList.setCommentNum(rs.getInt("comment"));
                articleList.setContent(rs.getString("content"));
                articleList.setShareNum(rs.getInt("share"));
                articleList.setPageViewer(rs.getInt("page_view"));
                articleList.setStarNum(rs.getInt("star"));
                articleList.setPublishedTime(rs.getTimestamp("published_time"));
                //是否置顶
                articleList.setStick(rs.getInt("stick"));

                articleLists.add(articleList);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleListDaoImpl---queryArticleByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return articleLists;
    }

    /**
     *  搜索我的文章中符合条件的记录数
     * @param searchContent 搜索的内容
     * @param authorId 作者id
     * @return 返回符合条件的记录数量
     */
    @Override
    public int getMyArticleSearchCount(String searchContent, int authorId) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_article WHERE author=? AND title LIKE ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, authorId);
            stm.setString(2, "%"+searchContent+"%");
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            System.out.println("ArticleListDaoImpl---getMyArticleSearchCount--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  分页查询，搜索到我的文章的符合条件的对象集合
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 搜索内容
     * @param authorId 作者id
     * @return 返回文章对象集合
     */
    @Override
    public List<ArticleList> queryMyArticleByPage(int currentPage, int pageSize, String searchContent, int authorId) {

        List<ArticleList> articleLists = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "select * FROM a_article WHERE author=? AND title LIKE ?  LIMIT ? OFFSET ?" ;
            stm = con.prepareStatement(sql);
            stm.setInt(1, authorId);
            stm.setString(2, "%"+searchContent+"%");
            stm.setInt(3, pageSize);
            stm.setInt(4, (currentPage - 1)*pageSize);
            rs = stm.executeQuery();

            while (rs.next()){

                ArticleList articleList = new ArticleList();

                articleList.setArticleId(rs.getInt("article_id"));
                articleList.setAuthorId(rs.getInt("author"));
                articleList.setTitle(rs.getString("title"));
                articleList.setCollectionNum(rs.getInt("collection"));
                articleList.setCommentNum(rs.getInt("comment"));
                articleList.setContent(rs.getString("content"));
                articleList.setShareNum(rs.getInt("share"));
                articleList.setPageViewer(rs.getInt("page_view"));
                articleList.setStarNum(rs.getInt("star"));
                articleList.setPublishedTime(rs.getTimestamp("published_time"));
                //是否置顶
                articleList.setStick(rs.getInt("stick"));

                articleLists.add(articleList);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleListDaoImpl---queryMyArticleByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return articleLists;
    }

    /**
     *  查询我的收藏文章的记录总数
     * @param userId 用户id
     * @return 返回记录总数
     */
    @Override
    public int getMyCollectionTotalCount(int userId) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_article ,a_collection WHERE a_article.article_id=a_collection.article_id AND a_collection.user_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            System.out.println("ArticleListDaoImpl---getMyCollectionTotalCount--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  分页查询，我的收藏，得到查询的对象集合
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return
     */
    @Override
    public List<ArticleList> queryMyCollectionBytPage(int currentPage, int pageSize, int userId) {

        List<ArticleList> articleLists = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "select * FROM a_article,a_collection WHERE a_article.article_id=a_collection.article_id AND a_collection.user_id=? ORDER BY stick DESC,published_time DESC LIMIT ? OFFSET ?" ;
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, pageSize);
            stm.setInt(3, (currentPage - 1)*pageSize);
            rs = stm.executeQuery();

            while (rs.next()){

                ArticleList articleList = new ArticleList();

                articleList.setArticleId(rs.getInt("article_id"));
                articleList.setAuthorId(rs.getInt("author"));
                articleList.setTitle(rs.getString("title"));
                articleList.setCollectionNum(rs.getInt("collection"));
                articleList.setCommentNum(rs.getInt("comment"));
                articleList.setContent(rs.getString("content"));
                articleList.setShareNum(rs.getInt("share"));
                articleList.setPageViewer(rs.getInt("page_view"));
                articleList.setStarNum(rs.getInt("star"));
                articleList.setPublishedTime(rs.getTimestamp("published_time"));
                //是否置顶
                articleList.setStick(rs.getInt("stick"));

                articleLists.add(articleList);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleListDaoImpl---queryMyCollectionBytPage--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return articleLists;
    }
}

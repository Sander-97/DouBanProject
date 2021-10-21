package com.Sander.daoImpl;

import com.Sander.dao.ArticleDao;
import com.Sander.pojo.Article;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName ArticleDaoImpl
 * @Direction: 文章编辑的数据库实现类
 * @Author: Sander
 * @Date 2021/9/23 10:39
 * @Version 1.0
 **/
public class ArticleDaoImpl implements ArticleDao {
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    JdbcPool pool = new JdbcPool();

    /**
     * 新增一条文章记录
     * @param article article表对应的实体类
     * @return 返回文章的id
     */
    @Override
    public int articleInsert(Article article) {
        int articleId = 0;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO a_article (title,author,content) values(?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, article.getTitle());
            stm.setInt(2, article.getAuthorId());
            stm.setString(3, article.getContent());
            stm.executeUpdate();

            String sql_1 = "SELECT LAST_INSERT_ID()";
            stm = con.prepareStatement(sql_1);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                articleId = rs.getInt(1);
        } catch (SQLException throwables) {
            System.out.println("ArticleDaoImpl---articleInsert--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return articleId;
    }

    /**
     *  查询一篇文章
     * @param article article表对应的实体类
     * @return 返回改文章是否存在
     *         1 是存在
     *         0 是不存在
     */
    @Override
    public int articleQuery(Article article) {
        int judge = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM a_article WHERE title=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, article.getTitle());
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                judge = 1;
        } catch (SQLException throwables) {
            judge = 0;
            System.out.println("ArticleDaoImpl---articleInsert--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  更新article表，一条记录
     * @param article article表对应的实体类
     * @return 返回操作成功与否
     */
    @Override
    public boolean articleUpdate(Article article) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE a_article SET title=?,content=? WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setString(1,article.getTitle());
            stm.setString(1,article.getContent());
            stm.setInt(1,article.getArticleId());
            int rs = stm.executeUpdate();
            if (rs>=0)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            throwables.printStackTrace();
            System.out.println("friendOperation---friendGroupInsert--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  查询标签名是否存在
     * @param tagName 用户所输入的标签名
     * @return 如果存在就返回tag_id，如果不存在就返回-1.
     */
    @Override
    public int tagIsExist(String tagName) {
        int judge = -1;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM a_tag WHERE tag_name=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, tagName);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                judge = rs.getInt("tag_id");
        } catch (SQLException throwables) {
            judge = -1;
            System.out.println("FriendDao---getMyTotalCount--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  插入一个标签
     * @param tagName 用户输入的标签tag名字
     * @return 返回插入的标签的id,操作不成功返回-1
     */
    @Override
    public int tagInsert(String tagName) {
        int articleId = -1;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO a_tag (tag_name) values(?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, tagName);

            stm.executeUpdate();

            String sql_1 = "SELECT LAST_INSERT_ID()";
            stm = con.prepareStatement(sql_1);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                articleId = rs.getInt(1);
        } catch (SQLException throwables) {
            articleId = -1;
            System.out.println("ArticleDaoImpl---articleInsert--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return articleId;
    }

    /**
     *  插入一条记录到文章-标签表中
     * @param articleId 文章id
     * @param tagId 文章的分类标签id
     * @return 返回操作成功与否
     */
    @Override
    public boolean middleInsert(int articleId, int tagId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO  article_to_tag (article_id,tag_id) values (?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1,articleId);
            stm.setInt(2,tagId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            throwables.printStackTrace();
            System.out.println("ArticleDaoImpl---middleInsert--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  删除一条记录在文章-标签表中
     * @param articleId 文章的id
     * @return 返回操作成功与否
     */
    @Override
    public boolean middleDelete(int articleId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM article_to_tag WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1,articleId);
            int rs = stm.executeUpdate();
            if (rs>=0)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            throwables.printStackTrace();
            System.out.println("ArticleDaoImpl---middleDelete--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  删除一篇文章
     * @param articleId 文章id
     * @return 返回操作成功与否
     */
    @Override
    public boolean articleDelete(int articleId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM a_article WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1,articleId);
            int rs = stm.executeUpdate();
            if (rs>=0)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            throwables.printStackTrace();
            System.out.println("ArticleDaoImpl---articleDelete--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }
}

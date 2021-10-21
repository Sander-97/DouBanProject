package com.Sander.daoImpl;

import com.Sander.dao.ArticleInteractionDao;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName ArticleInteractionDaoImpl
 * @Direction: 文章的交互功能-----点赞，转发，评论，收藏
 * @Author: Sander
 * @Date 2021/9/23 10:39
 * @Version 1.0
 **/
public class ArticleInteractionDaoImpl implements ArticleInteractionDao {
    private Connection con;
    private PreparedStatement stm;
    JdbcPool pool = new JdbcPool();
    private ResultSet rs;

    /**
     *  查询评论数在a_comment表找到符合article_id的记录总数
     * @param articleId 文章id
     * @return 返回记录的数量
     */
    @Override
    public int commentNumQuery(int articleId) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_comment WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, articleId);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----commentNumQuery------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  更新文章表中的评论数量
     * @param commentNum 评论数量
     * @param articleId 文章id
     * @return 返回操作是否成功
     */
    @Override
    public boolean commentNumUpdate(int commentNum, int articleId) {
       boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE a_article set comment=? WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, commentNum);
            stm.setInt(2, articleId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----commentNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

       return judge;
    }

    /**
     *  查看点赞是否存在，在a_star表中查找符合条件的记录
     * @param typeId 类型对应的主键id
     * @param type 所属类型
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean starIsExist(int typeId, int type, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM a_star WHERE user_id=? AND type_id=? AND type=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, typeId);
            stm.setInt(3, type);
            rs = stm.executeQuery();
            if (rs.next())
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----starIsExist------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  新增一条点赞记录
     * @param typeId 类型的主键id
     * @param type 所属类型
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean starInsert(int typeId, int type, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO a_star (type_id,type,user_id) values (?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, typeId);
            stm.setInt(2, userId);
            stm.setInt(3, type);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----starInsert------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  删除一条点赞记录
     * @param typeId 类型主键id
     * @param type 所属类型
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean starDelete(int typeId, int type, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM a_star WHERE user_id=? AND type=? AND type_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, type);
            stm.setInt(3, typeId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----commentNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     * 查询点赞数，查找a——star表中的点赞数
     * @param typeId 操作类型id
     * @param type 所属类型
     * @return 查询到的记录数量
     */
    @Override
    public int starNumQuery(int typeId, int type) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_star WHERE article_id=? AND type=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, typeId);
            stm.setInt(2, type);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----starNumQuery------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  更新文章表中的点赞数
     * @param starNum 点赞数
     * @param articleId 文章id
     * @return
     */
    @Override
    public boolean starNumUpdate(int starNum, int articleId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE  a_article SET star=? WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, starNum);
            stm.setInt(2, articleId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----starNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  评论表的点赞数量更新
     * @param starNum 评论的点赞数
     * @param commentId 评论的id
     * @return 返回操作是否成功
     */
    @Override
    public boolean commentStarNumUpdate(int starNum, int commentId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "Update a_comment SET comment_star=? WHERE comment_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, starNum);
            stm.setInt(2, commentId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----commentStarNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     * 在回复表中点赞数量的更新
     * @param starNum 回复的点赞数
     * @param replyId 回复的id
     * @return 返回操作是否成功
     */
    @Override
    public boolean replyStarNumUpdate(int starNum, int replyId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE a_reply set r_star=? WHERE reply_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, starNum);
            stm.setInt(2, replyId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----replyStarNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  查询收藏是否存在，在收藏表a——collection中查询是否有该条记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回是否存在该记录
     */
    @Override
    public boolean collectionIsExist(int articleId, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM a_collection WHERE article_id=? AND user_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, articleId);
            stm.setInt(2, userId);
            rs = stm.executeQuery();
            if (rs.next())
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----collectionIsExist------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     * 在收藏表a_collection中新增一条收藏记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean collectionInsert(int articleId, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO a_collection (article_id,user_id) values (?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, articleId);
            stm.setInt(2, userId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----collectionInsert------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     * 在收藏表a_collection中删除一条收藏记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean collectionDelete(int articleId, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM a_collection WHERE user_id=? AND article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, articleId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----collectionDelete------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  在收藏表中根据文章id查找收藏数量
     * @param articleId 文章id
     * @return 返回查询到的具体数量
     */
    @Override
    public int collectionNumQuery(int articleId) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_collection WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, articleId);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----starNumQuery------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     *  更新a_article表中收藏的数目
     * @param articleId 文章id
     * @param collectionNum 收藏数
     * @return 返回操作是否成功
     */
    @Override
    public boolean collectionNumUpdate(int articleId, int collectionNum) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE a_collection set collection=? WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, collectionNum);
            stm.setInt(2, articleId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----collectionNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     * 在a_share表中查找分享记录是否存在
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean shareIsExist(int articleId, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM a_share WHERE user_id=? AND article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, articleId);
            rs = stm.executeQuery();
            if (rs.next())
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----starIsExist------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  在a_share表中新增一条分享记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean shareInsert(int articleId, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO  a_share (article_id,user_id) values (?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1,articleId);
            stm.setInt(2,userId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            throwables.printStackTrace();
            System.out.println("ArticleDaoImpl---shareInsert--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  删除一条分享记录在a_share表
      * @param articleId 对应的文章id
     * @param userId 进行转发的用户id
     * @return 返回操作是否成功
     */
    @Override
    public boolean shareDelete(int articleId, int userId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM a_share WHERE user_id=? AND  article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, articleId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----shareDelete------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }

    /**
     *  在a_share表中根据文章id查找该文的分享数量
     * @param articleId 对应的文章id
     * @return 返回查找到的分享数量
     */
    @Override
    public int shareNumQuery(int articleId) {
        int count = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT count(1) FROM a_share WHERE article_id=? ";
            stm = con.prepareStatement(sql);
            stm.setInt(1, articleId);
            rs = stm.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----shareNumQuery------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return count;
    }

    /**
     * 在a_share表中更新一条记录，更新一篇文章的分享数
     * @param articleId 对应的文章id
     * @return 返回操作是否成功
     */
    @Override
    public boolean shareNumUpdate(int articleId,int shareNum) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE a_share set share=? WHERE article_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, shareNum);
            stm.setInt(2, articleId);
            int rs = stm.executeUpdate();
            if (rs>0)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("ArticleInteractionDaoImpl-----shareNumUpdate------数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }

        return judge;
    }
}

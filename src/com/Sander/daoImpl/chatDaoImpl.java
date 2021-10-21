package com.Sander.daoImpl;

import com.Sander.dao.chatDao;
import com.Sander.pojo.chat;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;



import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName chatDaoImpl
 * @Direction: 关于用户聊天的数据库操作
 * @Author: Sander
 * @Date 2021/9/23 10:40
 * @Version 1.0
 **/
public class chatDaoImpl implements chatDao {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    //新建一个数据库连接对象
    JdbcPool pool = new JdbcPool();

    /**
     *  在聊天列表中查找当前用户id收到的聊天记录，得到记录的总数
     * @param userId 用户id
     * @return 返回查询到的记录总数
     */
    @Override
    public int getTotalCount(int userId) {
       int count = -1;
        try {
            conn = pool.getConnection();
            String sql = "SELECT count(1) FROM chat WHERE to_user_id=?";
            stmt =  conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            //如果查询结果不为空
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("chatDaoImpl---getTotalCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stmt,rs);
        }
        return count;
    }

    /**
     *  查询当前用户收到的聊天记录数据集合
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return 返回数据集合
     */
    @Override
    public List<chat> queryChatByPage(int currentPage, int pageSize, int userId) {
        List<chat> chats = new ArrayList<chat>();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM chat WHERE to_user_id=? ORDER BY chat_time DESC limit ? offset ?";
            //limit为查询到数目，也就是pageSize
            //offset就是起始页
            int begin = (currentPage - 1)*pageSize;
            stmt =  conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, pageSize);
            stmt.setInt(1, begin);
            rs = stmt.executeQuery();
            while (rs.next()){
                chat chat = new chat();
                //拿到chat表中的详细数据
                //依次获取信息数据
                chat.setChatId(rs.getInt("chat_id"));
                chat.setFromUserId(rs.getInt("from_user_id"));
                chat.setToUserId(rs.getInt("to_user_id"));
                chat.setChatTime(rs.getTimestamp("chat_time"));
                chat.setChatMessage(rs.getString("chat_msg"));
                chat.setStatus(rs.getInt("status"));
                chat.setRead(rs.getInt("read"));
                chats.add(chat);
            }
        } catch (SQLException throwables) {
            System.out.println("chatDaoImpl---queryChatListByPage--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(conn,stmt,rs);
        }
        return chats;
    }

    /**
     * 得到符合当前用户和别选择用户的聊天记录总数
     * @param userId 发送人id
     * @param toUserId 接收人id
     * @return 返回记录总数
     */
    @Override
    public int getShowTotalCount(int userId, int toUserId) {
        //总数据量
        int count = -1;
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM chat WHERE from_user_id = ? AND to_user_id = ? OR to_user_id = ? AND from_user_id = ?";
            stmt =  conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, toUserId);
            stmt.setInt(3, userId);
            stmt.setInt(4, toUserId);
            rs = stmt.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("chatDaoImpl---getShowTotalCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stmt,rs);
        }
        return count;
    }

    /**
     *  在chat聊天列表中查找有关当前用户id和当前用户选择的用户的聊天记录---数据集合
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 发送人用户id
     * @param toUserId  接收人用户id
     * @return 数据集合
     */
    @Override
    public List<chat> queryShowByPage(int currentPage, int pageSize, int userId, int toUserId) {
        List<chat> chats = new ArrayList<chat>();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM chat WHERE from_user_id = ? AND to_user_id = ? OR to_user_id = ? AND from_user_id = ? ORDER BY chat_time DESC limit ? offset ?";
            int begin = (currentPage -1)*pageSize;
            stmt =  conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, toUserId);
            stmt.setInt(3, userId);
            stmt.setInt(4, toUserId);
            stmt.setInt(5, pageSize);
            stmt.setInt(4, begin);
            rs = stmt.executeQuery();
            while (rs.next()){
                chat chat = new chat();
                chat.setToUserId(rs.getInt("to_user_id"));
                chat.setFromUserId(rs.getInt("from_user_id"));
                chat.setChatId(rs.getInt("chat_id"));
                chat.setChatMessage(rs.getString("chat_msg"));
                chat.setChatTime(rs.getTimestamp("chat_time"));
                chat.setStatus(rs.getInt("status"));
                chat.setRead(rs.getInt("read"));
                chats.add(chat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("chatDaoImpl---queryShowByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stmt,rs);
        }

        return chats;
    }

    /**
     * 增加一条记录到聊天列表中
     * @param chat chat对象
     * @return 操作成功与否
     */
    @Override
    public boolean chatInsert(chat chat) {
         boolean judge = false;
        try {
            conn = pool.getConnection();
            String sql = "INSERT INTO chat (from_user_id,to_user_id,chat_msg) values(?,?,?)";
            stmt =  conn.prepareStatement(sql);
            stmt.setInt(1, chat.getFromUserId());
            stmt.setInt(2, chat.getToUserId());
            stmt.setString(3, chat.getChatMessage());
            int i =stmt.executeUpdate();
            if(i > 0){
                judge = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            judge=false;
            System.out.println("chatDaoImpl---chatInsert--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stmt,rs);
        }
        return judge;
    }
}

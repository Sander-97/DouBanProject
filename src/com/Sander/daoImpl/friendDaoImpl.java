package com.Sander.daoImpl;

import com.Sander.dao.friendDao;
import com.Sander.pojo.User;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName friendDaoImpl
 * @Direction: 处理用户好友相关的数据库操作
 * @Author: Sander
 * @Date 2021/9/23 10:40
 * @Version 1.0
 **/
public class friendDaoImpl implements friendDao {
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    JdbcPool pool = new JdbcPool();


    /**
     *  获取好友总数
     * @return 总的数量
     */
    @Override
    public int getTotalCount() {
        int count = 0;
        try {
            conn = pool.getConnection();
            //查询记录总数
            String sql = "SELECT count(1) FROM user";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()){
                //获取查询的count值
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---getTotalCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return count;
    }

    /**
     *  根据当前页和页面大小进行分页查询，查询每个人
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 当前页所有符合条件的对象的List集合
     */
    @Override
    public List<User> queryEveryoneByPage(int currentPage, int pageSize) {
        List<User> users = new ArrayList<User>();
        try {
            conn = pool.getConnection();
            int begin = (currentPage - 1)* pageSize;
            String sql = "SELECT * from user LIMIT ? OFFSET ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1,pageSize);
            stm.setInt(2,begin);
            rs = stm.executeQuery();
            //获取查询到的用户所有信息
            while (rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setStatus(rs.getInt("status"));
                user.setPassWord(rs.getString("password"));
                user.setReported(rs.getInt("reported"));
                user.setPortrait(rs.getString("portrait"));
                user.setAddress(rs.getString("address"));
                user.setSignature(rs.getString("signature"));
                user.setSelfIntroduction(rs.getString("self_introduction"));
                user.setNickName(rs.getString("nickname"));
                user.setTitleTime(rs.getTimestamp("title_time"));
                user.setTime(rs.getTimestamp("time"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---queryEveryoneByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return users;
    }

    /**
     *  搜索用户的昵称nickname,根据传入的searchContent，查询符合条件的用户昵称，得到满足条件的记录数量
     * @param searchContent 要模糊搜索的内容
     * @return 搜索得到的记录数量
     */
    @Override
    public int getSearchCount(String searchContent) {
        int count = 0;
        try {
            conn = pool.getConnection();
            String sql = "SELECT count(1) FROM user WHERE nickname LIKE ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + searchContent + "%");
            ResultSet rs =stm.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---getSearchCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return count;
    }

    /**
     *  分页查询用户的昵称nickname，根据当前页和页面大小进行分页查询，搜索符合条件的用户昵称
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 要搜索的内容
     * @return 返回满足条件的用户users集合List
     */
    @Override
    public List<User> queryEveryoneByPage(int currentPage, int pageSize, String searchContent) {
        List<User> users = new ArrayList<>();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM ( SELECT * FROM USER WHERE nickname LIKE ? ) AS searchUserList LIMIT ? OFFSET ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1,"%" + searchContent + "%");
            stm.setInt(2,pageSize);
            stm.setInt(3, (currentPage - 1)*pageSize);
            rs= stm.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setStatus(rs.getInt("status"));
                user.setPassWord(rs.getString("password"));
                user.setReported(rs.getInt("reported"));
                user.setPortrait(rs.getString("portrait"));
                user.setAddress(rs.getString("address"));
                user.setSignature(rs.getString("signature"));
                user.setSelfIntroduction(rs.getString("self_introduction"));
                user.setNickName(rs.getString("nickname"));
                user.setTitleTime(rs.getTimestamp("title_time"));
                user.setTime(rs.getTimestamp("time"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---queryEveryoneByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return users;
    }

    /**
     *  我的关注、好友、黑名单 的记录总数
     * @param userId 当前用户的id
     * @param status 查询条件：  1.关注    2.好友    3.黑名单
     * @return 记录的总数
     */
    @Override
    public int getFriendTotalCount(int userId, int status) {
        int count =0;
        try {
            conn = pool.getConnection();
            String sql = "SELECT count(1) FROM friend WHERE from_user_id = ? AND status = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1,userId);
            stm.setInt(2,status);
            rs = stm.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---getFriendTotalCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return count;
    }

    /**
     *  基于当前页和页面大小进行分页查询，查找  我的关注、好友、黑名单 的记录总数
     * @param userId  当前用户的id
     * @param status  查询的条件 1.关注-----2.好友-----3.黑名单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回满足条件的当前页user用户List集合
     * */
    @Override
    public List<User> queryFriendByPage(int userId, int status, int currentPage, int pageSize) {
        List<User> users = new ArrayList<>();
        try {
            conn = pool.getConnection();
            String sql ="SELECT * FROM friend WHERE from_user_id = ? AND status = ? LIMIT ? OFFSET ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, status);
            stm.setInt(3, pageSize);
            stm.setInt(4, (currentPage - 1)*pageSize);
            rs = stm.executeQuery();
            while (rs.next()){
                User user = new User();
                UserDaoImpl userDao = new UserDaoImpl();
                user = userDao.userInfoByUserId(rs.getInt("to_user_id"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---queryFriendByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }

        return users;
    }

    /**
     * 我关注的人、好友、黑名单- 找出其中符合条件的记录数 user表
     * @param searchContent 搜索的内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @return 查询到的记录数
     */
    @Override
    public int getFriendSearchCount(String searchContent, int userId, int status) {
        int count = 0;
        try {
            conn  = pool.getConnection();
            String sql = "SELECT count(1) FROM friend INNER JOIN user ON friend.from_user_id =? AND friend.status = ? AND user.nickname LIKE ? AND friend.to_user_id=user.user_id";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, status);
            stm.setString(3, "%"+searchContent+"%");
            rs = stm.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---getFriendSearchCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return count;
    }

    /**
     *  分页查询我关注的人、好友、黑名单，满足条件的对象集合
     * @param searchContent 搜索内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回当前页的对象集合
     */
    @Override
    public List<User> queryFriendByPage(String searchContent, int userId, int status, int currentPage, int pageSize) {
        List<User> users = new ArrayList<>();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM friend INNER JOIN user on friend.from_user_id=?AND friend.status=? AND user.nickname LIKE ? AND friend.to_user_id = user.user_id LIMIT ? OFFSET ?";
            stm =conn.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, status);
            stm.setString(3, "%"+searchContent+"%");
            stm.setInt(4, pageSize);
            stm.setInt(5, (currentPage - 1)*pageSize);
            rs = stm.executeQuery();
            while (rs.next()){
                User user = new User();
                UserDaoImpl userDao = new UserDaoImpl();
                user = userDao.userInfoByUserId(rs.getInt("to_user_id"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---queryFriendByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }

        return users;
    }

    /**
     * 联表查询，得到满足条件的记录数
     * @param searchContent 搜索的内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @return 返回记录的数量
     */
    @Override
    public int getFriendGroupCount(String searchContent, int userId, int status) {
        int count = 0;
        try {
            conn = pool.getConnection();
            String sql ="SELECT count(1) FROM friend INNER JOIN friend_group ON friend.from_user_id=? AND friend.status=? AND friend_group.group_name=? AND friend.group_id=friend_group.group_id";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, status);
            stm.setString(3,searchContent);
            rs = stm.executeQuery();
            if (rs.next())
            count = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---getFriendGroupCount--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return count;
    }

    /**
     *  分页查询好友分组 获取符合条件的记录集合
     * @param SearchContent 搜索内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回符合条件的记录集合
     */
    @Override
    public List<User> queryFriendGroupByPage(String SearchContent, int userId, int status, int currentPage, int pageSize) {
        List<User> users = new ArrayList<>();
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM friend INNER JOIN friend_group ON friend.from_user_id=? AND friend.status=? AND friend_group.group_name=? AND friend.group_id = friend_group.group_id LIMIT ? OFFSET ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, status);
            stm.setString(3, "%"+SearchContent+"%");
            stm.setInt(4, pageSize);
            stm.setInt(5, (currentPage - 1) *pageSize);
            rs = stm.executeQuery();
            while (rs.next()){
                UserDaoImpl userDao = new UserDaoImpl();
                User user = new User();
                user = userDao.userInfoByUserId(rs.getInt("to_user_id"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---queryFriendGroupByPage--数据库连接异常");
        } finally {
            JDBCUtil.release(conn,stm,rs);
        }
        return users;
    }
}

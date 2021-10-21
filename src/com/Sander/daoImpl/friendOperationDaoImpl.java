package com.Sander.daoImpl;

import com.Sander.dao.friendOperationDao;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName friendOperationDaoImpl
 * @Direction: 好友关系操作的处理实现类
 * @Author: Sander
 * @Date 2021/9/23 10:41
 * @Version 1.0
 **/
public class friendOperationDaoImpl implements friendOperationDao {
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    JdbcPool pool = new JdbcPool();

    /**
     *  更新好友关系的状态信息
     * @param fromUserId friend表中记录的发出者
     * @param toUserId 用户选择的好友
     * @param status 更改后的好友状态
     * @return 返回操作你是否成功
     */
    @Override
    public boolean friendUpdate(int fromUserId, int toUserId, int status) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE friend SET status=? WHERE from_user_id=? AND to_user_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, fromUserId);
            stm.setInt(3, toUserId);
            int rs = stm.executeUpdate();
            if (rs==1)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            System.out.println("friendOperation---friendUpdate--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  删除好友的操作
     * @param fromUserId 记录的发出者
     * @param toUserId 记录的执行人
     * @return 返回操作是否成功
     */
    @Override
    public boolean friendDelete(int fromUserId, int toUserId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM friend  WHERE from_user_id=? AND to_user_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, fromUserId);
            stm.setInt(2, toUserId);
            int rs = stm.executeUpdate();
            if (rs==1)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            System.out.println("friendOperation---friendDelete--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  新增一个好友的操作
     * @param fromUserId 记录的发出人
     * @param toUserId 记录的接收人
     * @return 返回操作是否成功
     */
    @Override
    public boolean friendInsert(int fromUserId, int toUserId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO  friend(from_user_id,to_user_id,status) values (?,?,1)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, fromUserId);
            stm.setInt(2, toUserId);
            int rs = stm.executeUpdate();
            if (rs==1)
                judge = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("friendOperation---friendInsert--数据库连接异常");
            judge = false;
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  根据好友的id查询好友的关系信息
     * @param fromUserId 进行查询的用户的id
     * @param toUserId 被查询的用户id
     * @return 返回查询到的好友id
     */
    @Override
    public int friendQuery(int fromUserId, int toUserId) {
       int status = 0 ;
        try {
            con = pool.getConnection();
            String sql = "SELECT status FROM friend WHERE from_user_id=? AND to_user_id=?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, fromUserId);
            stm.setInt(2, toUserId);
            rs = stm.executeQuery();
            if (rs.next())
                status = rs.getInt("status");
        } catch (SQLException throwables) {
            System.out.println("friendOperation---friendQuery--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return status;
    }

    /**
     *  查询好友分组是否存在
     * @param groupName 好友分组名称
     * @return 返回查询的结果，true表明存在，false则为不存在
     */
    @Override
    public boolean friendGroupIsExist(String groupName) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM friend_group WHERE group_name=? ";
            stm = con.prepareStatement(sql);
           stm.setString(1,groupName);
            rs = stm.executeQuery();
            if (rs.next())
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            System.out.println("friendOperation---friendGroupIsExist--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  新增一个好友分组
     * @param groupName 分组名
     * @return 返回操作是否成功
     */
    @Override
    public boolean friendGroupInsert(String groupName) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO  friend_group (group_name) values (?)";
            stm = con.prepareStatement(sql);
            stm.setString(1,groupName);
            int rs = stm.executeUpdate();
            if (rs==1)
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
     * 更改好友的分组
     * @param fromUserId 好友关系发出用户的id
     * @param toUserId 好友关系接收人的id
     * @param status 好友关系状态
     * @param groupId 好友分组id
     * @return 返回操作是否成功
     */
    @Override
    public boolean friendGroupUpdate(int fromUserId, int toUserId, int status, int groupId) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE friend SET group_id=? WHERE from_user_id=? AND to_user_id=? AND status=?";
            stm = con.prepareStatement(sql);
           stm.setInt(1,groupId);
           stm.setInt(2,fromUserId);
           stm.setInt(3,toUserId);
           stm.setInt(4,status);
            int rs = stm.executeUpdate();
            if (rs==1)
                judge = true;
        } catch (SQLException throwables) {
            judge = false;
            throwables.printStackTrace();
            System.out.println("friendOperation---friendGroupUpdate--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return judge;
    }

    /**
     *  通过分组名查询分组的id
     * @param groupName 分组名称
     * @return 返回查询到的id值
     */
    @Override
    public int getGroupIdByGroupName(String groupName) {
        int groupId = 0 ;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM friend_group WHERE group_name=?";
            stm = con.prepareStatement(sql);
           stm.setString(1, groupName);
            rs = stm.executeQuery();
            if (rs.next())
                groupId = rs.getInt("group_id");
        } catch (SQLException throwables) {
            System.out.println("friendOperation---getGroupIdByGroupName--数据库连接异常");
            throwables.printStackTrace();
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return groupId;
    }

    /**
     *  根据用户id查询好友分组组名的集合
     * @param userId 用户id
     * @return 返回查询到的记录集合
     */
    @Override
    public List<String> friendGroupNameQuery(int userId) {
        List<String> groupNames = new ArrayList<>();
        String groupName;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM friend INNER JOIN friend_group ON friend.group_id=friend_group.group_id AND friend.from_user_id=? AND friend.status=2";
            stm = con.prepareStatement(sql);
            stm.setInt(1,userId);
            rs = stm.executeQuery();
            while (rs.next()){
                int ret = 0;
                groupName = rs.getString("friend_group.group_name");
                //循环过滤重复的分组名
                for(String gn : groupNames){
                    if (groupName.equals(gn))
                        ret = 1;
                }
                //不重复的情况
                if (ret ==0)
                    groupNames.add(groupName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("FriendDao---friendGroupNameQuery--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stm,rs);
        }
        return groupNames;
    }
}

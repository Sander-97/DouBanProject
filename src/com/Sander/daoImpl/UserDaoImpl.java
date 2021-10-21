package com.Sander.daoImpl;

import com.Sander.bean.Msg;
import com.Sander.dao.UserDao;
import com.Sander.pojo.User;
import com.Sander.util.JDBCUtil;
import com.Sander.util.JdbcPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @ClassName UserDaoImpl
 * @Direction: 与用户登录注册相关的操作
 * @Author: Sander
 * @Date 2021/9/23 10:41
 * @Version 1.0
 **/
public class UserDaoImpl implements UserDao {
    public UserDaoImpl() {
        super();
    }

private Connection con;

private PreparedStatement stmt;

private ResultSet rs;
//新建一个数据库连接池对象
JdbcPool pool = new JdbcPool();

/**
* 给字符串常量定义成一个可注释的常量
 */
static final String USERNAME = "username";
static final String PASSWORD = "password";
static final String STATUS = "status";

    /**
     *  用户登录
     * @param user 用户对象
     * @return 返回msg对象，把信息存储在对象中进行不同类之间的传输
     */
    @Override
    public Msg login(User user) {
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            rs = stmt.executeQuery();
            if (rs.next()){
                //检查数据库中是否存在该用户和密码
                if (user.getPassWord().equals(rs.getString(PASSWORD))&&user.getUserName().equals(rs.getString(USERNAME))){
                    //如果数据库中存在该用户，并且用户名和密码都相同，就给user对象设置username和password的属性值
                    user.setUserName(rs.getString(USERNAME));
                    user.setUserName(rs.getString(PASSWORD));
                    //判断是否为普通用户
                    if (rs.getInt(STATUS) == 0){
                        return new Msg("用户登录成功",user);
                    }else {
                        return new Msg("管理员登录成功",user);
                    }
                }
                //如果密码输入错误，就返回密码错误的提示
                if (!user.getPassWord().equals(rs.getString("password"))){
                    return new Msg("密码错误",null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("userDao---login 数据库连接异常");
        }finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return new Msg("该用户不存在",null);
    }

    /**
     *   用户注册，默认的为普通用户
     * @param user 用户对象
     * @return 返回1表示注册成功
     *          返回0表示注册失败
     */
    @Override
    public int register(User user) {
        int judge = 0;
        try {
            con = pool.getConnection();
            String sql = "insert into user (username,password,status) values (?,?,0)";
            PreparedStatement pmt = con.prepareStatement(sql);
            pmt.setString(1, user.getUserName());
            pmt.setString(2, user.getPassWord());
            int rs = pmt.executeUpdate();
            if (rs>0){
                System.out.println("用户添加成功");
            }
            judge=1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加用户失败");
            judge=0;
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return judge;
    }

    /**
     *   查看用户名是否重复
     * @param user  用户对象
     * @return 如果返回1表示重复
     *             返回0表示不重复
     */
    @Override
    public int repetition(User user) {
        int judge = 0;
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM user WHERE username=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            rs = stmt.executeQuery();
           if (rs.next()){
               judge=1;
           }
        } catch (SQLException e) {
            e.printStackTrace();
            judge=0;
            System.out.println("repetition" + "数据库连接失败");
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return judge;
    }

    /**
     *  更新user表中的个人信息，传入session中的该用户user对象记录
     * @param user 通过user对象的用户名来查询更新
     * @return 返回1表示更新成功
     *             0表示更新失败
     */
    @Override
    public int personAge(User user) {
        int judge = 0;
        try {
            con = pool.getConnection();
            //更新user表的个人信息字段
            String sql = "UPDATE user SET signature=?,self_introduction=?,nickname=?,address=? WHERE username=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getSignature());
            stmt.setString(2, user.getSelfIntroduction());
            stmt.setString(3, user.getNickName());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getUserName());
            int rs = stmt.executeUpdate();
            if (rs==1){
                judge=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            judge=0;
            System.out.println("数据库连接失败");
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return judge;
    }

    /**
     *  更新数据库中的用户头像地址
     * @param user 用户对象
     * @return 返回1表示更新成功
     *             0表示更新失败
     */
    @Override
    public int portrait(User user) {
        int judge=0;
        try {
            con = pool.getConnection();
            String sql = "UPDATE user SET portrait=? WHERE username=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getPortrait());
            stmt.setString(2, user.getUserName());
            System.out.println("DAO----------portrait-----"+user.getPortrait());
            //进行更新操作，返回成功更新的结果集条数
            int rs = stmt.executeUpdate();
            if (rs==1){
                judge = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("portrait" + "数据库连接失败");
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return judge;
    }

    /**
     *   通过用户名查询用户的信息
     * @param userName 用户名
     * @return user对象
     */
    @Override
    public User userInfoByUserName(String userName) {
        User user = new User();
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM user WHERE username=?";

            //查询用户信息
            stmt = con.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()){
                //用户id和用户名
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));

                //用户的个人账号状态
                user.setPassWord(rs.getString("password"));
                user.setReported(rs.getInt("reported"));
                user.setStatus(rs.getInt("status"));

                //用户的个人基本信息
                user.setPortrait(rs.getString("portrait"));
                user.setAddress(rs.getString("address"));
                user.setNickName(rs.getString("nickname"));
                user.setSelfIntroduction(rs.getString("self_introduction"));
                user.setSignature(rs.getString("signature"));

                //用户的注册时间
                user.setTime(rs.getTimestamp("time"));

                user.setCode(rs.getInt("code"));
                user.setOutTime(rs.getTimestamp("out_time"));
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("userdao---userinfo--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return user;
    }

    /**
     * 根据用户id查询用户的所有信息
     * @param userId 用户的id
     * @return user对象
     */
    @Override
    public User userInfoByUserId(int userId) {
        User user = new User();
        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM user WHERE user_id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()){
                //用户id和用户名
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));

                //用户的个人账号状态
                user.setPassWord(rs.getString("password"));
                user.setReported(rs.getInt("reported"));
                user.setStatus(rs.getInt("status"));

                //被封号的截止时间
                user.setTitleTime(rs.getTimestamp("title_time"));

                //用户的个人基本信息
                user.setPortrait(rs.getString("portrait"));
                user.setAddress(rs.getString("address"));
                user.setNickName(rs.getString("nickname"));
                user.setSelfIntroduction(rs.getString("self_introduction"));
                user.setSignature(rs.getString("signature"));

                //用户的注册时间
                user.setTime(rs.getTimestamp("time"));

            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("userdao---userinfoByUserId--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return user;
    }

    @Override
    public boolean userUpdate(User user) {
        boolean judge = false;
        try {
            con = pool.getConnection();
            String sql = "UPDATE user SET code = ?,out_time=? WHERE username=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,user.getCode());
            stmt.setTimestamp(2, user.getOutTime());
            stmt.setString(3, user.getUserName());

            int rs = stmt.executeUpdate();
            if (rs==1) {
                judge = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("userdao---userUpdate--数据库连接异常");
        } finally {
            JDBCUtil.release(con,stmt,rs);
        }
        return judge;
    }
}

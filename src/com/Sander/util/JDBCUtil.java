package com.Sander.util;

/**
 * @ClassName JDBCUtil
 * @Direction: 数据库的连接和关闭工具
 * @Author: Sander
 * @Date 2021/9/23 10:59
 * @Version 1.0
 **/

import java.sql.*;


/**
 *  连接数据库的具体操作
 *  相当于一个连接和关闭数据库的工具
 *  为了减少代码的冗余
 *  存放一些数据库的配置文件
 */

public class JDBCUtil {

    //数据库连接池
    private static JdbcPool pool = new JdbcPool();



    /**
     * @Description: 从数据库连接池中获取数据库连接对象
     * @Param  []
     * @return java.sql.Connection
     * @Author Sander
     * @Date 14:00 2021/10/20
    */
    public static Connection getConnection() throws SQLException{
        return pool.getConnection();
    }

    public static void release(Connection connection,Statement stm,ResultSet rs){
        if (rs!=null){
            try {
                rs.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            rs = null;
        }

        if (stm != null){
            try {
                stm.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            stm = null;
        }

        if (connection != null){
            try {
                connection.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            connection = null;
        }
    }

}

package com.Sander.util;

import jdk.jshell.spi.ExecutionControl;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @ClassName JdbcPool
 * @Direction: 编写数据库连接池
 * @Author: Sander
 * @Date 2021/10/20 13:32
 * @Version 1.0
 **/
public class JdbcPool implements DataSource {
    /**
     * 因为需要频繁读写list集合所以使用LinkedList存储数据比较合适
     */
    private static LinkedList<Connection> listConnections = new LinkedList<Connection>();


    static {
        //在静态代码块中加载db.properties数据库配置文件

        InputStream inputStream = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
        Properties prop = new Properties();

        try {
            prop.load(inputStream);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            //数据库连接池的初始化连接数大小
            int jdbcPoolInitSize = Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));

            //加载数据驱动
            Class.forName(driver);
            for (int i = 0; i < jdbcPoolInitSize; i++) {
                Connection connection = DriverManager.getConnection(url,username,password);
                System.out.println("获取到了链接" + connection);

                //把获取到的数据库连接加入到listConnections集合中，listConnections集合此时就是一个存放了数据库连接的连接池

                listConnections.add(connection);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    /**
     * 获取数据库连接
     * @return javax.sql.DataSource#getConnection()
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        //如果数据库连接池中的连接对象的个数大于0
        if (listConnections.size()>0){
            //从listConnections集合中获取一个数据库连接
            final Connection connection = listConnections.removeFirst();
            System.out.println("listConnections的数据库连接池大小是" + listConnections.size());

            //返回Connection对象的代理对象
            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), connection.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (!method.getName().equals("close")){
                        return method.invoke(connection, args);
                    }else {
                        //如果调用的是Connection对象的close方法，就把connection还给数据库连接池
                        listConnections.add(connection);
                        System.out.println(connection + "被还给listConnections数据库连接池了！！");
                        System.out.println("listConnections数据库连接池大小是" + listConnections.size());
                        return null;
                    }
                }
            });

        }else {
            throw new RuntimeException("对比起，数据库正忙");
        }

    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}

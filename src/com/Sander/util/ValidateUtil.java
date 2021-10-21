package com.Sander.util;

/**
 * @ClassName ValidateUtil
 * @Direction: 验证用户名和密码是否为空，如果是空的就返回true，否则就返回false
 * @Author: Sander
 * @Date 2021/9/27 11:12
 * @Version 1.0
 **/
public class ValidateUtil {
    /**
     *  判断用户名是否为空
     * @param userName
     * @return 如果存在就返回true，不存在就返回false
     */
    public static boolean isInvalidUserName(String userName){
        if (userName == null || "".equals(userName))
        {
            System.out.println("用户名为空，不合理");
            return true;
        }

        return false;
    }

    /***
     * 判断密码是否为空
     * @param password
     * @return 是空的就返回true，否则就返回false
     */
    public static boolean isInvalidPassword(String password){
        if (password == null || "".equals(password))
        {
            System.out.println("密码为空，不合理");
            return true;
        }

        return false;
    }
}

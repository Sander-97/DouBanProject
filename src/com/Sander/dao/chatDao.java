package com.Sander.dao;

import com.Sander.pojo.chat;

import java.util.List;

/**
 * @InterfaceName chatDao
 * @Direction: 用户聊天的数据库操作
 * @Author: Sander
 * @Date 2021/9/16 8:59
 * @Version 1.0
 **/
public interface chatDao {
    /**
     * 获取记录的总数
     * @param userId 用户id
     * @return 记录总数
     */
    public int getTotalCount(int userId);

    /***
     * 通过用户定义的页面数和页面大小进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return 只含有chat 表的数据集合
     */
    public List<chat> queryChatByPage(int currentPage, int pageSize ,int userId);

    /**
     * 找到符合发送者和接收者id的记录
     * @param userId 发送人id
     * @param toUserId 接收人id
     * @return 符合条件的记录总数
     */
    public int getShowTotalCount(int userId, int toUserId);

    /**
     * 通过用户定义的页面数和页面大小，进行分页查询--找到符合发送者和接受者的记录
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 发送人用户id
     * @param toUserId  接收人用户id
     * @return 返回符合条件的chat数据集合
     */
    public List<chat> queryShowByPage(int currentPage, int pageSize ,int userId , int toUserId);

    /**
     *  新建一个聊天
     * @param chat chat对象
     * @return 操作成功与否
     */
    public boolean chatInsert(chat chat);

}

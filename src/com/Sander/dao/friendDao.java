package com.Sander.dao;

import com.Sander.pojo.User;

import java.util.List;

/**
 * @ClassName friendDao
 * @Direction: 处理好友相关功能
 * @Author: Sander
 * @Date 2021/9/16 9:00
 * @Version 1.0
 **/
public interface friendDao {
    /**
     * 查询列表的记录总数
     * @return 返回记录总数
     */
    public int getTotalCount();

    /**
     *  通过用户定义的页面数和页面大小，进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 当前页的对象集合---users
     */
    public List<User> queryEveryoneByPage(int currentPage, int pageSize);

    /**
     *  传入要模糊搜索的内容，进行模糊搜索，找出与其相符合的记录条数
     * @param searchContent 要模糊搜索的内容
     * @return 相匹配的记录数
     */
    public int getSearchCount(String searchContent);

    /**
     *  模糊搜索，通过用户定义的页面大小和页面数进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 要搜索的内容
     * @return 当前页的对象集合--users
     */
    public List<User> queryEveryoneByPage(int currentPage, int pageSize,String searchContent);

    /**
     *  关注的人或者好友或者黑名单的记录总数
     * @param userId 当前用户的id
     * @param status 查询条件----1.关注-----2.好友-----3.黑名单
     * @retur 记录总数
     */
    public int getFriendTotalCount(int userId, int status);

    /**
     *   我的关注--好友--黑名单的分页查询
     * @param userId  当前用户的id
     * @param status  查询的条件1.关注-----2.好友-----3.黑名单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return  当前页的对象集合
     */
    public List<User> queryFriendByPage(int userId, int status ,int currentPage, int pageSize);

    /**
     *   从我的关注--好友--黑名单 中找出符合条件的记录数
     * @param searchContent 搜索的内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @return 返回查询的记录总数
     */
    public int getFriendSearchCount(String searchContent,int userId, int status);

    /**
     *  我的关注--好友--黑名单中进行分页查询
     * @param searchContent 搜索内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回当前页的对象集合
     */
    public List<User> queryFriendByPage(String searchContent,int userId, int status,int  currentPage, int pageSize);

    /**
     * 找出其中符合条件的记录数 friend_group表和friend表
     * @param searchContent 搜索的内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @return  记录总数
     */
    public int getFriendGroupCount(String searchContent, int userId,int status);

    /**
     *  分页查询
     * @param SearchContent 搜索内容
     * @param userId 用户id
     * @param status 查询的条件1.关注-----2.好友-----3.黑名单
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 当前页的对象集合
     */
    public List<User> queryFriendGroupByPage(String SearchContent ,int userId, int status,int currentPage,int pageSize);
}

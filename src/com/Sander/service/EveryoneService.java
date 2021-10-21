package com.Sander.service;

import com.Sander.dao.friendDao;
import com.Sander.daoImpl.friendDaoImpl;
import com.Sander.pojo.User;

import java.util.List;

/**
 * @ClassName EveryoneService
 * @Direction: 负责调用和用户好友功能相关的DAO操作
 * @Author: Sander
 * @Date 2021/9/27 10:39
 * @Version 1.0
 **/
public class EveryoneService {
    private friendDao friendDao = new friendDaoImpl();

    /**
     *  查询数据的总数
     * @return 返回查询到的数据
     */
    public int getTotalCount(){
        return friendDao.getTotalCount();
    }

    /**
     * 分页查询所有用户信息
     * @param currentPage 当前页
     * @param pageSize  页面大小
     * @return 用户集合
     */
    public List<User> queryEveryoneByPage(int currentPage , int pageSize){
        return friendDao.queryEveryoneByPage(currentPage, pageSize);
    }

    /**
     * 查询符合条件数据的总条数
     * @param searchContent 模糊搜索的内容
     * @return 返回搜索到的数据条数
     */
    public int getSearchCount(String searchContent){
        return friendDao.getSearchCount(searchContent);

    }

    /**
     * 通过模糊搜索的内容进行页面查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 搜索内容
     * @return 返回查询到的用户集合
     */
    public List<User> queryEveryoneByPageAndSearchContent(int currentPage , int pageSize ,String searchContent){
        return friendDao.queryEveryoneByPage(currentPage, pageSize, searchContent);
    }

    /**
     * 根据本人的用户id和好友关系状态获取好友的总数
     * @param userId 用户id
     * @param status 好友关系状态 1--关注 2---好友
     * @return 返回查询到的数据总数
     */
    public int getFriendTotalCount(int userId , int status){
        return friendDao.getFriendTotalCount(userId, status);
    }

    /**
     * 通过用户本人id和好友关系状态进行分页查询
     * @param userId 用户id
     * @param status 好友关系状态 1--关注 2---好友
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 查询到的用户集合
     */
    public List<User> queryFriendByPage(int userId , int status ,int currentPage ,int pageSize){
        return friendDao.queryFriendByPage(userId, status, currentPage, pageSize);
    }

    /**
     *  通过用户本人id和好友关系状态以及搜索的内容进行分页查询
     * @param searchContent 搜索的内容
     * @param userId 用户本人id
     * @param status 好友关系状态 1--关注 2---好友
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回用户集合
     */
    public List<User> queryFriendByPageAndSearchContent( String searchContent , int userId , int status , int currentPage , int pageSize){
        return friendDao.queryFriendByPage(searchContent, userId, status, currentPage, pageSize);
    }

    /**
     *  查询关于搜索内容的好友数据总数
     * @param userId 用户本人id
     * @param status 好友关系状态 1--关注 2---好友
     * @param searchContent 搜索的内容
     * @return 返回搜索的数据大小
     */
    public int getFriendTotalCount(int userId , int status , String searchContent){
        return friendDao.getFriendGroupCount(searchContent, userId, status);
    }

    /**
     *  查询所有用户的相关信息
     * @param searchContent 搜索的内容
     * @param userId 当前用户id
     * @param status 好友关系状态 1--关注 2---好友
     * @param currentPage 当前页
     * @param pageSize  页面大小
     * @return 返回查询到的用户 集合
     */
    public List<User> queryFriendGroupByPage(String searchContent , int userId , int status , int currentPage , int pageSize){
        return friendDao.queryFriendGroupByPage(searchContent,userId,status, currentPage, pageSize);
    }

}

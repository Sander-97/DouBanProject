package com.Sander.dao;

import java.util.List;

/**
 * @ClassName friendOperationDao
 * @Direction: 用户更改好友关系的DAO操作
 * @Author: Sander
 * @Date 2021/9/16 9:00
 * @Version 1.0
 **/
public interface friendOperationDao {
    /**
     * 更新当前用户的好友关系，把好友关系更改为当前用户选择的好友关系
     * @param fromUserId friend表中记录的发出者
     * @param toUserId 用户选择的好友
     * @param status 更改后的好友状态
     * @return 返回是否成功，1是成功----0是失败
     */
    public boolean friendUpdate(int fromUserId , int toUserId , int status);

    /***
     *  删除好友的操作，让好友关系变为无状态
     * @param fromUserId 记录的发出者
     * @param toUserId 记录的执行人
     * @return 操作是否成功
     */
    public boolean friendDelete(int fromUserId, int toUserId);

    /**
     *增加一条好友关系，从双方关系为无状态，到一方关注另外一方
     * @param fromUserId 记录的发出人
     * @param toUserId 记录的接收人
     * @return 返回操作是否成功
     */
    public boolean friendInsert(int fromUserId, int toUserId);

    /**
     *  查询在好友分组表中双方的好友关系
     * @param fromUserId 进行查询的用户的id
     * @param toUserId 被查询的用户id
     * @return 返回双方的关系，0位无状态信息
     */
    public int friendQuery(int fromUserId, int toUserId);

    /**
     *   查询该好友分组是否存在
     * @param groupName 好友分组名称
     * @return 是否成功
     */
    public boolean friendGroupIsExist(String groupName);

    /**
     *  新增一个好友分组
     * @param groupName 分组名
     * @return  是否成功
     */
    public boolean friendGroupInsert(String groupName);

    /**
     *  好友分组更新
     * @param fromUserId 好友关系发出用户的id
     * @param toUserId 好友关系接收人的id
     * @param status 好友关系状态
     * @param groupId 好友分组id
     * @return 成功与否
     */
    public boolean friendGroupUpdate(int fromUserId , int toUserId , int status , int groupId) ;

    /**
     *  通过好友分组名称获取好友的分组id
     * @param groupName 分组名称
     * @return 分组的id
     */
    public int getGroupIdByGroupName(String groupName);

    /**
     * 通过好友id查询该用户好友分组名的数据集合
     * @param userId 用户id
     * @return 该用户好友分组名称的数据集合
     */
    public List<String> friendGroupNameQuery(int userId);
}

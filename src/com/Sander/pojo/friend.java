package com.Sander.pojo;

/**
 * @ClassName friend
 * @Direction: 好友关系表的对应类
 * @Author: Sander
 * @Date 2021/9/15 20:14
 * @Version 1.0
 **/
public class friend {
    /**
     * @Description 好友关系分组的id
     *
    */
    private int friendId;
    /**
     * @Description  进行好友关系操作的id
     *
    */
    private int  fromUserId;
    /**
     * @Description 被进行好友关系操作的用户id
     *
    */
    private int toUserId;
    /**
     * @Description  好友关系状态
     *
    */
    private int status;
    /**
     * @Description  好友分组id
     *
    */
    private int groupId;

    public friend() {
        //TODO Auto-generated constructor stub--
        super();
    }

    /**
     *
     * @param friendId
     * @param fromUserId
     * @param toUserId
     * @param status
     * @param groupId
     */
    public friend(int friendId, int fromUserId, int toUserId, int status, int groupId) {
        super();
        this.friendId = friendId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.groupId = groupId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}

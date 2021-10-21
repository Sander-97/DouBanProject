package com.Sander.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @ClassName chat
 * @Direction: 用户聊天交流数据信息类
 * @Author: Sander
 * @Date 2021/9/15 19:43
 * @Version 1.0
 **/
public class chat {

    /**
     * @Description  聊天表的id
     *
    */
    private int chatId;
    /**
     * @Description 发出聊天消息的人的id
     *
    */
    private int fromUserId;
    /**
     * @Description 发出聊天消息的人的头像
     *
    */
    private String fromUserImg;
    /**
     * @Description 发出聊天消息的人的昵称
     *
    */
    private String fromUserNickName;
    /**
     * @Description 接收消息的人的id
     *
    */
    private int toUserId;
    /**
     * @Description 接收消息的人的头像
     *
    */
    private String toUserImg;
    /**
     * @Description 接收消息的人的昵称
     *
    */
    private String toUserNickName;

    /**
     * @Description 聊天信息
     *
    */
    private String chatMessage;
    /**
     * @Description 聊天的时间
     *
    */
    private java.sql.Timestamp chatTime;
    /**
     * @Description 记录当前记录的删除情况---
     *               0为无状态
     *               1是发送豆邮的人进行了删除，
     *               2是接收豆邮的人进行了删除
     *
     *
    */
    private int status;
    /**
     * @Description 记录当前的豆邮是否已被阅读
     *
    */
    private int read;

    public chat() {
        super();
    }

    /**
     *
     * @param chatId
     * @param fromUserId
     * @param fromUserImg
     * @param fromUserNickName
     * @param toUserId
     * @param toUserImg
     * @param toUserNickName
     * @param chatMessage
     * @param chatTime
     * @param status
     * @param read
     */
    public chat(int chatId, int fromUserId, String fromUserImg,
                String fromUserNickName, int toUserId, String toUserImg,
                String toUserNickName, String chatMessage, Timestamp chatTime,
                int status, int read)
    {
        this.chatId = chatId;
        this.fromUserId = fromUserId;
        this.fromUserImg = fromUserImg;
        this.fromUserNickName = fromUserNickName;
        this.toUserId = toUserId;
        this.toUserImg = toUserImg;
        this.toUserNickName = toUserNickName;
        this.chatMessage = chatMessage;
        this.chatTime = chatTime;
        this.status = status;
        this.read = read;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserImg() {
        return fromUserImg;
    }

    public void setFromUserImg(String fromUserImg) {
        this.fromUserImg = fromUserImg;
    }

    public String getFromUserNickName() {
        return fromUserNickName;
    }

    public void setFromUserNickName(String fromUserNickName) {
        this.fromUserNickName = fromUserNickName;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserImg() {
        return toUserImg;
    }

    public void setToUserImg(String toUserImg) {
        this.toUserImg = toUserImg;
    }

    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatTime() {
        //更改其时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  df.format(chatTime);
    }

    public void setChatTime(Timestamp chatTime) {
        this.chatTime = chatTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}

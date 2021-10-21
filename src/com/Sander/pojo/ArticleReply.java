package com.Sander.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @ClassName ArticleReply
 * @Direction: 文章回复的相关数据内容，对应数据库中的a_reply表
 * @Author: Sander
 * @Date 2021/9/15 15:16
 * @Version 1.0
 **/
public class ArticleReply {

    /**
     * @Description 回复表的id
     *
    */
    private int replyId;
    /**
     * @Description 评论表的id
     *
    */
    private int commentId;
    /**
     * @Description 回复的内容
     *
    */
    private String replyMessage;
    /**
     * @Description 进行回复用户的id
     *
    */
    private int userReplyFromId;
    /**
     * @Description 被回复用户的id
     *
    */
    private int userReplyToId;
    /**
     * @Description 进行回复的用户的头像
    */
    private String userReplyImg;
    /**
     * @Description 进行回复的用户的昵称
     *
    */
    private String userReplyFromNickName;
    /**
     * @Description 被回复的用户的昵称
     *
    */
    private String userReplyToNickName;
    /**
     * @Description 回复的点赞数
     *
    */
    private int replyStarNum;
    /**
     * @Description 回复的时间
     *
    */
    private java.sql.Timestamp replyTime;
/**
 * @Description
 *
*/
    public ArticleReply() {
        super();
    }

    /**
     *
     * @param replyId
     * @param commentId
     * @param replyMessage
     * @param userReplyFromId
     * @param userReplyToId
     * @param userReplyImg
     * @param userReplyFromNickName
     * @param userReplyToNickName
     * @param replyStarNum
     * @param replyTime
     */
    public ArticleReply(int replyId, int commentId, String replyMessage,
                        int userReplyFromId, int userReplyToId,
                        String userReplyImg, String userReplyFromNickName,
                        String userReplyToNickName, int replyStarNum, Timestamp replyTime)
    {   super();
        this.replyId = replyId;
        this.commentId = commentId;
        this.replyMessage = replyMessage;
        this.userReplyFromId = userReplyFromId;
        this.userReplyToId = userReplyToId;
        this.userReplyImg = userReplyImg;
        this.userReplyFromNickName = userReplyFromNickName;
        this.userReplyToNickName = userReplyToNickName;
        this.replyStarNum = replyStarNum;
        this.replyTime = replyTime;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public int getUserReplyFromId() {
        return userReplyFromId;
    }

    public void setUserReplyFromId(int userReplyFromId) {
        this.userReplyFromId = userReplyFromId;
    }

    public int getUserReplyToId() {
        return userReplyToId;
    }

    public void setUserReplyToId(int userReplyToId) {
        this.userReplyToId = userReplyToId;
    }

    public String getUserReplyImg() {
        return userReplyImg;
    }

    public void setUserReplyImg(String userReplyImg) {
        this.userReplyImg = userReplyImg;
    }

    public String getUserReplyFromNickName() {
        return userReplyFromNickName;
    }

    public void setUserReplyFromNickName(String userReplyFromNickName) {
        this.userReplyFromNickName = userReplyFromNickName;
    }

    public String getUserReplyToNickName() {
        return userReplyToNickName;
    }

    public void setUserReplyToNickName(String userReplyToNickName) {
        this.userReplyToNickName = userReplyToNickName;
    }

    public int getReplyStarNum() {
        return replyStarNum;
    }

    public void setReplyStarNum(int replyStarNum) {
        this.replyStarNum = replyStarNum;
    }

    public String getReplyTime() {
        //更改其时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  df.format(replyTime);
    }

    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
    }
}

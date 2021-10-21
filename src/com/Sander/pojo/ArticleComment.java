package com.Sander.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @ClassName ArticleComment
 * @Direction: 对于文章评论相关数据，对应数据库中的a_comment表
 * @Author: Sander
 * @Date 2021/9/15 15:00
 * @Version 1.0
 **/
public class ArticleComment {

    /**
     * @Description 评论表的id
     *
    */
    private int commentId;
    /**
     * @Description 被评论文章的id
     *
    */
    private int articleId;
    /**
     * @Description 评论内容
     *
    */
    private String commentMessage;
    /**
     * @Description 进行评论的用户id
     *
    */
    private int userCommentId;
    /**
     * @Description 进行评论的用户头像
     *
    */
    private String userCommentImg;
    /**
     * @Description 进行评论的用户昵称
     *
    */
    private String userCommentNickName;
    /**
     * @Description 评论的点赞数
     *
    */
    private int commentStarNum;
    /**
     * @Description 评论的时间
     *
    */
    private java.sql.Timestamp commentTime;
    /**
     * @Description 记录当前用户对该评论是否为点赞状态
     * 1----是点赞
     * 0----是没有
     *
    */
    private int starStatus = 0;
/**
 * @Description
 *
*/
    public ArticleComment() {
        super();
    }

    /**
     *
     * @param commentId
     * @param articleId
     * @param commentMessage
     * @param userCommentId
     * @param userCommentImg
     * @param userCommentNickName
     * @param commentStarNum
     * @param commentTime
     * @param starStatus
     */
    public ArticleComment(int commentId, int articleId, String commentMessage,
                          int userCommentId, String userCommentImg, String userCommentNickName,
                          int commentStarNum, Timestamp commentTime, int starStatus)
    {
        super();
        this.commentId = commentId;
        this.articleId = articleId;
        this.commentMessage = commentMessage;
        this.userCommentId = userCommentId;
        this.userCommentImg = userCommentImg;
        this.userCommentNickName = userCommentNickName;
        this.commentStarNum = commentStarNum;
        this.commentTime = commentTime;
        this.starStatus = starStatus;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public int getUserCommentId() {
        return userCommentId;
    }

    public void setUserCommentId(int userCommentId) {
        this.userCommentId = userCommentId;
    }

    public String getUserCommentImg() {
        return userCommentImg;
    }

    public void setUserCommentImg(String userCommentImg) {
        this.userCommentImg = userCommentImg;
    }

    public String getUserCommentNickName() {
        return userCommentNickName;
    }

    public void setUserCommentNickName(String userCommentNickName) {
        this.userCommentNickName = userCommentNickName;
    }

    public int getCommentStarNum() {
        return commentStarNum;
    }

    public void setCommentStarNum(int commentStarNum) {
        this.commentStarNum = commentStarNum;
    }

    public String getCommentTime() {
        //更改其时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  df.format(commentTime);
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public int getStarStatus() {
        return starStatus;
    }

    public void setStarStatus(int starStatus) {
        this.starStatus = starStatus;
    }
}

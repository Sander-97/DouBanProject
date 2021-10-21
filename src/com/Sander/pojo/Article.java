package com.Sander.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @ClassName Article
 * @Direction: article表的实体类 ---对应文章编辑界面的文章主体部分--以及文章主体的显示功能
 * @Author: Sander
 * @Date 2021/9/15 14:46
 * @Version 1.0
 **/
public class Article {
/**
 * @Description 文章id
 *
*/
    private int articleId;
    /**
     * @Description 文章标题
     *
    */
    private String title;
    /**
     * @Description 文章分类名
     *
    */
    private String tagName;
    /**
     * @Description 文章作者id
     *
    */
    private int authorId;
    /**
     * @Description 文章作者昵称
     *
    */
    private String authorNickName;
    /**
     * @Description 作者的头像
     *
    */
    private String authorImg;
    /**
     * @Description 文章发表时间
     *
    */
    private java.sql.Timestamp publishedTime;
    /**
     * @Description 分类id
     *
    */
    private int tagId;
    /**
     * @Description 主要的文章内容HTML代码
     *
    */
    private String  content;
    /**
     * @Description 点赞数
     *
    */
    private int starNum;
    /**
     * @Description 收藏数
     *
    */
    private int collectionNum;
    /**
     * @Description 评论数
     *
    */
    private int commentNum;
    /**
     * @Description 转发量
     *
    */
    private int sharNum;
    /**
     * @Description 浏览量
     *
    */
    private int pageView;
    /**
     * @Description 是否置顶
     *
    */
    private int stick;

    /**
     * @Description
     *
    */
    public Article() {
        super();
    }

    /**
     *
     * @param articleId
     * @param title
     * @param tagName
     * @param authorId
     * @param authorNickName
     * @param authorImg
     * @param publishedTime
     * @param tagId
     * @param content
     * @param starNum
     * @param collectionNum
     * @param commentNum
     * @param sharNum
     * @param pageView
     * @param stick
     */
    public Article(int articleId, String title, String tagName, int authorId,
                   String authorNickName, String authorImg, Timestamp publishedTime,
                   int tagId, String content, int starNum, int collectionNum,
                   int commentNum, int sharNum, int pageView, int stick)
    {
        super();
        this.articleId = articleId;
        this.title = title;
        this.tagName = tagName;
        this.authorId = authorId;
        this.authorNickName = authorNickName;
        this.authorImg = authorImg;
        this.publishedTime = publishedTime;
        this.tagId = tagId;
        this.content = content;
        this.starNum = starNum;
        this.collectionNum = collectionNum;
        this.commentNum = commentNum;
        this.sharNum = sharNum;
        this.pageView = pageView;
        this.stick = stick;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getPublishedTime() {
        //更改其时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  df.format(publishedTime);
    }

    public void setPublishedTime(Timestamp publishedTime) {
        this.publishedTime = publishedTime;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getSharNum() {
        return sharNum;
    }

    public void setSharNum(int sharNum) {
        this.sharNum = sharNum;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }

    public int getStick() {
        return stick;
    }

    public void setStick(int stick) {
        this.stick = stick;
    }
}

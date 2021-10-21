package com.Sander.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @ClassName ArticleList
 * @Direction: article_list.jsp中列表中每行所需元素 预览文章信息的列表 相当于浏览发现
 * @Author: Sander
 * @Date 2021/9/15 15:27
 * @Version 1.0
 **/
public class ArticleList {
    /**
     * @Description 文章的id
     *
    */
    private int articleId;
    /**
     * @Description 文章的标题
     *
    */
    private String title;
    /**
     * @Description 发布的时间
     *
    */
    private java.sql.Timestamp publishedTime;
    /**
     * @Description 文章的分类名
     *
    */
    private String tagName;
    /**
     * @Description 作者id
     *
    */
    private int authorId;
    /**
     * @Description 作者头像
     *
    */
    private String authorImg;
    /**
     * @Description 作者昵称
     *
    */
    private String authorNickName;
    /**
     * @Description 转发量
     *
    */
    private int shareNum;
    /**
     * @Description 评论数
     *
    */
    private int commentNum;
    /**
     * @Description 点赞数
     *
    */
    private  int starNum;
    /**
     * @Description 收藏数
     *
    */
    private int collectionNum;
    /**
     * @Description 预览文字--整份文章内容
     *
    */
    private String content;
    /**
     * @Description 文章的第一张图片
     *
    */
    private String articleFirstImg;
    /**
     * @Description 是否置顶
     *
    */
    private int stick;
    /**
     * @Description 浏览量
     *
    */
    private int pageViewer;
/**
 * @Description
 *
*/
    public ArticleList() {
        super();
    }

    /**
     *
     * @param articleId
     * @param title
     * @param publishedTime
     * @param tagName
     * @param authorId
     * @param authorImg
     * @param authorNickName
     * @param shareNum
     * @param commentNum
     * @param starNum
     * @param collectionNum
     * @param content
     * @param articleFirstImg
     * @param stick
     * @param pageViewer
     */
    public ArticleList(int articleId, String title, Timestamp publishedTime,
                       String tagName, int authorId, String authorImg,
                       String authorNickName, int shareNum, int commentNum,
                       int starNum, int collectionNum, String content,
                       String articleFirstImg, int stick, int pageViewer)
    {
        this.articleId = articleId;
        this.title = title;
        this.publishedTime = publishedTime;
        this.tagName = tagName;
        this.authorId = authorId;
        this.authorImg = authorImg;
        this.authorNickName = authorNickName;
        this.shareNum = shareNum;
        this.commentNum = commentNum;
        this.starNum = starNum;
        this.collectionNum = collectionNum;
        this.content = content;
        this.articleFirstImg = articleFirstImg;
        this.stick = stick;
        this.pageViewer = pageViewer;
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

    public String getPublishedTime() {
        //更改其时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  df.format(publishedTime);
    }

    public void setPublishedTime(Timestamp publishedTime) {
        this.publishedTime = publishedTime;
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

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleFirstImg() {
        return articleFirstImg;
    }

    public void setArticleFirstImg(String articleFirstImg) {
        this.articleFirstImg = articleFirstImg;
    }

    public int getStick() {
        return stick;
    }

    public void setStick(int stick) {
        this.stick = stick;
    }

    public int getPageViewer() {
        return pageViewer;
    }

    public void setPageViewer(int pageViewer) {
        this.pageViewer = pageViewer;
    }
}

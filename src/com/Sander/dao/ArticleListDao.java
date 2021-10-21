package com.Sander.dao;

import com.Sander.pojo.ArticleList;

import java.util.List;

/**
 * @InterfaceName ArticleListDao
 * @Direction: 文章列表相关的数据库操作
 * @Author: Sander
 * @Date 2021/9/16 9:02
 * @Version 1.0
 **/
public interface ArticleListDao {
    /**
     *  查询列表的文章记录总数
     * @return 返回记录总数
     */
    public int getTotalCount();

    /**
     *   通过用户定义的页面数和页面大小，进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 返回articleLIst书籍对象集合
     */
    public List<ArticleList> queryArticleListByPage(int currentPage, int pageSize );

    /**
     *  查询列表的记录总数
     * @param userId 用户id
     * @return 返回记录总数
     */
    public int getMyTotalCount(int userId);

    /**
     *   通过用户定义的页面数，页面大小和用户id进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return  返回articleLIst对象的数据集合
     */
    public List<ArticleList> queryMyArticleListByPage(int currentPage, int pageSize ,int userId);

    /**
     *   找出其中符合条件的记录数----a_article表
     * @param searchContent 模糊搜索的字段内容
     * @return 返回记录总数
     */
    public int getArticleSearchCount(String searchContent);

    /**
     *  通过用户定义的页面数，页面大小和模糊搜索内容进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 模糊搜索字段内容
     * @return  articleList对象数据集合
     */
    public List<ArticleList> queryArticleByPage(int currentPage, int pageSize , String searchContent);

    /**
     * 找出其中符合条件的记录数----a_article表
     * @param searchContent 搜索的内容
     * @param authorId 作者id
     * @return 记录条数
     */
    public int getMyArticleSearchCount(String searchContent, int authorId);

    /**
     *  通过用户定义的页面数，页面大小，作者id和搜索字段内容进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param searchContent 搜索内容
     * @param authorId 作者id
     * @return 返回articleLIst的对象集合
     */
    public List<ArticleList> queryMyArticleByPage(int currentPage, int pageSize , String searchContent,int authorId);

    /**
     *  通过用户id查询我的收藏记录总数
     * @param userId 用户id
     * @return 收藏总数
     */
    public int getMyCollectionTotalCount(int userId);

    /**
     *   通过用户定义的页面数，页面大小和模糊搜索字段内容进行分页查询
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @param userId 用户id
     * @return articleLIst对象集合
     */
    public List<ArticleList> queryMyCollectionBytPage(int currentPage, int pageSize , int userId);


}

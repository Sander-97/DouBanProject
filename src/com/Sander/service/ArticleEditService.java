package com.Sander.service;

import com.Sander.dao.ArticleDao;
import com.Sander.daoImpl.ArticleDaoImpl;
import com.Sander.pojo.Article;

/**
 * @ClassName ArticleEditService
 * @Direction: 实现文章的储存和展示的逻辑处理
 * @Author: Sander
 * @Date 2021/9/27 9:38
 * @Version 1.0
 **/
public class ArticleEditService {
    /**
     * DAO层
     */
    ArticleDao articleDao = new ArticleDaoImpl();

    /**
     * 新增一篇文章
     * @param article
     * @return 返回操作是否成功
     */
    public int articleInsert(Article article){
        return articleDao.articleInsert(article);
    }

    /**
     *  如果tagName不存在，就新增一条记录到a_tag表，然后新增一条记录到中间表
     * @param tagName
     * @param articleId
     * @return 操作成功与否
     */
    public boolean articleNewTag(String tagName, int articleId){
        boolean judge = false;
        int tagId = -1;
        tagId = articleDao.tagIsExist(tagName);
        //如果a_tag表中没有找到该标签的记录，就新建一条记录到a_tag表中
        if (tagId == -1){
            tagId = articleDao.tagInsert(tagName);
        }
        //把上面得到的结果放到文章和标签中间的表中去
        judge = articleDao.middleInsert(articleId, tagId);
        return  judge;
    }

    /**
     *  更新文章信息，同时删除中间表的记录
     * @param article
     * @return 返回操作是否成功
     */
    public boolean articleModify(Article article){
        boolean judge = false;
        judge = articleDao.articleUpdate(article);
        if (judge ==true)
            judge = articleDao.middleDelete(article.getArticleId());
        else {
            judge  = false ;
            System.out.println("文章更新出错");
        };
        return judge;
    }

    /**
     *  删除文章
     * @param articleId
     * @return 返回操作是否成功
     */
    public boolean articleDelete(int articleId){
       return articleDao.articleDelete(articleId);

    }
}

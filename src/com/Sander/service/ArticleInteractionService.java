package com.Sander.service;

import com.Sander.dao.ArticleInteractionDao;
import com.Sander.daoImpl.ArticleInteractionDaoImpl;


/**
 * @ClassName ArticleInteractionService
 * @Direction: 文章点赞收藏转发评论的逻辑处理
 * @Author: Sander
 * @Date 2021/9/27 10:38
 * @Version 1.0
 **/
public class ArticleInteractionService {
    ArticleInteractionDao articleInteractionDao = new ArticleInteractionDaoImpl();

    /**
     * 查询点赞记录是否存在
     * @param type 操作类型
     * @param typeId 操作的主键id
     * @param userId 用户id
     * @return 存在返回1，不存在返回0
     */
    public int starIsExist(int type,int typeId,int userId){
        if (articleInteractionDao.starIsExist(typeId, type, userId))
            return 1;
        else return 0;

    }

    /**
     * 新增一条点赞记录
     * @param type 操作类型
     * @param typeId 操作的主键id
     * @param userId 用户id
     * @return 返回操作成功与否
     */
    public boolean starInsert(int type,int typeId,int userId){
        //如果已经存在该点赞记录，就返回结果，不执行下一步
        if (articleInteractionDao.starIsExist(typeId, type, userId))
            return true;

        articleInteractionDao.starInsert(typeId,type,userId);
        int starNum = articleInteractionDao.starNumQuery(typeId,type);

        switch (type){
            case 1://点赞类型为文章
            {
                return articleInteractionDao.starNumUpdate(starNum, typeId);
            }
            case 2://点赞类型为评论
            {
                return articleInteractionDao.commentStarNumUpdate(starNum, typeId);
            }
            case 3://点赞类型是回复
            {
                return articleInteractionDao.replyStarNumUpdate(starNum, typeId);
            }

        }
        return false;

    }

    /**
     *  删除一条点赞记录
     * @param typeId
     * @param type
     * @param userId
     * @return
     */
    public boolean starDelete(int typeId,int type,int userId){
        //先在点赞表中删除一条点赞
        articleInteractionDao.starDelete(typeId, type,userId);

        int starNum = articleInteractionDao.starNumQuery(typeId, type);

        //把点赞数更新到相应类型的记录上
        switch (type){
            case 1://点赞类型为文章
            {
                return articleInteractionDao.starNumUpdate(starNum, typeId);
            }
            case 2://点赞类型为评论
            {
                return articleInteractionDao.commentStarNumUpdate(starNum, typeId);
            }
            case 3://点赞类型是回复
            {
                return articleInteractionDao.replyStarNumUpdate(starNum, typeId);
            }

        }
        return false;
    }

    /**
     * 查询是否存在该收藏记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回是否存在
     */
    public boolean collectionIsExist(int articleId,int userId){

        return articleInteractionDao.collectionIsExist(articleId, userId);
    }

    /**
     *  新增一条收藏记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回操作成功与否
     */
    public boolean collectionInsert(int articleId,int userId){
        //如果已经存在该收藏，就直接返回结果
        if (articleInteractionDao.collectionIsExist(articleId, userId))
            return  true;
        //先加入收藏
        articleInteractionDao.collectionInsert(articleId, userId);

        int collectionNum = articleInteractionDao.collectionNumQuery(articleId);
        //把记录的收藏数更新到文章表的收藏数上
        return articleInteractionDao.collectionNumUpdate(articleId, collectionNum);
    }

    /**
     * 删除一条收藏记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回操作是否成功
     */
    public boolean collectionDelete(int articleId,int userId){
        articleInteractionDao.collectionDelete(articleId, userId);

        int collectionNum = articleInteractionDao.collectionNumQuery(articleId);

        return articleInteractionDao.collectionNumUpdate(articleId, collectionNum);
    }

    /**
     *  查询分享是否存在
     * @param articleId 文章id
     * @param userId 用户id
     * @return 返回是否存在的布尔值
     */
    public boolean shareIsExist(int articleId, int userId){
        return articleInteractionDao.shareIsExist(articleId, userId);
    }

    public boolean shareInsert(int articleId, int userId){
        if (articleInteractionDao.shareIsExist(articleId, userId))
            return true;

        articleInteractionDao.shareInsert(articleId, userId);

        int shareNum = articleInteractionDao.shareNumQuery(articleId);

        return articleInteractionDao.shareNumUpdate(articleId, shareNum);
    }

    /**
     * 删除一条转发记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 操作是否成功
     */
    public boolean shareDelete(int articleId,int userId){
        articleInteractionDao.shareDelete(articleId, userId);

        int shareNum = articleInteractionDao.shareNumQuery(articleId);

        return articleInteractionDao.shareNumUpdate(articleId, shareNum);

    }


}

package com.Sander.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName page
 * @Direction:   分页辅助类 ---用于封装分类的属性--List当做装所有的对象集合
 * @Author: Sander
 * @Date 2021/9/15 19:23
 * @Version 1.0
 **/
public class page {

    /**
     * @Description 总页数
     *
    */
    private int totalPage;
    /**
     * @Description 每页显示的数量--页面大小
     *
    */
    private int pageSize;
    /**
     * @Description  总数量
     *
    */
    private int totalCount;
    /**
     * @Description 当前页
     *
    */
    private int currentPage;

    /**
     * @Description  当前页面数据集合
     *
    */
    private List objects = new ArrayList();

    public page() {
        //TODO
    }

    public page(int totalPage, int pageSize, int totalCount, int currentPage, List objects)
    {
        super();
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.objects = objects;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     *  当调用总数据数的set方法 和 页面大小的set方法之后，计算出总页数
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        //计算公式  --总页数=数据总数%页面大小==0？数据总数 / 页面大小 : 数据总数 / 页面大小 + 1
        this.totalPage =this.totalCount % this.pageSize == 0 ? this.totalCount /this.pageSize  :  this.totalCount/this.pageSize +1 ;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List getObjects() {
        return objects;
    }

    public void setObjects(List objects) {
        this.objects = objects;
    }
}

package com.Sander.pojo;



import java.text.SimpleDateFormat;
import java.sql.Timestamp;

/**
 * @ClassName User
 * @Direction: 和数据库表中的user表相对应，用来存放用户登录注册时的相关信息
 * @Author: Sander
 * @Date 2021/9/15 14:11
 * @Version 1.0
 **/
public class User {
   /**
    * @Description  用户ID
    *
   */
    private int userId;
    /**
     * @Description 用户名
     *
    */
    private  String userName;
    /**
     * @Description 密码
     *
    */
    private String passWord;
    /**
     * @Description 管理员权限的区分，默认为普通用户，以及黑名单
     *
    */
    private int status = 0;
    /**
     * @Description 是否被举报的状态量 0-----未被举报  1-------被举报
     *
    */
    private int reported = 0;
    /**
     * @Description 封号截止时间
     *
    */
    private java.sql.Timestamp titleTime;
    /**
     * @Description 头像-----图片地址
     *
    */
    private String portrait;
    /**
     * @Description 个性签名--最多存250个中文字符
     *
    */
    private String signature;
    /**
     * @Description 自我介绍--最多存250个中文字符
     *
    */
    private String selfIntroduction;
    /**
     * @Description 用户昵称
     *
    */
    private String nickName;
    /**
     * @Description 用户地址
     *
    */
    private String address;
    /**
     * @Description 用户注册时间
     *
    */
    private java.sql.Timestamp time ;
    /**
     * @Description 可以用来找回密码
     *
    */
    private int code ;
    /**
     * @Description 找回密码的连接，失效的时间
     *
    */
    private java.sql.Timestamp outTime ;

    /**
     *
     * @param userId
     * @param userName
     * @param passWord
     * @param status
     * @param reported
     * @param titleTime
     * @param portrait
     * @param signature
     * @param selfIntroduction
     * @param nickName
     * @param address
     * @param time
     * @param code
     * @param outTime
     */
    public User(int userId, String userName, String passWord, int status, int reported,
                Timestamp titleTime, String portrait, String signature, String selfIntroduction,
                String nickName, String address, Timestamp time, int code, Timestamp outTime)
    {
        super();
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.status = status;
        this.reported = reported;
        this.titleTime = titleTime;
        this.portrait = portrait;
        this.signature = signature;
        this.selfIntroduction = selfIntroduction;
        this.nickName = nickName;
        this.address = address;
        this.time = time;
        this.code = code;
        this.outTime = outTime;
    }

    public User() {
        super();//自动生成构造器
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassWord() { return passWord; }
    public void setPassWord(String passWord) { this.passWord = passWord; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public int getReported() { return reported; }
    public void setReported(int reported) { this.reported = reported; }
    public Timestamp getTitleTime() { return titleTime; }
    public void setTitleTime(Timestamp titleTime) { this.titleTime = titleTime; }
    public String getPortrait() { return portrait; }
    public void setPortrait(String portrait) { this.portrait = portrait; }
    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }
    public String getSelfIntroduction() { return selfIntroduction; }
    public void setSelfIntroduction(String selfIntroduction) { this.selfIntroduction = selfIntroduction; }
    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getTime()
    {   //更改时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  simpleDateFormat.format(time);
    }
    public void setTime(Timestamp time) { this.time = time; }
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public java.sql.Timestamp getOutTime() { return outTime; }
    public void setOutTime(java.sql.Timestamp outTime) { this.outTime = outTime; }}

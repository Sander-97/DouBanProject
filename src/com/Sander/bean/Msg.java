package com.Sander.bean;

/**
 * @ClassName Msg
 * @Direction: 用来操作信息的转接 ，返回值默认是该类的实例对象，结果是字符串
 * @Author: Sander
 * @Date 2021/9/16 9:08
 * @Version 1.0
 **/
public class Msg {

    /**
     * @Description 返回的值
     *
    */
    private String result;

    /**
     * @Description 封装成 object对象
     *
    */
    private Object message;

    public Msg() {
        //TODO Auto-generated constructor stub
        super();
    }

    /**
     *
     * @param result
     * @param message
     */
    public Msg(String result, Object message) {
        super();
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "result='" + result + '\'' +
                ", message=" + message +
                '}';
    }
}

package com.Sander.util;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

/**
 * @ClassName JavaMail
 * @Direction: 用于用户找回密码，邮件功能
 * @Author: Sander
 * @Date 2021/10/15 14:20
 * @Version 1.0
 **/
public class JavaMail {



    public static void main(String[] args)throws Exception{

        //邮箱协议信息封装，配置属性
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol","smtp");//协议smtp
        properties.setProperty("mail.smtp.host","smtp.qq.com");
        properties.setProperty("mail.smtp.port","465");//端口号
        properties.setProperty("mail.transport.auth","true");//需要授权
        //QQ:SSL安全验证
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallBack", "false");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        //私有化的，只能有getInstance返回session对象，通过session对象访问服务器
        Session session = Session.getInstance(properties);
        //创建邮件
        MimeMessage message = createMimeMessage(session, "2655016848@qq.com", "2655016848@qq.com", "2655016848@qq.com", "2655016848@qq.com");
        //传输对象
        Transport transport = session.getTransport();//建立连接对象
        transport.connect("2655016848@qq.com","tbludggodvkadjdf");//建立连接，其中密码以授权码的形式。
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
    }


    public static MimeMessage createMimeMessage(Session session,String send,String receive,String cReceive,String mReceive) throws Exception {
        //邮件内容
        MimeMessage mimeMessage = new MimeMessage(session);

        //纯文本，带图片
        //发件人
        Address address = new InternetAddress(send,"发件人的名字","UTF-8");

        mimeMessage.setFrom(address);
        mimeMessage.setSubject("这是标题。。。。","UTF-8");
        mimeMessage.setContent("正文内容收件人","text/html; charset=UTF-8");
        //收件人类型，TO普通收件人，CC抄送，BCC密送
        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receive,"收件人-A","UTF-8"));
        mimeMessage.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(receive,"收件人-B","UTF-8"));
        mimeMessage.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(receive,"收件人-C","UTF-8"));

        //发件时间
        mimeMessage.setSentDate(new Date());
        mimeMessage.saveChanges();//保存邮件

        return mimeMessage;


}
}

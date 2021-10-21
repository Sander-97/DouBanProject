package com.Sander.servlet;

import com.Sander.util.VerifyCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet(name = "VerifyCodeServlet" ,value = "/VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     *   用来生成图片验证码
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //生成util中VerifyCode的对象
        VerifyCode verifyCode = new VerifyCode();
        //获取图片对象
        BufferedImage bufferedImage = verifyCode.getImage();
        //获取验证码文本
        String text = verifyCode.getText();
        //把系统生成的文本保存到session中
        request.getSession().setAttribute("text", text);
        //向浏览器输出图片
        VerifyCode.output(bufferedImage, response.getOutputStream());

    }
}

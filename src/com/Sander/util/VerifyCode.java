package com.Sander.util;

import javax.imageio.ImageIO;
import javax.sql.rowset.spi.SyncResolver;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.FloatBuffer;
import java.util.Random;

/**
 * @ClassName VerifyCode
 * @Direction: 生成不同的验证码
 * @Author: Sander
 * @Date 2021/10/14 11:07
 * @Version 1.0
 **/
public class VerifyCode{
    private int w = 70;
    private int h = 35;

    /**
     * 一个随机数
     */
    private Random random = new Random();

    /**
     * 设置的字体：{"宋体"，“华文楷体”，“黑体”，“华文新魏”，“华文隶书”，“微软雅黑”，“楷体_GB2312”}
     */
    private String[] fontNames = {"宋体","华文楷体","黑体","微软雅黑","楷体_GB2312"};

    /**
     * 可选字符
     */

    private String codes = "0123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    /**
     * 验证码的背景颜色
     */

    private Color bgColor = new Color(255,255,255);

    /**
     * 验证码上的文本：待核验的正确答案
     */

    private String Text;

    /**
     *  生成随机的颜色
     * @return 返回一个随机颜色
     */
    private Color randomColor(){
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);

        return new Color(red,green,blue);
    }

    /**
     * 生成随机的字体
     * @return 返回一种随机生成的字体
     */
    private Font randomFont(){
        int index = random.nextInt(fontNames.length);

        String fontName = fontNames[index];//随机生成字体名称

        int style = random.nextInt(4);//生成随机样式，0：无样式，1：粗体，2：斜体，3：粗体+斜体。

        int size = random.nextInt(5) + 24;//生成随机字号；24~28

        return new Font(fontName, style, size);
    }

    /**
     * 画干扰线
     * @param image
     */
    private void drawLine(BufferedImage image){
        int num = 3;//一共画3条

        Graphics2D g2 = (Graphics2D) image.getGraphics();
        //生成两个点坐标

        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            int x2 = random.nextInt(w);
            int y2 = random.nextInt(h);

            g2.setStroke(new BasicStroke(1.5F));//画干扰线
            g2.setColor(Color.BLUE);//干扰线是蓝色
            g2.drawLine(x1, y1, x2, y2);//划线
        }
    }

    /**
     * 随机生成一个字符
     * @return 返回一个char字符
     */
    private char randomChar(){
        int index = random.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 创建BufferedImage
     * @return 返回BufferedImage对象
     */
    private BufferedImage createImage(){

        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();

        graphics2D.setColor(this.bgColor);
        graphics2D.fillRect(0, 0, w, h);

        return bufferedImage;
    }

    /**
     * 调用这个方法得到验证码
     * @return BufferedImage
     */
    public BufferedImage getImage(){
        //创建图片缓冲区
        BufferedImage image = createImage();

        //得到绘制环境
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        //用来装载生成的验证码文本
        StringBuilder stringBuilder = new StringBuilder();

        //向图片中画4个字符，循环四次，每次生成一个字符
        for (int i = 0; i < 4; i++) {
            //随机生成一个字母
            String s = randomChar() + "";
            //把字母添加到stringBuilder中
            stringBuilder.append(s);
            //设置当前字符的x轴坐标
            float x = i * 1.0F * w /4;
            //设置随机字体
            graphics2D.setFont(randomFont());
            //设置随机字体颜色
            graphics2D.setColor(randomColor());
            //画图
            graphics2D.drawString(s, x, h-5);

        }

        /**
         * 把stringBuilder 类型的验证码赋给this.text
         */

        this.Text = stringBuilder.toString();
        //添加干扰线
        drawLine(image);
        return image;
    }

    /**
     * 获取验证码图片上的文本
     * @return 返回文本内容
     */
    public String getText(){
        return Text;
    }

    /**
     *  保存图片到指定的输出流
     * @param image BufferedImage
     * @param outputStream 输出流
     * @throws IOException
     */
    public static void output(BufferedImage image , OutputStream outputStream) throws IOException {
        ImageIO.write(image, "JPEG", outputStream);
    }




}

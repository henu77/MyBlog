package cn.malong.blog.utils;/*对图片进行处理的类和方法*/

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Component
public class VerifyCode {

    Color[] colors = {
            new Color(0, 80, 199, 238), new Color(0, 0, 0), new Color(68, 255, 155, 156),
            new Color(243, 17, 194), new Color(90, 20, 220), new Color(68, 255, 155),
            new Color(147, 210, 77, 180), new Color(250, 136, 159), new Color(120, 168, 239),
            new Color(0, 0, 205), new Color(220, 20, 60), new Color(46, 139, 87),
            new Color(255, 215, 0, 158), new Color(255, 127, 80), new Color(105, 105, 105),
            new Color(255, 109, 0, 158), new Color(86, 227, 252), new Color(226, 27, 27, 213)};


    // 图片高度
    private static final int IMG_HEIGHT = 100;
    // 图片宽度
    private static final int IMG_WIDTH = 30;
    // 验证码长度
    private static final int CODE_LEN = 4;

    private String verifyCode = "";

    public BufferedImage getImage() {
        verifyCode = "";
        BufferedImage verifyImg = new BufferedImage(IMG_HEIGHT, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        //设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        //填充背景
        graphics.fillRect(0, 0, IMG_HEIGHT, IMG_HEIGHT);
        //设置格式
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 21));

        //数字和字母的组合 ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz
        char[] baseNumLetter = "0123456789".toCharArray();

        //随机获得验证码
        Random random = new Random();
        for (int i = 0; i < CODE_LEN; i++) {
            verifyCode += baseNumLetter[random.nextInt(baseNumLetter.length)];
        }

        //将字符填充到图片中
        double x = 10;  //旋转原点的 x 坐标
        String ch = "";
        for (int i = 0; i < CODE_LEN; i++) {
            ch = verifyCode.charAt(i) + "";
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            int degree = random.nextInt() % 15;  //角度小于30度
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, IMG_WIDTH / 2);
            graphics.drawString(ch, (int) x, (int) IMG_WIDTH / 2 + 7);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, IMG_WIDTH / 2);
            x += IMG_HEIGHT / CODE_LEN;
        }

        for (int i = 0; i < 4; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(IMG_HEIGHT), random.nextInt(IMG_WIDTH),
                    random.nextInt(IMG_HEIGHT), random.nextInt(IMG_WIDTH));
        }
        //添加噪点
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(IMG_HEIGHT);
            int y1 = random.nextInt(IMG_WIDTH);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 1, 1);
        }
        //加边框
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, IMG_HEIGHT - 1, IMG_WIDTH - 1);
        return verifyImg;
    }

    /**
     * 随机取色
     */
    private Color getRandomColor() {
        Random ran = new Random();
        Color color = colors[ran.nextInt(colors.length)];
        return color;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void outPut(BufferedImage image, OutputStream outputStream) throws IOException {
        ImageIO.write(image, "JPEG", outputStream);
    }

}
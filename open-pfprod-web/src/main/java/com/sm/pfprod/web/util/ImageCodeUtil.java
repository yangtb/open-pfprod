package com.sm.pfprod.web.util;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @ClassName: ImageCodeUtil
 * @Description: 验证码相关
 * @Author yangtongbin
 * @Date 2018/3/9 15:45
 */
public class ImageCodeUtil {

//    @Value("${ImageCode.width}")
    private final int width ;
//    @Value("${ImageCode.height}")
    private final int height ;
//    @Value("${ImageCode.codeLength}")
    private final int codeLength ;
//    @Value("${ImageCode.randomString}")
    private final String randomString;
//    @Value("${ImageCode.sessionKey}")
    private final String sessionKey;
//    @Value("${ImageCode.font.name}")
    private final String fontName;
//    @Value("${ImageCode.font.style}")
    private final int fontStyle;
//    @Value("${ImageCode.font.size}")
    private final int fontSize;
//    @Value("${ImageCode.expireTime}")

    public ImageCodeUtil(int width, int height, int codeLength, String randomString, String sessionKey, String fontName, int fontStyle, int fontSize) {
        this.width = width;
        this.height = height;
        this.codeLength = codeLength;
        this.randomString = randomString;
        this.sessionKey = sessionKey;
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
    }

//    public ImageCode() {
//    }

    public BufferedImage getImage(HttpServletRequest request){
        // 在内存中创建图象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        String sRand = randomRand(codeLength);// 取随机产生的认证码
        int strWidth = width/2-g.getFontMetrics().stringWidth(sRand)/codeLength-24;
        int strHeight = height/2+12;
        for (int i = 0; i < codeLength; i++) {
            String rand = sRand.substring(i, i + 1);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，
            int zz=new Random().nextInt(8);
            zz=zz%2==0?zz-10:zz;
            g.drawString(rand, strWidth+(13+16*i), strHeight+zz);
        }
        request.getSession().setAttribute(sessionKey, sRand);
        g.dispose();
        return image;
    }

    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public String randomRand(int n) {
        String rand = "";
        int len = randomString.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            rand = rand + randomString.charAt((int) r);
        }
        return rand;
    }

    public String getSessionKey() {
        return sessionKey;
    }

}

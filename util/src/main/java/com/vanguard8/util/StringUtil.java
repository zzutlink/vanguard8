package com.vanguard8.util;

import java.util.Random;

public class StringUtil {

    //随机生成一定长度的字符串
    public static String generateString(int length) {
        //字母与数字
        String sources = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        //随机产生位置组合成字符串
        Random random = new Random();
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(62);
            s=s.append(sources.charAt(index));
        }
        return s.toString();
    }
}

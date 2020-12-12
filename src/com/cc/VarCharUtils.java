package com.cc;

import java.util.Random;

/**
 * @Classname VarCharUtils
 * @Description TODO
 * @Date 2020/12/12 23:23
 * @Created by 2413776263@qq.com
 */
public class VarCharUtils {
    public static String getRandomString(int num){
        String[] strings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String u = "";
        for (int k = 0; k < num; k++) {
            String string = strings[(new Random()).nextInt(26)];
            u += string;
        }
     return u;
    }
}

package test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPatch;
import com.mysql.cj.xdevapi.JsonParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @Classname testMethod
 * @Description TODO
 * @Date 2020/12/13 19:13
 * @Created by 2413776263@qq.com
 */
public class testMethod {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("6");
         strings.forEach(System.out::print);
//        System.out.println();
        Collections.shuffle(strings);
        strings.forEach(System.out::print);
        strings.toArray();
        JSONObject jsonObject = new JSONObject();
            jsonObject.put("2",strings);
        ArrayList<String> o = (ArrayList<String>)jsonObject.get("2");
//        o.forEach(System.out::print);
//        JSONObject jsonObject1 = jsonObject.
//        System.out.println(jsonObject1);
//
        JSONObject jsonObject1 = jsonObject.fluentPut("1", "2");
//        Object put = jsonObject.put("1", "2");
        jsonObject1.put("222","22");
//        System.out.println(jsonObject1);
        jsonObject.forEach((key, value) -> {
            System.out.println(key+"===="+value);
//            System.out.println(value);
        });

    }
}
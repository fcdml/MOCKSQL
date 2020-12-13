package com.cc;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

/**
 * @Classname testDbForFieldDefinition
 * @Description TODO
 * @Date 2020/12/12 1:18
 * @Created by 2413776263@qq.com
 */
public class testDbForFieldDefinition {
    public static void main(String[] args) {
        DbForFieldDefinition dbForFieldDefinition = new DbForFieldDefinition();
        ArrayList<JSONObject> anEnum = dbForFieldDefinition.getAllFieldDefinition();
     anEnum.forEach((e)->{
         if (e.getString("dataType").equals("enum")){
             System.out.println(e.get("columnType").getClass().getTypeName());
         }

     });
    }
}

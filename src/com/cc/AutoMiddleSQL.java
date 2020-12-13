package com.cc;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @Classname AutoMiddleSQL
 * @Description TODO
 * @Date 2020/12/13 16:37
 * @Created by 2413776263@qq.com
 */
public class AutoMiddleSQL{
    private String tableName;
    private Integer num;
    private JSONObject jsonObject;
    static{
        SQLUtils.getDBConnection();
    }
    public AutoMiddleSQL(String tableName, Integer num,JSONObject jsonObject) {
        this.tableName = tableName;
        this.num = num;
        this.jsonObject=jsonObject;
        SQLUtils.getFieldNum(tableName);
        SQLUtils.getMaxLine(tableName);
    }
    public void autoInsertM(){
   //TODO 做个预判 某个表中 某个字段是否为空 要预先判断 不然到插入关联表中没有任何意义
        //key是表名 val是该表的字段
        jsonObject.forEach((key, value) -> {


        });









    };


















}

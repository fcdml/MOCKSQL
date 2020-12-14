package com.cc;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cc.SQLUtils.getMaxLine;

/**
 * @Classname AutoMiddleSQL
 * @Description TODO
 * @Date 2020/12/13 16:37
 * @Created by 2413776263@qq.com
 */
public class AutoMiddleSQL{
    private String tableName;
    private JSONObject jsonObject;
    public AutoMiddleSQL(String tableName,JSONObject jsonObject) {
        this.tableName = tableName;
        this.jsonObject=jsonObject;
        SQLUtils.getFieldNum(tableName);
        SQLUtils.getMaxLine(tableName);
    }
    public void autoInsertM(){
        System.out.println("开始生成随机数据....");
        java.util.Date dateStart = new java.util.Date();
        AtomicInteger lengths= new AtomicInteger(0);
        //为true 即取最大的行 反之false
   //TODO 做个预判 某个表中 某个字段是否为空 要预先判断 不然到插入关联表中没有任何意义
        //key是表名 val是该表的字段
        JSONObject allTable = new JSONObject();
        this.jsonObject.forEach((key, value) -> {
            ArrayList<String> strings = new ArrayList<>();
            JSONObject rowConnection = AutoMiddleUtils.getRowConnection(key, (String) value);
            if ("int".equals(rowConnection.getString("dataType"))){
                ArrayList<Integer> integers = (ArrayList<Integer>) JSONArray.parseArray(rowConnection.getString(String.valueOf(value)), Integer.class);
                Collections.shuffle(integers);

                if (integers.size()>lengths.get()){
                    lengths.set(integers.size());
                }
             allTable.fluentPut(String.valueOf(value),integers).fluentPut("dataType","int");
            }
            if ("varchar".equals(rowConnection.getString("dataType"))){
//                System.out.println("11111");
//                System.out.println(JSONUtils.toJSONString(rowConnection.getString(String.valueOf(value))));
//                System.out.println(JSONArray.parseArray(rowConnection.getString(String.valueOf(value)), String.class));
//                String string = rowConnection.getString(String.valueOf(value));
//
//                ArrayList<String> strings1= new ArrayList<String>(Arrays.asList(string));
//                ArrayList<String> strings1 = (ArrayList<String>)Arrays.asList(string.split(","));
//                ArrayList<String>    strings1=JSON.parseObject(rowConnection.getString(String.valueOf(value)), new TypeReference<ArrayList<String>>(){});
//                System.out.println("2222");
//                ArrayList<String> strings1 = (ArrayList<String>) JSONArray.parseArray(rowConnection.getString(String.valueOf(value)), String.class);
//                Collections.shuffle(strings1);
//             allTable.fluentPut(String.valueOf(value),strings1).fluentPut("dataType","varchar");;
//                if (strings1.size()>lengths.get()){
//                    lengths.set(strings1.size());
//                }
            }
            if ("enum".equals(rowConnection.getString("dataType"))){
                ArrayList<Enum> enums = (ArrayList<Enum>) JSONArray.parseArray(rowConnection.getString(String.valueOf(value)), Enum.class);
                Collections.shuffle(enums);
             allTable.fluentPut(String.valueOf(value),enums).fluentPut("dataType","enum");;
                if (enums.size()>lengths.get()){
                    lengths.set(enums.size());
                }
            }
        });
        String insertsql="insert into "+tableName+" values (";
        System.out.println("jsonObject.size()--->"+jsonObject.size());
        for (int i = 1; i <=jsonObject.size() ; i++) {
            if (i==jsonObject.size()){
                insertsql+="?)";
                break;
            }
            insertsql+="?,";
        }
        Connection dbConnection =null;
        PreparedStatement preparedStatement=null;
        try {
            dbConnection = SQLUtils.getDBConnection();
           preparedStatement = dbConnection.prepareStatement(insertsql);
            int maxLine = getMaxLine(tableName);
            int j=-1;
            for (int i = maxLine+1; i <=maxLine+lengths.get(); i++) {
             ++j;
                int k=1;
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
//                    System.out.println("key="+key+"<----->"+"value="+value);
//                    System.out.println("allTable=="+allTable);
                    if ("int".equals(allTable.getString("dataType"))) {
                        System.out.println("==================="+JSONArray.parseArray(allTable.getString(String.valueOf(value)), Integer.class).get(j));
                        System.out.println("k==="+k);
                        System.out.println("j==="+j);
                        preparedStatement.setInt(k++, JSONArray.parseArray(allTable.getString(String.valueOf(value)), Integer.class).get(j));

                    }
                    if ("varchar".equals(allTable.getString("dateType"))) {
                        preparedStatement.setString(k++, JSONArray.parseArray(allTable.getString(String.valueOf(value)), String.class).get(j));
                    }
                    if ("enum".equals(allTable.getString("dateType"))) {
                        preparedStatement.setString(k++, JSONArray.parseArray(allTable.getString(String.valueOf(value)), String.class).get(j));
                    }
                }

             preparedStatement.executeUpdate();
                System.out.println("已生成编号为"+i+"的数据");
            }
            java.util.Date dateEnd = new java.util.Date();
            System.out.println("生成数据完成---共生成"+lengths.get()+"条数据---"+"耗时---"+(dateEnd.getTime()-dateStart.getTime())+"毫秒");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            SQLUtils.release(dbConnection,preparedStatement);
        }


    };


















}

package com.cc.code.autoAllSQL;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.code.autoAllUtils.MiddleTableUtils.AutoMiddleUtils;
import com.cc.code.connectionUtils.SQLUtils;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cc.code.connectionUtils.SQLUtils.getMaxLine;

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
            System.out.println("key="+key+"  "+value);
            ArrayList<String> strings = new ArrayList<>();
            JSONObject rowConnection = AutoMiddleUtils.getRowConnection(key, (String) value);
            if ("int".equals(rowConnection.getString("dataType"))){
                List<Integer> integers = new ArrayList<>();
               integers= JSONObject.parseArray(rowConnection.getJSONArray(String.valueOf(value)).toJSONString(),Integer.class);
                Collections.shuffle(integers);
                if (integers.size()>lengths.get()){
                    lengths.set(integers.size());
                }
                JSONObject intjsonObject = new JSONObject();
                JSONArray objects = new JSONArray();
                objects.addAll(integers);
                intjsonObject.fluentPut(String.valueOf(value),objects).fluentPut("dataType","int");
                System.out.println("++++++++++"+intjsonObject);
                allTable.put(String.valueOf(value),intjsonObject);
            }
            if ("varchar".equals(rowConnection.getString("dataType"))){
                List<String> uu = new ArrayList<>();
                uu= JSONObject.parseArray(rowConnection.getJSONArray(String.valueOf(value)).toJSONString(),String.class);
                DateTime startDateTime = new DateTime();
                System.out.println("startDateTime---"+startDateTime);
                Collections.shuffle(uu);
                DateTime endDateTime = new DateTime();
                System.out.println("endDateTime-----"+endDateTime);
                System.out.println("打乱顺序 耗时-- ---"+(endDateTime.getMillis()-startDateTime.getMillis()));
                if (uu.size()>lengths.get()){
                    lengths.set(uu.size());
                }
                JSONObject stringjsonObject = new JSONObject();
                JSONArray objects = new JSONArray();
                objects.addAll(uu);
                stringjsonObject.fluentPut(String.valueOf(value),objects).fluentPut("dataType","varchar");
                allTable.put(String.valueOf(value),stringjsonObject);
            }
            if ("enum".equals(rowConnection.getString("dataType"))){
                List<String> enumList = new ArrayList<>();
                enumList= JSONObject.parseArray(rowConnection.getJSONArray(String.valueOf(value)).toJSONString(),String.class);
                Collections.shuffle(enumList);
                if (enumList.size()>lengths.get()){
                    lengths.set(enumList.size());
                }
                JSONObject enumjsonObject = new JSONObject();
                JSONArray objects = new JSONArray();
                objects.addAll(enumList);
                enumjsonObject.fluentPut(String.valueOf(value),objects).fluentPut("dataType","enum");
                System.out.println("====================="+enumjsonObject);
                allTable.put(String.valueOf(value),enumjsonObject);
            }
        });
//        System.out.println(allTable+"alllll");
        String insertsql="insert into "+tableName+" values (";
        System.out.println("jsonObject.size()--->"+ this.jsonObject.size());
        for (int i = 1; i <= this.jsonObject.size() ; i++) {
            if (i== this.jsonObject.size()){
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
                    System.out.println("key="+key+"<----->"+"value="+value);
                    System.out.println("dataType"+allTable.getString("dataType"));
//                    System.out.println("allTable=="+allTable);
                    if ("int".equals(allTable.getJSONObject(String.valueOf(value)).getString("dataType"))) {
                        System.out.println(j);
//                        System.out.println("==================="+JSONArray.parseArray(allTable.getJSONObject(String.valueOf(value)).getString(String.valueOf(value)), Integer.class).get(j));
                        System.out.println("k==="+k);
                        System.out.println("j==="+j);
                        System.out.println(allTable.getJSONObject(String.valueOf(value)).getJSONArray(String.valueOf(value)).get(j));
                        Integer o = (Integer)allTable.getJSONObject(String.valueOf(value)).getJSONArray(String.valueOf(value)).get(j);
//                        int i1 = Integer.parseInt(o);
                        preparedStatement.setInt(k++,o);
                    }
                    if ("varchar".equals(allTable.getJSONObject(String.valueOf(value)).getString("dataType"))) {
//                        System.out.println(JSONArray.parseArray(allTable.getJSONObject(String.valueOf(value)).toString())
                        String o = (String)allTable.getJSONObject(String.valueOf(value)).getJSONArray(String.valueOf(value)).get(j);
                        preparedStatement.setString(k++,o);
                    }
                    if ("enum".equals(allTable.getJSONObject(String.valueOf(value)).getString("dataType"))) {
                        System.out.println("------------------");
                        String o = (String)allTable.getJSONObject(String.valueOf(value)).getJSONArray(String.valueOf(value)).get(j);
                        System.out.println("o----"+o);
                        preparedStatement.setString(k++,o);
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

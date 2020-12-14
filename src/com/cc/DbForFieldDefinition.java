package com.cc;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cc.SQLUtils.getDBConnection;

/**
 * @Classname DbForFieldDefinition
 * @Description TODO
 * @Date 2020/12/12 0:19
 * @Created by 2413776263@qq.com
 */
public class DbForFieldDefinition {
    public DbForFieldDefinition() {
    }

    public ArrayList<JSONObject> getAllFieldDefinition(String tableName) {
        ArrayList<JSONObject> allJsonObject = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = getDBConnection();
            String selectSQL="SELECT COLUMN_NAME  ename,ORDINAL_POSITION eposition,DATA_TYPE etype,CHARACTER_MAXIMUM_LENGTH eCMaxnum,COLUMN_TYPE etype FROM  information_schema.COLUMNS WHERE TABLE_SCHEMA = 'mocksql' "+"AND table_name="+"'"+tableName+"'" ;
            preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet execute = preparedStatement.executeQuery();
            HashMap<Integer, String> integerStringHashMap = new HashMap<>();
            String s = "'.{1,}'";
            Pattern compile = Pattern.compile(s);
            Matcher matcher = null;
            int i = 0;
            allJsonObject = new ArrayList<>();
            while (execute.next()) {
                JSONObject jsonObject = new JSONObject(new LinkedHashMap());
                jsonObject.fluentPut("columnName", execute.getString(1))
                        .fluentPut("ordinalPosition", execute.getInt(2))
                        .fluentPut("dataType", execute.getString(3))
                        .fluentPut("characterMaximumLength", execute.getInt(4))
                        .fluentPut("columnType", execute.getString(5));
                if (execute.getString(5).contains("enum")) {
                    System.out.println("==+++++===="+execute.getString(5));
                    ArrayList<String> strings = new ArrayList<>();
//                System.out.println("====="+execute.getString(2));
                    matcher = compile.matcher(execute.getString(5));
                   System.out.println("iiiiiiiii"+execute.getString(5));
                    int u=0;
                    while (matcher.find()) {
                        u++;
                        String[] split = matcher.group().split(",");
                        System.out.println("LLLLLLL"+split);
                        System.out.println(split.length);
                        for (int j = 0; j <split.length ; j++) {
                            System.out.println("ZZZZZZ"+split[i].substring(1,split[i].length()-1));
                            strings.add(split[j].substring(1,split[j].length()-1));
                        }
                    }
                    System.out.println("u--->"+u);
                    System.out.println(strings);
                    jsonObject.put("columnType", strings);
                }
                allJsonObject.add(jsonObject);
            }
            System.out.println("allJsonObject----->"+allJsonObject);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库的驱动校验失败!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            SQLUtils.release(connection, preparedStatement);
        }
        return allJsonObject;
    }
    public String  getSingleFieldDefinition(String tableName,String columnName){
        ArrayList<JSONObject> allFieldDefinition = getAllFieldDefinition(tableName);
        for (JSONObject e : allFieldDefinition) {
            if (e.getString("columnName").equals(columnName)){
                return e.getString("dataType");
            }
        }
        return null;
    }



}

package com.cc;

import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname DbForFieldDefinition
 * @Description TODO
 * @Date 2020/12/12 0:19
 * @Created by 2413776263@qq.com
 */
public class DbForFieldDefinition {
    private static String url = "jdbc:mysql://localhost:3306/newframework?useSSL=false&characterEncoding=utf8&serverTimeZone=GMT+8";
    private static String username = "root";
    private static String password = "mysql9614";
    public DbForFieldDefinition() {
    }
    public ArrayList<JSONObject> getAllFieldDefinition() {
        ArrayList<JSONObject> allJsonObject =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DruidUtils.getConnection();;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT\n" +
                    "COLUMN_NAME  ename,ORDINAL_POSITION eposition,DATA_TYPE etype,CHARACTER_MAXIMUM_LENGTH eCMaxnum,COLUMN_TYPE etype\n" +
                    "FROM\n" +
                    "information_schema.COLUMNS\n" +
                    "WHERE\n" +
                    "TABLE_SCHEMA = 'newframework'\n" +
                    "AND table_name='forjdbc'");
            ResultSet execute = preparedStatement.executeQuery();
            HashMap<Integer, String> integerStringHashMap = new HashMap<>();
            String s = "'...'";
            Pattern compile = Pattern.compile(s);
            Matcher matcher = null;
            int i = 0;
            allJsonObject = new ArrayList<>();
            while (execute.next()) {
            JSONObject    jsonObject = new JSONObject(new LinkedHashMap());
//                System.out.println(execute.getString(1));
                jsonObject.put("columnName",execute.getString(1));
               jsonObject.put("ordinalPosition",execute.getInt(2));
               jsonObject.put("dataType",execute.getString(3));
               jsonObject.put("characterMaximumLength",execute.getInt(4));
               jsonObject.put("columnType",execute.getString(5));
                if (execute.getString(5).contains("enum")){
//                    System.out.println("======");
                    ArrayList<String> strings = new ArrayList<>();
//                System.out.println("====="+execute.getString(2));
                    matcher = compile.matcher(execute.getString(5));
                    while (matcher.find()) {
//                    System.out.println("+++");
                        strings.add(matcher.group().substring(1, matcher.group().length() - 1));
                    }
//                    System.out.println(strings);
                    jsonObject.put("columnType",strings);
                }
                allJsonObject.add(jsonObject);

            }
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库的驱动校验失败!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allJsonObject;
    }

}

package com.cc;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname DbForEnum
 * @Description TODO
 * @Date 2020/12/12 0:19
 * @Created by 2413776263@qq.com
 */
public class DbForEnum {
    private static String url = "jdbc:mysql://localhost:3306/woniu_thirdstage_project?useSSL=false&characterEncoding=utf8&serverTimeZone=GMT+8";
    private static String username = "root";
    private static String password = "mysql9614";

    public DbForEnum() {
    }

    public  HashMap<HashMap<Integer, String>, ArrayList<String>> getEnum() {
        HashMap<HashMap<Integer, String>, ArrayList<String>> stringStringHashMap=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT\n" +
                    "COLUMN_NAME  ename,COLUMN_TYPE etype,ORDINAL_POSITION eposition\n" +
                    "FROM\n" +
                    "information_schema.COLUMNS\n" +
                    "WHERE\n" +
                    "TABLE_SCHEMA = 'newframework'\n" +
                    "AND DATA_TYPE = 'enum'\n" +
                    "AND table_name='forjdbc'");
            ResultSet execute = preparedStatement.executeQuery();
       stringStringHashMap = new HashMap<>();
            HashMap<Integer, String> integerStringHashMap = new HashMap<>();
            String s = "'...'";
            Pattern compile = Pattern.compile(s);
            Matcher matcher = null;
            int i = 0;
            while (execute.next()) {
                System.out.println(execute.getInt(3));
                ArrayList<String> strings = new ArrayList<>();
//                System.out.println("====="+execute.getString(2));
                matcher = compile.matcher(execute.getString(2));
                while (matcher.find()) {
//                    System.out.println("+++");
                    strings.add(matcher.group().substring(1, matcher.group().length() - 1));
                }
                HashMap<Integer, String> integerStringHashMap1 = new HashMap<>();
                integerStringHashMap1.put(execute.getInt(3),execute.getString(1));
stringStringHashMap.put(integerStringHashMap1, strings);
//                stringStringHashMap.put(integerStringHashMap.put(,execute.getString(1)), strings);
//                System.out.println("----"+execute.getString(1)+strings);
            }
//            for (Map.Entry<String, ArrayList<String>>  u:
//                 stringStringHashMap.entrySet()) {
//                System.out.println(u.getKey());
//                System.out.println("size-----"+u.getValue().size());
//                for (int j = 0; j <u.getValue().size() ; j++) {
//                    System.out.println("enum--------->"+u.getValue().get(j));
//                }
//            }
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库的驱动校验失败!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return stringStringHashMap;
    }

}

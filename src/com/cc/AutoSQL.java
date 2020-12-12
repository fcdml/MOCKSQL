package com.cc;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @Classname com.cc.AutoSQL
 * @Description TODO
 * @Date 2020/12/11 21:32
 * @Created by 2413776263@qq.com
 */
public class AutoSQL {
    private static String url = "jdbc:mysql://localhost:3306/newframework?useSSL=false&characterEncoding=utf8&serverTimeZone=GMT+8";
    private static String username = "root";
    private static String password = "mysql9614";
    private String tableName;
    private Integer num;
    public AutoSQL(String tableName, Integer num) {
        this.tableName = tableName;
        this.num = num;

    }

    //TODO 写私有方法防止外部调用
    private void  ss(){
        System.out.println(222);
    }
    private Connection getDBConnection(){
        Connection connection =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败");
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("连接数据库异常 请检查 数据库名称 用户名 密码");
            throwables.printStackTrace();
        }
       return  connection;
    }
    //TODO 需要从url中获取数据库名称
    private int getFieldNum(String tablename){
        Connection dbConnection = getDBConnection();
        int tableFieldNum = 0;
        try {
            String getColumnNumSQL="select count(*) columnnum from information_schema.columns where table_schema='newframework' and table_name="+"'"+tablename+"'";
            PreparedStatement preparedColumnnum = dbConnection.prepareStatement(getColumnNumSQL);
            ResultSet tableFieldNumPre=preparedColumnnum.executeQuery();
            tableFieldNum = 0;
            while (tableFieldNumPre.next()){
                tableFieldNum = tableFieldNumPre.getInt(1);
//                System.out.println(tableFieldNum);
            }
            preparedColumnnum.close();
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  tableFieldNum;
    }
    // 为了从已有数据往后查 所有要找到当前最大的行数
     private  int getMaxLine(String tableName){
         int maxLineNum=0;
        try {
             Connection dbConnection = getDBConnection();
             String maxLineSQL="select count(*) from "+tableName;
             PreparedStatement preparedStatementNum = dbConnection.prepareStatement(maxLineSQL);
             ResultSet allnum=preparedStatementNum.executeQuery();
             while (allnum.next()){
                 maxLineNum = allnum.getInt(1);
             }
         } catch (SQLException throwables) {
            System.out.println("getMaxLine方法中的sql异常");
            throwables.printStackTrace();
         }
        return maxLineNum;
     }
    public void AutoInsert(){
        try {
            System.out.println("开始生成随机数据....");
            java.util.Date date1 = new java.util.Date();
            Connection connection = getDBConnection();
            int tableFieldNum = getFieldNum(tableName);
            String insertsql="insert into forjdbc values (";
            for (int i = 1; i <=tableFieldNum ; i++) {
                if (i==tableFieldNum){
                    insertsql+="?)";
                break;
                }
                insertsql+="?,";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(insertsql);
            Random random = new Random();
            int q = 0;// declaredFields数组下表
            boolean hh=false;
            int maxLine = getMaxLine(tableName);
            for (int j = maxLine+1; j <= maxLine+num; j++) {
                for (int i = 1; i <= tableFieldNum; i++) {
                    String typeName=FieldDefinitionUtils.getDataType(i);
                    Boolean anEnum = EnumUtils.isEnum(i);
                    if (anEnum){
                        Random random1 = new Random();
                        ArrayList<String> columnType =FieldDefinitionUtils.getColumnType(i);
                        try {
                            preparedStatement.setString(i,columnType.get(random1.nextInt(columnType.size())));
                            hh=true;
                        } catch (SQLException throwables) {
                            System.out.println("116行出现异常");
                            throwables.printStackTrace();
                        }
                    }
                    if (hh){
                        hh=false;
                        continue;
                    }
                    if (i == 1 && ("int".equals(typeName) || "Integer".equals(typeName))) {
                        preparedStatement.setString(i, String.valueOf(j));
                        continue;
                    }
                    if ("varchar".equals(typeName)) {
                        preparedStatement.setString(i,VarCharUtils.getRandomString(6));
                        continue;
                    }
                    if ("datetime".equals(typeName)){
                        preparedStatement.setDate(i,  new Date(TimeUtils.getDateTime().getMillis()));
                        continue;
                    }
                    if ("date".equals(typeName)){
                        preparedStatement.setDate(i,  new Date(TimeUtils.getDate().getMillis()));
                        continue;
                    }
                    if ("double".equals(typeName)){
                        continue;
                    }
                }
//                System.out.println("j====="+j);
                preparedStatement.executeUpdate();
//                System.out.println("j((((("+j);
                System.out.println("已生成编号为"+j+"的数据");
            }
            java.util.Date date2 = new java.util.Date();
            System.out.println("生成数据完成---共生成"+num+"条数据---"+"耗时---"+(date2.getTime()-date1.getTime())+"毫秒");
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void AutoInsert(String dateField1,String dateField2,String dateField3){
        if (dateField1==null||dateField2==null||dateField3==null){
            System.out.println("三个参数需要全部输入");
            return;
        }
        try{
            System.out.println("开始生成随机数据....");
            java.util.Date dateStart = new java.util.Date();
            Connection connection = getDBConnection();
            int tableFieldNum = getFieldNum(tableName);
            String insertsql="insert into forjdbc values (";
            for (int i = 1; i <=tableFieldNum ; i++) {
                if (i==tableFieldNum){
                    insertsql+="?)";
                    break;
                }
                insertsql+="?,";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(insertsql);
            Random random = new Random();
            int q = 0;// declaredFields数组下表
            boolean hh=false;
            int maxLine = getMaxLine(tableName);
            for (int j = maxLine+1; j <= maxLine+num; j++) {
                for (int i = 1; i <= tableFieldNum; i++) {
                    String typeName=FieldDefinitionUtils.getDataType(i);
                    Boolean anEnum = EnumUtils.isEnum(i);
                    if (anEnum){
                        Random random1 = new Random();
                        ArrayList<String> columnType =FieldDefinitionUtils.getColumnType(i);
                        try {
                            preparedStatement.setString(i,columnType.get(random1.nextInt(columnType.size())));
                            hh=true;
                        } catch (SQLException throwables) {
                            System.out.println("116行出现异常");
                            throwables.printStackTrace();
                        }
                    }
                    if (hh){
                        hh=false;
                        continue;
                    }
                    if (i == 1 && ("int".equals(typeName) || "Integer".equals(typeName))) {
                        preparedStatement.setString(i, String.valueOf(j));
                        continue;
                    }
                    if ("varchar".equals(typeName)) {
                        preparedStatement.setString(i,VarCharUtils.getRandomString(6));
                        continue;
                    }
                    if ("datetime".equals(typeName)){
                        Boolean datetimeFlag=false;
                        TreeMap<Integer, HashMap<String, DateTime>> sortDateTime = TimeUtils.getSortDateTime(dateField1, dateField2, dateField3);
                        for (Map.Entry s:
                        sortDateTime.entrySet()) {
                            if (s.getKey().equals(i)){
                                HashMap<String, DateTime> value = (HashMap<String, DateTime>)s.getValue();
                                System.out.println(new Date(value.get(FieldDefinitionUtils.getColumnName(i)).getMillis()));
                                preparedStatement.setDate(i,new Date(value.get(FieldDefinitionUtils.getColumnName(i)).getMillis()));
                                datetimeFlag=true;
                                break;
                            }
                        }
                        sortDateTime.remove(i);
                        if (datetimeFlag){
                            continue;
                        }
                        preparedStatement.setDate(i,  new Date(TimeUtils.getDateTime().getMillis()));
                        continue;
                    }
                    if ("date".equals(typeName)){
                        preparedStatement.setDate(i,  new Date(TimeUtils.getDate().getMillis()));
                        continue;
                    }
                    if ("double".equals(typeName)){
                        continue;
                    }
                }
//                System.out.println("j====="+j);
                preparedStatement.executeUpdate();
//                System.out.println("j((((("+j);
                System.out.println("已生成编号为"+j+"的数据");
            }
            java.util.Date dateEnd = new java.util.Date();
            System.out.println("生成数据完成---共生成"+num+"条数据---"+"耗时---"+(dateEnd.getTime()-dateStart.getTime())+"毫秒");
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

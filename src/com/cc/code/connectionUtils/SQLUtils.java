package com.cc.code.connectionUtils;

import java.sql.*;
import java.util.HashSet;

/**
 * @Classname SQLUtils
 * @Description TODO
 * @Date 2020/12/13 16:47
 * @Created by 2413776263@qq.com
 */
public class SQLUtils {
    public SQLUtils() {
    }

    //获取连接
    public static   Connection getDBConnection(){
        Connection connection =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DruidUtils.getConnection();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败");
            e.printStackTrace();
        } catch (Exception throwables) {
            System.out.println("Method:getDBConnection 未知异常");
            throwables.printStackTrace();
        }
        return  connection;
    }
    //获取字段数
    //TODO 需要从url中获取数据库名称
    public static int getFieldNum(String tablename){
        Connection dbConnection = getDBConnection();
        int tableFieldNum = 0;
        try {
            String getColumnNumSQL="select count(*) columnnum from information_schema.columns where table_schema='mocksql' and table_name="+"'"+tablename+"'";
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
    //为了从已有数据往后查 所有要找到当前最大的行数
    public static int getMaxLine(String tableName){
        int maxLineNum=0;
        try {
//            System.out.println(tableName);
            Connection dbConnection = getDBConnection();
            String maxLineSQL="select count(*) from "+tableName;
            PreparedStatement preparedStatementNum = dbConnection.prepareStatement(maxLineSQL);
            ResultSet  allnum = preparedStatementNum.executeQuery(maxLineSQL);
            while (allnum.next()){
                maxLineNum = allnum.getInt(1);
            }
            release(dbConnection,preparedStatementNum);
        } catch (SQLException throwables) {
            System.out.println("getMaxLine方法中的sql异常");
            throwables.printStackTrace();
        }
        return maxLineNum;
    }
    /**
     * 释放资源的方法
     * @param conn   数据库连接
     * @param statement  预编译对象
     */
    public static void release(Connection conn, Statement statement) {
        HashSet<Object> objects = new HashSet<>();
        try {
            if(statement != null) {
                statement.close();
            }
            if(conn !=null) {
//                conn.commit();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

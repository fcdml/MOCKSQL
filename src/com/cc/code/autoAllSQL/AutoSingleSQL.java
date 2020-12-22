package com.cc.code.autoAllSQL;
import com.cc.code.Definition.FieldDefinitionUtils;
import com.cc.code.autoAllUtils.MiddleTableUtils.AutoSingleUtils;
import com.cc.code.connectionUtils.SQLUtils;
import com.cc.code.fieldUtils.EnumUtils;
import com.cc.code.fieldUtils.TimeUtils;
import com.cc.code.fieldUtils.VarCharUtils;
import org.joda.time.DateTime;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.cc.code.autoAllUtils.MiddleTableUtils.AutoSingleUtils.*;

/**
 * @Classname com.cc.AutoAllSQL.AutoSingleSQL
 * @Description TODO
 * @Date 2020/12/11 21:32
 * @Created by 2413776263@qq.com
 */
public class AutoSingleSQL {
    private static String tableName;
    private Integer num;
    private static  volatile ConcurrentHashMap<String, Object> allVal= new ConcurrentHashMap<>();
    static{
        System.out.println("++++++++++++++++");
        SQLUtils.getDBConnection();
        System.out.println("===============");
    }
    public AutoSingleSQL(String tableName, Integer num) {
        this.tableName = tableName;
        this.num = num;
    }
    //TODO 需要从url中获取数据库名称
    public void autoInsertAll(String ... dateFields) throws Exception {
        //参数放在list 通过调用contains 判断是否包含某元素 主要用在if ("datetime".equals(typeName)||"date".equals(typeName))
        // 加强if 判断 不然下面无排序要的日期也无法走通
        ArrayList<String> temporaryList = new ArrayList<>();
        for (String s:dateFields){
                if (s==null){
                    System.out.println("三个参数需要全部输入");
                    return;
                }
                temporaryList.add(s);
        }
        Connection connection =null;
        PreparedStatement preparedStatement =null;
        try{
            System.out.println("开始生成随机数据....");
            java.util.Date dateStart = new java.util.Date();
           connection = SQLUtils.getDBConnection();
            int tableFieldNum = SQLUtils.getFieldNum(tableName);
            Random random = new Random();
            int q = 0;// declaredFields数组下表
            boolean hh=false;
            int maxLine = SQLUtils.getMaxLine(tableName);
            int testDateTimesNum=0;
            String insertsql="insert into "+tableName+" values ";
            Integer allNum=maxLine+num;
            DateTime dateTimeforbegin = new DateTime();
            System.out.println("forloop begin time===="+dateTimeforbegin);
            for (int j = maxLine+1; j <= allNum; j++) {
                Boolean datetimeFlagX=false;
                Boolean dateFlagX=false;
                TreeMap<Integer, HashMap<String, DateTime>> sortDateTime = TimeUtils.getSortDateOrDateTime(tableName,dateFields);
                HashMap<String, DateTime> testDateTimes = new HashMap<>();
                insertsql+=getSingleValue(  allVal, j,dateFields,temporaryList,tableName,tableFieldNum);
                if (j!=allNum){
                    insertsql+=",";
                    System.out.println("已生拼接标号为"+j+"的数据");
                continue;
                }
                if (j==allNum){
                    insertsql+=";";
                    System.out.println("已生拼接标号为"+j+"的数据");
                }
                int tt=0;
            }
            System.out.println("forloop All time===="+(new DateTime().getMillis()-dateTimeforbegin.getMillis()));
            System.out.println("insertsql---"+insertsql);
            preparedStatement = connection.prepareStatement(insertsql);
            preparedStatement.execute();
            if (testDateTimesNum==num){
                System.out.println("大小关系已建立");
            }
            java.util.Date dateEnd = new java.util.Date();
            System.out.println("生成数据完成---共生成"+num+"条数据---"+"耗时---"+(dateEnd.getTime()-dateStart.getTime())+"毫秒");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            SQLUtils.release(connection,preparedStatement);
        }
    }
    public void autoInsertAllNoRelation() throws Exception {
//        String[] dateFields;
//        dateFields = (String[])allVal.get("setAutoDateTimeR");
        //参数放在list 通过调用contains 判断是否包含某元素 主要用在if ("datetime".equals(typeName)||"date".equals(typeName))
        // 加强if 判断 不然下面无排序要的日期也无法走通
//        ArrayList<String> temporaryList = new ArrayList<>();
//            for (String s:dateFields){
//                if (s==null){
//                    System.out.println("三个参数需要全部输入");
//                    return;
//                }
//                temporaryList.add(s);
//        }
        Connection connection =null;
        PreparedStatement preparedStatement =null;
        try{
            System.out.println("开始生成随机数据....");
            java.util.Date dateStart = new java.util.Date();
           connection = SQLUtils.getDBConnection();
            int tableFieldNum = SQLUtils.getFieldNum(tableName);
            Random random = new Random();
            int q = 0;// declaredFields数组下表
            boolean hh=false;
            int maxLine = SQLUtils.getMaxLine(tableName);
            int testDateTimesNum=0;
            String insertsql="insert into "+tableName+" values ";
            Integer allNum=maxLine+num;
            DateTime dateTimeforbegin = new DateTime();
            System.out.println("forloop begin time===="+dateTimeforbegin);
            for (int j = maxLine+1; j <= allNum; j++) {
                Boolean datetimeFlagX=false;
                Boolean dateFlagX=false;
//                TreeMap<Integer, HashMap<String, DateTime>> sortDateTime = TimeUtils.getSortDateOrDateTime(tableName,dateFields);
//                HashMap<String, DateTime> testDateTimes = new HashMap<>();
                insertsql+=getSingleValueNoRelation(  allVal, j,tableName,tableFieldNum);
                if (j!=allNum){
                    insertsql+=",";
                    System.out.println("已生拼接标号为"+j+"的数据");
                continue;
                }
                if (j==allNum){
                    insertsql+=";";
                    System.out.println("已生拼接标号为"+j+"的数据");
                }
                int tt=0;
            }
            System.out.println("insertsql---"+insertsql);
            preparedStatement = connection.prepareStatement(insertsql);
            preparedStatement.execute();
            if (testDateTimesNum==num){
                System.out.println("大小关系已建立");
            }
            java.util.Date dateEnd = new java.util.Date();
            System.out.println("生成数据完成---共生成"+num+"条数据---"+"耗时---"+(dateEnd.getTime()-dateStart.getTime())+"毫秒");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            SQLUtils.release(connection,preparedStatement);
        }
    }
    //TODO 日期之间的比较做了么？
//    public void setAutoDateTimeR(String ... dateFields){
//
//        allVal.put("setAutoDateTime",dateFields);
//    }
//    public void setAutoDateR(String ... dateFields){
//        allVal.put("setAutoDateTime",dateFields);
//    }
    public void setAutoDateTimeInterval(String columnNameDateTime,String beginDateTime,String endDateTime){
        ArrayList<String> stringDateTime = new ArrayList<>();
        stringDateTime.add(beginDateTime);
        stringDateTime.add(endDateTime);
        allVal.put(columnNameDateTime,stringDateTime);
    }
    public void setAutoDateInterval(String columnNameDate,String beginDate,String endDate){
        ArrayList<String> stringDate = new ArrayList<>();
        stringDate.add(beginDate);
        stringDate.add(endDate);
        System.out.println("columnDName======="+stringDate.get(0));
        allVal.put(columnNameDate,stringDate);
    }
    public void setAutoUUIDInterval(String columnNameDate){
        ArrayList<String> stringDate = new ArrayList<>();
        stringDate.add("-");
        System.out.println("columnDName======="+stringDate.get(0));
        allVal.put(columnNameDate,stringDate);
    }
}

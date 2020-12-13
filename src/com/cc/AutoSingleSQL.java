package com.cc;
import org.joda.time.DateTime;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.cc.FieldDefinitionUtils.getColumnName;
import static com.cc.SQLUtils.*;

/**
 * @Classname com.cc.AutoSingleSQL
 * @Description TODO
 * @Date 2020/12/11 21:32
 * @Created by 2413776263@qq.com
 */
public class AutoSingleSQL {
    private static String tableName;
    private Integer num;
    static{
        SQLUtils.getDBConnection();
    }
    public AutoSingleSQL(String tableName, Integer num) {
        this.tableName = tableName;
        this.num = num;
    }
    //TODO 需要从url中获取数据库名称
    public void autoInsertS(){
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
                preparedStatement.executeUpdate();
                System.out.println("已生成编号为"+j+"的数据");
            }
            java.util.Date date2 = new java.util.Date();
            System.out.println("生成数据完成---共生成"+num+"条数据---"+"耗时---"+(date2.getTime()-date1.getTime())+"毫秒");
           SQLUtils.release(connection,preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void autoInsertS(String ... dateFields){
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
            int testDateTimesNum=0;
            for (int j = maxLine+1; j <= maxLine+num; j++) {
                Boolean datetimeFlagX=false;
                Boolean dateFlagX=false;
                TreeMap<Integer, HashMap<String, DateTime>> sortDateTime = TimeUtils.getSortDateOrDateTime(dateFields);
                HashMap<String, DateTime> testDateTimes = new HashMap<>();
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
                    if ("datetime".equals(typeName)&&temporaryList.contains(getColumnName(i))||"date".equals(typeName)&&temporaryList.contains(getColumnName(i))){
                        Boolean  temporaryFlag=false;
                        for (String df:
                             dateFields) {
                            DateTime ii=null;
                            for ( Map.Entry s:sortDateTime.entrySet()) {
                                if (s.getKey().equals(i)){
                                    HashMap<String, DateTime> value = (HashMap<String, DateTime>)s.getValue();
                                    testDateTimes.put(getColumnName(i),value.get(getColumnName(i)));
                                             if ("datetime".equals(typeName)){
                                                 preparedStatement.setTimestamp(i,new Timestamp(value.get(getColumnName(i)).getMillis()));
                                                 datetimeFlagX=true;
                                             }else if ("date".equals(typeName)){
                                                 preparedStatement.setDate(i,new Date(value.get(getColumnName(i)).getMillis()));
                                                 dateFlagX=true;
                                             }
                                             temporaryFlag=true;
                                             break;
                                         }
                            }
                            if (temporaryFlag){
                                break;
                            }

                        }
                        if (temporaryFlag){
                            temporaryFlag=false;
                            continue;
                        }
                    }
                    if ("datetime".equals(typeName)){
//                        System.out.println(2222);
                        if (datetimeFlagX){
                            continue;
                        }
                        preparedStatement.setTimestamp(i,  new Timestamp(TimeUtils.getDateTime().getMillis()));
                        continue;
                    }
                    if ("date".equals(typeName)){
                        if (dateFlagX){
                            continue;
                        }
                        preparedStatement.setDate(i,  new Date(TimeUtils.getDate().getMillis()));
                        continue;
                    }
                    if ("double".equals(typeName)){
                        continue;
                    }
                }
                int tt=0;
               DateTime max=new DateTime(1790,1,1,1,1,1,1);
                for (String y:
                     dateFields) {
                    if (testDateTimes.get(y).getMillis()>max.getMillis()){
                        tt++;
                        max=testDateTimes.get(y);
                    }
                }
                if (tt==testDateTimes.size()){
                    testDateTimesNum++;
                }
                preparedStatement.execute();
                sortDateTime=null;
                System.out.println("已生成编号为"+j+"的数据");
            }
            if (testDateTimesNum==num){
                System.out.println("大小关系已建立");
            }
            java.util.Date dateEnd = new java.util.Date();
            System.out.println("生成数据完成---共生成"+num+"条数据---"+"耗时---"+(dateEnd.getTime()-dateStart.getTime())+"毫秒");
             SQLUtils.release(connection,preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

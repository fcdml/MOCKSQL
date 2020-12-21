package com.cc.code.autoAllUtils.MiddleTableUtils;

import com.alibaba.fastjson.JSONObject;
import com.cc.code.Definition.FieldDefinitionUtils;
import com.cc.code.connectionUtils.SQLUtils;
import com.cc.code.fieldUtils.EnumUtils;
import com.cc.code.fieldUtils.TimeUtils;
import com.cc.code.fieldUtils.VarCharUtils;
import org.joda.time.DateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname AutoSingleUtils
 * @Description TODO
 * @Date 2020/12/17 9:32
 * @Created by 2413776263@qq.com
 */
public class AutoSingleUtils {
  private static  int j=0;
  public static Boolean getSingleTxt(String tableName,Integer num,Integer tableFieldNum) throws IOException {
    FileWriter fileWriter = new FileWriter("F:\\fourthlylearn\\learntryjdbc\\jdbcsome\\learnIO\\src\\file\\haha.txt",false);
    int maxLine = SQLUtils.getMaxLine(tableName);
    boolean hh=false;
    for (int j = maxLine+1; j <= maxLine+num; j++) {
      for (int i = 1; i <= tableFieldNum; i++) {
        String typeName= FieldDefinitionUtils.getDataType(tableName,i);
        Boolean anEnum = EnumUtils.isEnum(tableName,i);
        if (anEnum){
          Random random1 = new Random();
          ArrayList<String> columnType =FieldDefinitionUtils.getColumnType(tableName,i);
          //                            System.out.println("-----"+columnType.size());
//            preparedStatement.setString(i,columnType.get(random1.nextInt(columnType.size())));
          fileWriter.write(columnType.get(random1.nextInt(columnType.size()))+getsymbol( i,tableFieldNum));
          hh=true;
        }
        if (hh){
          hh=false;
          continue;
        }
        if (i == 1 && ("int".equals(typeName) || "Integer".equals(typeName))) {
//          preparedStatement.setString(i, String.valueOf(j));
          fileWriter.write(String.valueOf(j)+getsymbol( i,tableFieldNum));
          continue;
        }

        if ("varchar".equals(typeName)) {
//          preparedStatement.setString(i, VarCharUtils.getRandomString(6));
          fileWriter.write(VarCharUtils.getRandomString(6)+getsymbol( i,tableFieldNum));
          continue;
        }
        if ("datetime".equals(typeName)){
//          preparedStatement.setDate(i,  new Date(TimeUtils.getDateTime().getMillis()));
          fileWriter.write(new Date(TimeUtils.getDateTime().getMillis())+getsymbol( i,tableFieldNum));
          continue;
        }
        if ("date".equals(typeName)){
//          preparedStatement.setDate(i,  new Date(TimeUtils.getDate().getMillis()));
          fileWriter.write( new Date(TimeUtils.getDate().getMillis())+getsymbol( i,tableFieldNum));
          continue;
        }
        if ("double".equals(typeName)){
          continue;
        }
      }
      System.out.println("已插入txt文本中编号为"+j+"的数据");
    }
    fileWriter.close();
return true;
  }
  public static  String getsymbol(Integer i,Integer tableFieldNum){
    return i.equals(tableFieldNum) ?"\n":",";
  }
  public static  String getSingleValueSymbol(Integer i,Integer tableFieldNum){
    return i.equals(tableFieldNum) ?")":",";
  }
  public static  String getAllSymbol(Integer i,Integer allNum){
    return i.equals(allNum) ?";":",";
  }
  public static  String getSingleValue(ConcurrentHashMap<String, Object> allVal, Integer j, String[] dateFields, ArrayList<String> temporaryList, String tableName, Integer tableFieldNum) throws Exception {
    System.out.println(allVal+"------");
    //    JSONObject jsonObject = new JSONObject();
    boolean hh=false;
    Boolean datetimeFlagX=false;
    Boolean dateFlagX=false;
    TreeMap<Integer, HashMap<String, DateTime>> sortDateTime = TimeUtils.getSortDateOrDateTime(tableName,dateFields);
    HashMap<String, DateTime> testDateTimes = new HashMap<>();
    String begin="(";
    for (int i = 1; i <= tableFieldNum; i++) {
      String typeName=FieldDefinitionUtils.getDataType(tableName,i);
      Boolean anEnum = EnumUtils.isEnum(tableName,i);
      if (anEnum){
        Random random1 = new Random();
        ArrayList<String> columnType =FieldDefinitionUtils.getColumnType(tableName,i);
            begin+="'"+columnType.get(random1.nextInt(columnType.size()))+"'"+getSingleValueSymbol(i,tableFieldNum);
          hh=true;
      }
      if (hh){
        hh=false;
        continue;
      }
      if (i == 1 && ("int".equals(typeName) || "Integer".equals(typeName))) {
        begin+=String.valueOf(j)+getSingleValueSymbol(i,tableFieldNum);
        continue;
      }
      if ("varchar".equals(typeName)) {
        begin+="'"+VarCharUtils.getRandomString(6)+"'"+getSingleValueSymbol(i,tableFieldNum);
        continue;
      }
      if ("datetime".equals(typeName)&&temporaryList.contains(FieldDefinitionUtils.getColumnName(tableName,i))||"date".equals(typeName)&&temporaryList.contains(FieldDefinitionUtils.getColumnName(tableName,i))){
        Boolean  temporaryFlag=false;
        for (String df:
                dateFields) {
          DateTime ii=null;
          for ( Map.Entry s:sortDateTime.entrySet()) {
            if (s.getKey().equals(i)){
              HashMap<String, DateTime> value = (HashMap<String, DateTime>)s.getValue();
              testDateTimes.put(FieldDefinitionUtils.getColumnName(tableName,i),value.get(FieldDefinitionUtils.getColumnName(tableName,i)));
              if ("datetime".equals(typeName)){
                begin+="'"+(new Timestamp(value.get(FieldDefinitionUtils.getColumnName(tableName,i)).getMillis()))+"'"+getSingleValueSymbol(i,tableFieldNum);
                datetimeFlagX=true;
              }else if ("date".equals(typeName)){
                begin+="'"+(new Date(value.get(FieldDefinitionUtils.getColumnName(tableName,i)).getMillis()))+"'"+getSingleValueSymbol(i,tableFieldNum);
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
        if (datetimeFlagX){
          continue;
        }
        if (allVal.get("setAutoDateTimeInterval")!=null){
          ArrayList<String> setAutoDateTimeInterval = (ArrayList<String>)allVal.get("setAutoDateTimeInterval");
          if (setAutoDateTimeInterval.get(0).equals(FieldDefinitionUtils.getColumnName(tableName,i))){
            DateTime datTimeByInterval = TimeUtils.getDatTimeByInterval(setAutoDateTimeInterval.get(1), setAutoDateTimeInterval.get(2));
            begin += "'" + (new Timestamp(datTimeByInterval.getMillis())) + "'" + getSingleValueSymbol(i, tableFieldNum);
          }
        }else {
          begin+="'"+(new Timestamp(TimeUtils.getDateTime().getMillis()))+"'"+getSingleValueSymbol(i,tableFieldNum);
        }
//        preparedStatement.setTimestamp(i,  new Timestamp(TimeUtils.getDateTime().getMillis()));
        continue;
      }
      if ("date".equals(typeName)){
        if (dateFlagX){
          continue;
        }
        begin+="'"+(new Date(TimeUtils.getDate().getMillis()))+"'"+getSingleValueSymbol(i,tableFieldNum);
//        preparedStatement.setDate(i,  new Date(TimeUtils.getDate().getMillis()));
        continue;
      }
      if ("double".equals(typeName)){
        continue;
      }
    }
    if (j==1){
      System.out.println("AutoSingleUtils.getSingleValue  begin="+begin);
    }
    return begin;
  }
  public static  String getSingleValueNoRelation(ConcurrentHashMap<String, Object> allVal, Integer j, String tableName, Integer tableFieldNum) throws Exception {
    System.out.println(allVal+"------");
    //    JSONObject jsonObject = new JSONObject();
    boolean hh=false;
    Boolean datetimeFlagX=false;
    Boolean dateFlagX=false;
    HashMap<String, DateTime> testDateTimes = new HashMap<>();
    String begin="(";
    for (int i = 1; i <= tableFieldNum; i++) {
      String typeName=FieldDefinitionUtils.getDataType(tableName,i);
      Boolean anEnum = EnumUtils.isEnum(tableName,i);
      if (anEnum){
        Random random1 = new Random();
        ArrayList<String> columnType =FieldDefinitionUtils.getColumnType(tableName,i);
            begin+="'"+columnType.get(random1.nextInt(columnType.size()))+"'"+getSingleValueSymbol(i,tableFieldNum);
          hh=true;
      }
      if (hh){
        hh=false;
        continue;
      }
      if (i == 1 && ("int".equals(typeName) || "Integer".equals(typeName))) {
        begin+=String.valueOf(j)+getSingleValueSymbol(i,tableFieldNum);
        continue;
      }
      if ("varchar".equals(typeName)) {
        begin+="'"+VarCharUtils.getRandomString(6)+"'"+getSingleValueSymbol(i,tableFieldNum);
        continue;
      }
      if ("datetime".equals(typeName)){
        if (datetimeFlagX){
          continue;
        }
        if (allVal.containsKey(FieldDefinitionUtils.getColumnName(tableName,i))){
          ArrayList<String> s=(ArrayList<String>)allVal.get(FieldDefinitionUtils.getColumnName(tableName,i));
            DateTime datTimeByInterval = TimeUtils.getDatTimeByInterval(s.get(0), s.get(1));
            begin += "'" + (new Timestamp(datTimeByInterval.getMillis())) + "'" + getSingleValueSymbol(i, tableFieldNum);
        }else {
          begin+="'"+(new Timestamp(TimeUtils.getDateTime().getMillis()))+"'"+getSingleValueSymbol(i,tableFieldNum);
        }
//        preparedStatement.setTimestamp(i,  new Timestamp(TimeUtils.getDateTime().getMillis()));
        continue;
      }
      if ("date".equals(typeName)){
        if (dateFlagX){
          continue;
        }
        if (allVal.containsKey(FieldDefinitionUtils.getColumnName(tableName,i))){
          ArrayList<String> s=(ArrayList<String>)allVal.get(FieldDefinitionUtils.getColumnName(tableName,i));
          DateTime datTimeByInterval = TimeUtils.getDateByInterval(s.get(0), s.get(1));
          begin += "'" + (new Date(datTimeByInterval.getMillis())) + "'" + getSingleValueSymbol(i, tableFieldNum);
        }else {
          begin += "'" + (new Date(TimeUtils.getDate().getMillis())) + "'" + getSingleValueSymbol(i, tableFieldNum);
        }
//        preparedStatement.setDate(i,  new Date(TimeUtils.getDate().getMillis()));
        continue;
      }
      if ("double".equals(typeName)){
        continue;
      }
    }
    if (j==1){
      System.out.println("AutoSingleUtils.getSingleValue  begin="+begin);
    }
    return begin;
  }
  public static DateTime getDateTime(String needDateTime){
    if (needDateTime.contains("T")){
      DateTime dateTime = new DateTime(needDateTime);
      return dateTime;
    }
    if (needDateTime.contains(" ")){
      String[] s = needDateTime.split(" ");
      System.out.println("s--------"+Arrays.toString(s));
      System.out.println(s[0]+"T"+s[1]);
   return  new DateTime(s[0]+"T"+s[1]);
    }
    return null;
  }
  public static String[] getStringDateTime(String needDateTime){
    if (needDateTime.contains("T")){
      String[] ts = needDateTime.split("T");
      return ts;
    }
    if (needDateTime.contains(" ")){
      String[] s = needDateTime.split(" ");
      return s;
    }
    return null;
  }
  public static String[] getStringSplitDate(String needDate){
    String[] split = needDate.split("-");
    return split;
  }
  public static String[] getStringSplitTime(String needTime){
    String[] split = needTime.split(":");
    return split;
  }
}

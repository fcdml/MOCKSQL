package com.cc.code.autoAllUtils.MiddleTableUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.code.Definition.DbForFieldDefinition;
import com.cc.code.connectionUtils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Classname AutoMiddleUtils
 * @Description TODO
 * @Date 2020/12/13 21:02
 * @Created by 2413776263@qq.com
 */
public class AutoMiddleUtils{
  public static JSONObject getRowConnection(String tableName,String columnName){
      ArrayList<String> strings = new ArrayList<>();
      Connection dbConnection =null;
      PreparedStatement preparedStatement =null;
      try {
          dbConnection = SQLUtils.getDBConnection();
          String selectSql="select "+columnName+" from "+tableName;
          preparedStatement = dbConnection.prepareStatement(selectSql);
          ResultSet resultSet = preparedStatement.executeQuery();
          DbForFieldDefinition dbForFieldDefinition = new DbForFieldDefinition();
          String singleFieldDefinition = dbForFieldDefinition.getSingleFieldDefinition(tableName, columnName);
          JSONArray  integers  = new JSONArray();
          JSONArray varcharList = new JSONArray();
          JSONArray enumList = new JSONArray();
          JSONObject jsonObject = new JSONObject();
          if ("int".equals(singleFieldDefinition)){
                while (resultSet.next()) {
                    integers.add(resultSet.getInt(1));
                }
             return jsonObject.fluentPut(columnName,integers)
                              .fluentPut("dataType","int");

          }
            if ("varchar".equals(singleFieldDefinition)){
                while (resultSet.next()) {
                    varcharList.add(resultSet.getString(1));
                }
            return jsonObject.fluentPut(columnName,varcharList).fluentPut("dataType","varchar");

            }
            if ("enum".equals(singleFieldDefinition)){
                while (resultSet.next()) {
                    enumList.add(resultSet.getString(1));
                }
            return jsonObject.fluentPut(columnName,enumList).fluentPut("dataType","enum");
            }
      } catch (SQLException throwables) {
          throwables.printStackTrace();
      }finally {
          SQLUtils.release(dbConnection,preparedStatement);
      }
      return null;
  }
}

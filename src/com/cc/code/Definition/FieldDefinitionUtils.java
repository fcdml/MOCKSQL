package com.cc.code.Definition;

import com.alibaba.fastjson.JSONObject;
import com.cc.code.Definition.DbForFieldDefinition;

import java.util.ArrayList;

/**
 * @Classname FieldDefinitionUtils
 * @Description TODO
 * @Date 2020/12/12 23:01
 * @Created by 2413776263@qq.com
 */
public class FieldDefinitionUtils {
    public  static String getDataType(String tableName,int i){
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        for (int j = 0; j <allFieldDefinition.size() ; j++) {
            if (allFieldDefinition.get(j).getInteger("ordinalPosition")==i){
                return allFieldDefinition.get(j).getString("dataType");
            }
        }
        String typeName=(String)allFieldDefinition.get(i-1).get("dataType");
        return typeName;
    }
    public  static ArrayList<String> getColumnType(String tableName,int i){
        ArrayList<String> columnType =null;
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        for (JSONObject s: allFieldDefinition) {
            if (s.getIntValue("ordinalPosition")==i&&"enum".equals(s.getString("dataType"))){
                 columnType =(ArrayList<String>)s.get("columnType");
                break;
            }
        }
        return columnType;
    }
    public  static String getColumnName(String tableName,int i){
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        for (int j = 0; j <allFieldDefinition.size() ; j++) {
            if (allFieldDefinition.get(j).getInteger("ordinalPosition")==i){
                return allFieldDefinition.get(j).getString("columnName");
            }
        }
        return null;
    }
}

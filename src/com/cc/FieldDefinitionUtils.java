package com.cc;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * @Classname FieldDefinitionUtils
 * @Description TODO
 * @Date 2020/12/12 23:01
 * @Created by 2413776263@qq.com
 */
public class FieldDefinitionUtils {
    public  static String getDataType(int i){
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition();
        String typeName=(String)allFieldDefinition.get(i-1).get("dataType");
        return typeName;
    }
    public  static ArrayList<String> getColumnType(int i){
        ArrayList<String> columnType =null;
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition();
        for (JSONObject s: allFieldDefinition) {
            if (s.getIntValue("ordinalPosition")==i&&"enum".equals(s.getString("dataType"))){
                 columnType =(ArrayList<String>)s.get("columnType");
                break;
            }
        }
        return columnType;
    }
    public  static String getColumnName(int i){
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition();
        String typeName=(String)allFieldDefinition.get(i-1).get("columnName");
        return typeName;
    }
}

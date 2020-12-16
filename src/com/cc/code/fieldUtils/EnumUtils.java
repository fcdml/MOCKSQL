package com.cc.code.fieldUtils;
import com.alibaba.fastjson.JSONObject;
import com.cc.code.Definition.DbForFieldDefinition;

import java.util.ArrayList;
/**
 * @Classname EnumUtils
 * @Description TODO
 * @Date 2020/12/12 22:46
 * @Created by 2413776263@qq.com
 */
public class EnumUtils {
    // i:当前遍历到的字段下标
    public static Boolean isEnum(String tableName,int i){
        DbForFieldDefinition dbForEnum = new DbForFieldDefinition();
        ArrayList<JSONObject> allFieldDefinition = dbForEnum.getAllFieldDefinition(tableName);
        String typeName=(String)allFieldDefinition.get(i-1).get("dataType");
//                    System.out.println("typeName"+typeName);
        Boolean flag=false;
        for (JSONObject s:
                allFieldDefinition) {
            if (s.getIntValue("ordinalPosition")==i&&"enum".equals(s.getString("dataType")))
            {
                flag=true;
               break;
            }
        }
        return flag;
    }
}

package test;
import com.alibaba.fastjson.JSONObject;
import com.cc.code.autoAllSQL.AutoMiddleSQL;

import java.util.LinkedHashMap;

/**
 * @Classname testMiddleAutoSQLDemo
 * @Description TODO
 * @Date 2020/12/14 11:22
 * @Created by 2413776263@qq.com
 */
public class testMiddleAutoSQLDemo {
    public static void main(String[] args) {
        getStringAndEnum();
    }
    public static void getIntAndInt(){
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.fluentPut("user","id").fluentPut("deptsome","did");
        AutoMiddleSQL uu = new AutoMiddleSQL("uu", jsonObject);
        uu.autoInsertM();
    }
    public static void getIntAndString(){
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.fluentPut("user","id").fluentPut("employee","ename");
        System.out.println(jsonObject);
        AutoMiddleSQL uu = new AutoMiddleSQL("emi", jsonObject);
        uu.autoInsertM();
    }
    public static void getIndAndEnum(){
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.fluentPut("user","id").fluentPut("deptsome","dept");
        AutoMiddleSQL uu = new AutoMiddleSQL("ud", jsonObject);
        uu.autoInsertM();
    } public static void getStringAndEnum(){
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.fluentPut("employee","ename").fluentPut("deptsome","dept");
        AutoMiddleSQL uu = new AutoMiddleSQL("de", jsonObject);
        uu.autoInsertM();
    }
}

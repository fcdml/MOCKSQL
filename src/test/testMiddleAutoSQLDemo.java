package test;
import com.alibaba.fastjson.JSONObject;
import com.cc.AutoMiddleSQL;
/**
 * @Classname testMiddleAutoSQLDemo
 * @Description TODO
 * @Date 2020/12/14 11:22
 * @Created by 2413776263@qq.com
 */
public class testMiddleAutoSQLDemo {
    public static void main(String[] args) {
      getIntAndInt();
    }
    public static void getIntAndInt(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("user","id").fluentPut("deptsome","did");
        AutoMiddleSQL uu = new AutoMiddleSQL("uu", jsonObject);
        uu.autoInsertM();
    }
    public static void getIntAndString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("user","id").fluentPut("employee","ename");
        AutoMiddleSQL uu = new AutoMiddleSQL("emi", jsonObject);
        uu.autoInsertM();
    }
    public static void getIndAndEnum(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("user","id").fluentPut("deptsome","dept");
        AutoMiddleSQL uu = new AutoMiddleSQL("ud", jsonObject);
        uu.autoInsertM();
    }
}

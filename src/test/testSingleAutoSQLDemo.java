package test;

import com.cc.code.autoAllSQL.AutoSingleSQL;

/**
 * @Classname testSingleAutoSQLDemo
 * @Description TODO
 * @Date 2020/12/13 14:03
 * @Created by 2413776263@qq.com
 */
public class testSingleAutoSQLDemo {
    public static void main(String[] args) {
        //deptsome forjdbc
        AutoSingleSQL forjdbc = new AutoSingleSQL("forjdbc", 100);
        forjdbc.autoInsertS();
//        forjdbc.autoInsertS("createdate","enddate","afterdate","mydate","aa","bb","cc","dd");
    }
}
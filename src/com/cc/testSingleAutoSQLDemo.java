package com.cc;

/**
 * @Classname testSingleAutoSQLDemo
 * @Description TODO
 * @Date 2020/12/13 14:03
 * @Created by 2413776263@qq.com
 */
public class testSingleAutoSQLDemo {
    public static void main(String[] args) {
        AutoSingleSQL forjdbc = new AutoSingleSQL("forjdbc", 100000);
        forjdbc.autoInsertS("createdate","enddate","afterdate","mydate","aa","bb","cc","dd");
    }
}

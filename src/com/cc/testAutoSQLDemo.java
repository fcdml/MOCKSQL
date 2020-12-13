package com.cc;

/**
 * @Classname testAutoSQLDemo
 * @Description TODO
 * @Date 2020/12/13 14:03
 * @Created by 2413776263@qq.com
 */
public class testAutoSQLDemo{
    public static void main(String[] args) {
        AutoSQL forjdbc = new AutoSQL("forjdbc", 10);
        forjdbc.AutoInsert("createdate","enddate","afterdate","mydate","aa","bb","cc","dd");
    }
}

package com.cc;

import java.sql.Connection;

/**
 * @Classname AutoMiddleUtils
 * @Description TODO
 * @Date 2020/12/13 21:02
 * @Created by 2413776263@qq.com
 */
public class AutoMiddleUtils{
    static{
        Connection dbConnection = SQLUtils.getDBConnection();
    }



}

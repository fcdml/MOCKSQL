package com.cc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Random;

/**
 * @Classname com.cc.DBUitls
 * @Description TODO
 * @Date 2020/12/11 21:32
 * @Created by 2413776263@qq.com
 */
public class DBUitls {
    private static String url = "jdbc:mysql://localhost:3306/newframework?useSSL=false&characterEncoding=utf8&serverTimeZone=GMT+8";
    private static String username = "root";
    private static String password = "mysql9614";
    private Class aclass;
    private Integer num;

    public DBUitls(Class aclass, Integer num) {
        this.aclass = aclass;
        this.num = num;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Date date = new Date((new java.util.Date()).getTime());
            PreparedStatement preparedStatement = connection.prepareStatement("insert into forjdbc values (?,?,?,?,?)");
            int varnum = Class.forName(aclass.getName()).getDeclaredFields().length;
            Field[] declaredFields = Class.forName(aclass.getName()).getDeclaredFields();
            String[] strings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            Random random = new Random();
            int q = 0;// declaredFields数组下表



            for (int j = 0; j < num; j++) {
                for (int i = 1; i <= varnum; i++) {
                    String typeName = declaredFields[i - 1].getType().getTypeName();
                    if (i == 1 && ("int".equals(typeName) || "Integer".equals(typeName))) {
                        preparedStatement.setString(i, String.valueOf(i));
                        continue;
                    }
                    if ("java.lang.String".equals(typeName)) {
                            String u = "";
                            for (int k = 0; k < 6; k++) {
                                String string = strings[random.nextInt(26)];
                                u += string;
                            }
                            preparedStatement.setString(i, u);
                     continue;
                    }
                    if ("java.util.Date".equals(typeName)){




                        continue;
                    }
                    if (" java.lang.Double".equals(typeName)){
                        continue;
                    }
                }
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库的驱动校验失败!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Date date = new Date((new java.util.Date()).getTime());
            PreparedStatement preparedStatement = connection.prepareStatement("insert into forjdbc values (?,?,?,?,?)");
            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, "aa");
            preparedStatement.setString(3, "qq");
            preparedStatement.setString(4, date.toString());
            preparedStatement.setString(5, date.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库的驱动校验失败!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

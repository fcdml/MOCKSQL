package com.cc.code.connectionUtils;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Classname DruidUtils
 * @Description TODO
 * @Date 2020/12/13 13:26
 * @Created by 2413776263@qq.com
 */
public class DruidUtils {
    private static DruidDataSource dataSource = null;  // druid数据源


    static {

        Properties props = new Properties();
        try {
            String driverClassName = "com.mysql.cj.jdbc.Driver";
            String jdbcUrl ="jdbc:mysql://localhost:3306/mocksql?useSSL=false&characterEncoding=utf8&serverTimeZone=GMT+8";
            String username ="root";
            String password = "mysql9614";
            int initialSize = 10;
            int maxActive =  50;
            //创建并初始化数据库连接池 DruidDataSource
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(driverClassName);  // 配置数据库驱动类
            dataSource.setUrl(jdbcUrl);  // 配置连接MySQL的url
            dataSource.setUsername(username); // 配置数据库用户名
            dataSource.setPassword(password); // 配置数据库密码
            dataSource.setInitialSize(initialSize);  // 配置数据库连接池的初始化连接数量
            dataSource.setMaxActive(maxActive); // 配置数据库连接池最大活跃的连接数量
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 从数据库连接池中取出一个新的连接
     * @return
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放资源的方法(查询)
     * @param conn   数据库连接
     * @param statement  预编译对象
     * @param rs 结果集对象
     */
    public static void release(Connection conn, Statement statement, ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();  // 销毁结果集对象
            }
            if(statement != null) {
                statement.close();  // 销毁预编译对象
            }
            if(conn !=null) {
                conn.close();  // 归还数据库连接到druid连接池
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源的方法(添加删除修改)
     * @param conn   数据库连接
     * @param statement  预编译对象
     */
    public static void release(Connection conn, Statement statement) {
        release(conn, statement, null);
    }
}

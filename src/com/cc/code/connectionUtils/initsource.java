package com.cc.code.connectionUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname initsource
 * @Description TODO
 * @Date 2020/12/20 1:00
 * @Created by 2413776263@qq.com
 */
public class initsource {

    private String jdbcUrl=null;
    private String username=null;
    private String password=null;
    private  ConcurrentHashMap<String, String> st =null;
    public initsource() {
    }
    public initsource(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        st = new ConcurrentHashMap<>();
        st.put("jdbcUrl",this.jdbcUrl);
        st.put("username",this.username);
        st.put("password",this.password);
    }

    public ConcurrentHashMap<String, String> getSt() {
        return st;
    }
}

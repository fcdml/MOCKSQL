package com.cc;

import java.util.Date;

/**
 * @Classname com.cc.FORJDBC
 * @Description TODO
 * @Date 2020/12/11 22:03
 * @Created by 2413776263@qq.com
 */
public class FORJDBC {
public int id;
public String username;
public String password;
public Date createdate;
public Date enddate;
    public FORJDBC() {
    }
    public FORJDBC(int id, String username, String password, Date createdate, Date enddate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdate = createdate;
        this.enddate = enddate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Override
    public String toString() {
        return "com.cc.FORJDBC{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdate=" + createdate +
                ", enddate=" + enddate +
                '}';
    }
}

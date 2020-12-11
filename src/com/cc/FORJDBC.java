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
public String status;
    public FORJDBC() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FORJDBC{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdate=" + createdate +
                ", enddate=" + enddate +
                ", status='" + status + '\'' +
                '}';
    }
}

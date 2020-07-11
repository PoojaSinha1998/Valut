package com.example.dell.passwords.Pojo;

public class RecordsPojo {



    String idnumber, Name,userId,userPassword,des;

    public RecordsPojo(String name, String userId, String userPassword,String des) {
        Name = name;
        this.userId = userId;
        this.userPassword = userPassword;
        this.des = des;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

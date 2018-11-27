package com.easybug.model;

import java.util.List;

public class User {
   private Integer uId;
   private String uName;
   private Integer gender;
   private String rName;//真实姓名
   private String password;
   private List<UserSchool> userSchools;

    public List<UserSchool> getUserSchools() {
        return userSchools;
    }

    public void setUserSchools(List<UserSchool> userSchools) {
        this.userSchools = userSchools;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

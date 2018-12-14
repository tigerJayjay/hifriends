package com.easybug.model;

import java.util.List;

public class UserSchool {
    private Integer id;
    private Integer uId;
    private Integer schoolId;
    private List<User> users;
    private List<SchoolInfo> schoolInfos;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<SchoolInfo> getSchoolInfos() {
        return schoolInfos;
    }

    public void setSchoolInfos(List<SchoolInfo> schoolInfos) {
        this.schoolInfos = schoolInfos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}

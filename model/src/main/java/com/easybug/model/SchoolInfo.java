package com.easybug.model;

import java.util.List;

public class SchoolInfo {
    private Integer id;
    private String schoolName;
    private String classNo;
    private String grade;
    private String province;
    private String city;
    private String profession;
    private List<UserSchool> userSchool;

    public List<UserSchool> getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(List<UserSchool> userSchool) {
        this.userSchool = userSchool;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}

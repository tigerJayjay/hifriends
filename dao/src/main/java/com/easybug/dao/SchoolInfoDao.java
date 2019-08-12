package com.easybug.dao;

import com.easybug.model.SchoolInfo;

import java.util.List;

public interface SchoolInfoDao {
    public SchoolInfo selectInfoById(Integer schoolId);
    public List<SchoolInfo> selectListByUid(Integer uId);
}

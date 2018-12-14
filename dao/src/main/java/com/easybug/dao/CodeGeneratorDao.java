package com.easybug.dao;

import java.util.HashMap;

public interface CodeGeneratorDao {
    public void generatorCode(HashMap conditon);
    public Integer getLeftCount();
    public Integer getNewCode();
    public Integer getMaxCode();
    public Integer updateStatus(Integer code);
}

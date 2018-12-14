package com.easybug.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static Properties props;
    private static String filePath;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("code.properties");
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("文件加载异常",500);
        }finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                throw new SysException("文件关闭异常",500);
            }
        }
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
    public  static void setFilePath(String path){
        filePath = path;
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
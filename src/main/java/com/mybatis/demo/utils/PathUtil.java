package com.mybatis.demo.utils;


import com.mybatis.demo.constant.SessionConstant;
import org.springframework.util.StringUtils;

public class PathUtil {

    public static String getFullPath(String path) {
        if (path == null) {
            path = "";
        }
        String urlPrefix = SessionConstant.APP_URL_PREFIX;
        if (!StringUtils.isEmpty(urlPrefix)) {
            urlPrefix += "/";
        }

        return AppContext.getContextPath() + "/" + urlPrefix + path;
    }

}

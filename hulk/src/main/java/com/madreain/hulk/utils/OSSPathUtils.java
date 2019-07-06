package com.madreain.hulk.utils;

/**
 * @author madreain
 * @date 2019-07-04.
 * module： OSS工具类
 * description：
 */
public class OSSPathUtils {

    public static String getOSSPath(String path) {
        if (!StringUtils.isEmpty(path)) {
            if (path.contains("htmlstore")) {
//                path = path + "?x-oss-process=image/resize,p_" + resize;
//                path = path + "?x-oss-process=image/resize,h_600,p_70";
            }
        }
        return path;
    }
}

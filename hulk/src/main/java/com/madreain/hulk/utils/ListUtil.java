package com.madreain.hulk.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author madreain
 * @date 2019-07-04.
 * module： 集合判断工具类
 * description：
 */
public class ListUtil {

    /**
     * 判断集合list数据是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(List<?> list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    /**
     * 判断集合list数据是否为非空
     *
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static void clearList(List<?> list) {
        if (!isEmpty(list)) {
            list.clear();
        }
    }

    /**
     * 获取集合list数据长度
     *
     * @param list
     * @return
     */
    public static <T> int getCount(List<T> list) {
        return list == null ? 0 : list.size();
    }

    /**
     * 反转数据
     *
     * @param sourceList
     * @return
     */
    public static <V> List<V> invertList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return sourceList;
        }

        List<V> invertList = new ArrayList<V>(sourceList.size());
        for (int i = sourceList.size() - 1; i >= 0; i--) {
            invertList.add(sourceList.get(i));
        }
        return invertList;
    }
}

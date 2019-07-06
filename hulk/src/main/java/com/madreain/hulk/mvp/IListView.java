package com.madreain.hulk.mvp;

import java.util.List;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public interface IListView<T> extends IView {

    /**
     * 刷新结束
     */
    void refreshComplete();

    /**
     * 显示数据
     * @param datas
     * @param pageNum
     */
    void showListData(List<T> datas, int pageNum);

}

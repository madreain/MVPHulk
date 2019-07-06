package com.madreain.hulk.view.varyview;

import android.content.Context;
import android.view.View;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：变化view辅助接口
 */
public interface IVaryViewHelper {

    /**
     * 当前显示的view
     * @return
     */
    View getCurrentView();

    /**
     * 需替换的布局view
     * @return
     */
    View getView();

    /**
     * 恢复view
     */
    void restoreView();

    /**
     * 设置要展示view
     * @param view
     */
    void showView(View view);

    /**
     * 设置要加载布局的layoutId
     * @param layoutId
     * @return
     */
    View inflate(int layoutId);

    /**
     * 上下文
     * @return
     */
    Context getContext();

}

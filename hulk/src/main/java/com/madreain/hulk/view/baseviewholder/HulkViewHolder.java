package com.madreain.hulk.view.baseviewholder;

import android.support.annotation.IntDef;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：继承BaseViewHolder，自定义通用方法
 *
 * https://www.jianshu.com/p/b343fcff51b0根据里面的注意事项介绍：
 * 需要单独建一个外部类继承BaseViewHolder，否则部分机型会出现ClassCastException，
 * 如果是内部类的构造方法要是public，定义的那个类也最好是public。
 */
public class HulkViewHolder extends BaseViewHolder {

    public HulkViewHolder(View view) {
        super(view);
    }

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }

    public HulkViewHolder setVisibility(int viewId, @Visibility int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

}

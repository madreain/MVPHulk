package com.madreain.mvphulk.module.Common;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.madreain.hulk.ui.BaseDialogFragment;
import com.madreain.hulk.utils.StringUtils;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterKey;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author madreain
 * @date module：
 * description：
 */
public class CommonDialogFragment extends BaseDialogFragment<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.fragment_common)
    FrameLayout prelativeLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.v_horizontal)
    View vHorizontal;
    @BindView(R.id.v_vertical)
    View vVertical;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;

    String title;
    String desc;
    String left;
    String right;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        Window window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.CommonDialog);
        WindowManager.LayoutParams params = window.getAttributes();
        //背景透明
        params.dimAmount = 0.4f;
        params.gravity = Gravity.CENTER;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //数据的获取
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(ARouterKey.CommonTitle);
            desc = bundle.getString(ARouterKey.CommonDesc);
            left = bundle.getString(ARouterKey.CommonLeft);
            right = bundle.getString(ARouterKey.CommonRight);
        }
        //样式的展示
        //标题
        if (StringUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        //内容
        if (StringUtils.isEmpty(desc)) {
            tvDesc.setVisibility(View.GONE);
        } else {
            tvDesc.setText(desc);
        }
        //左边隐藏
        if (StringUtils.isEmpty(left)) {
            tvLeft.setVisibility(View.GONE);
            vVertical.setVisibility(View.GONE);
        } else {
            tvLeft.setText(left);
        }
        //右边隐藏
        if (StringUtils.isEmpty(right)) {
            tvRight.setVisibility(View.GONE);
            vVertical.setVisibility(View.GONE);
        } else {
            tvRight.setText(right);
        }
        //初始化
        presenter.onStart();
    }

    @Override
    public View getReplaceView() {
        return prelativeLayout;
    }

    @OnClick({R.id.tv_left, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                if (onLeftRightClickListener != null) {
                    onLeftRightClickListener.onLeftClick();
                }
                dismiss();
                break;
            case R.id.tv_right:
                if (onLeftRightClickListener != null) {
                    onLeftRightClickListener.onRightClick();
                }
                dismiss();
                break;
        }
    }

    private onLeftRightClickListener onLeftRightClickListener;

    public interface onLeftRightClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public void setOnLeftRightClickListener(onLeftRightClickListener onLeftRightClickListener) {
        this.onLeftRightClickListener = onLeftRightClickListener;
    }

}

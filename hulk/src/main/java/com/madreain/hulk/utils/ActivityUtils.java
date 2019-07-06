package com.madreain.hulk.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：activity 管理工具类
 */

public class ActivityUtils {
    private static ActivityUtils activityUtils;
    private Stack<Activity> activityStack;

    public static ActivityUtils get() {
        if (activityUtils == null) {
            synchronized (ActivityUtils.class) {
                if (activityUtils == null) {
                    activityUtils = new ActivityUtils();
                }
            }
        }
        return activityUtils;
    }

    /**
     * 获取activityStack
     *
     * @return
     */
    public Stack<Activity> getActivityStack() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        return activityStack;
    }

    /**
     * 添加
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        getActivityStack().add(activity);
    }

    /**
     * 移除
     *
     * @param activity
     */
    public void remove(Activity activity) {
        getActivityStack().remove(activity);
    }

    /**
     * 关闭指定界面
     *
     * @param activity 要关闭的Activity
     */
    public void finish(Activity activity) {
        if (activity != null) {
            activity.finish();
            getActivityStack().remove(activity);
        }
    }

    public boolean isActivityExist(Class<? extends Activity> clz) {
        return getActivityStack().contains(getActivity(clz));
    }

    public boolean isEmpty() {
        return getActivityStack().isEmpty();
    }

    /**
     * 关闭指定界面
     *
     * @param clz 要关闭的Activity.class
     */
    public void finish(Class<? extends Activity> clz) {
        finish(getActivity(clz));
    }

    public void finishAll() {
        while (getActivityStack().size() != 0) {
            finish(getActivityStack().peek());
        }
//        MobclickAgent.onKillProcess(Utils.getContext());
    }

    /**
     * 结束其他Activity
     *
     * @param clz 当前Activity.class 不需要finish
     */
    public void finishOthers(Class<? extends Activity> clz) {
        Activity activity = getActivity(clz);
        finishOthers(activity);
    }

    /**
     * 结束其他Activity
     *
     * @param activity 当前Activity 不需要finish
     */
    public void finishOthers(Activity activity) {
        for (Activity a : getActivityStack()) {
            if (activity != a && a != null) {
                a.finish();
            }
        }
        getActivityStack().clear();
        getActivityStack().add(activity);
    }

    /**
     * 根据className 获取Activity对象
     *
     * @param clz Class
     */
    public Activity getActivity(Class<? extends Activity> clz) {
        for (Activity activity : getActivityStack()) {
            if (activity != null && activity.getClass().equals(clz)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 回退到指定的Activity.class
     *
     * @param clz Activity.class
     */
    public void backTo(Class<? extends Activity> clz) {
        if (getActivityStack().size() > 0) {
            Activity activity = getActivityStack().peek();
            while (activity != null && !clz.equals(activity.getClass())) {
                activity.finish();
                getActivityStack().remove(activity);
                if (getActivityStack().size() > 0) {
                    activity = getActivityStack().peek();
                }
            }
        }
    }

    /**
     * 判断activity是否是最顶部Activity
     *
     * @param activity 需要判断的activity
     * @return
     */
    public boolean isTopActivity(Activity activity) {
        return getActivityStack().size() != 0 && getActivityStack().peek() == activity;
    }
}

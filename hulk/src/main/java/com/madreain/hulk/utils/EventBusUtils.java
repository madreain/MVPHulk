package com.madreain.hulk.utils;


import org.greenrobot.eventbus.EventBus;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：EventBus工具类的封装
 * description：
 */

public class EventBusUtils {

    private EventBusUtils() {

    }

    /**
     * 注册EventBus
     *
     * @param subscriber 订阅者对象
     */
    public static void register(Object subscriber) {
        if (!isRegister(subscriber)) {
            try {
                EventBus.getDefault().register(subscriber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消注册EventBus
     *
     * @param subscriber 订阅者对象
     */
    public static void unRegister(Object subscriber) {
        if (!isRegister(subscriber)) {
            try {
                EventBus.getDefault().unregister(subscriber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 是否已经注册
     * @param subscriber
     * @return
     */
    private static boolean isRegister(Object subscriber) {
        return EventBus.getDefault().isRegistered(subscriber);
    }

    /**
     * 发布订阅事件
     *
     * @param event 事件对象
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发布粘性订阅事件
     *
     * @param event 事件对象
     */
    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 移除指定的粘性订阅事件
     *
     * @param eventType class的字节码，例如：String.class
     */
    public static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
    }

    /**
     * 移除所有的粘性订阅事件
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 取消事件传送
     *
     * @param event 事件对象
     */
    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

}

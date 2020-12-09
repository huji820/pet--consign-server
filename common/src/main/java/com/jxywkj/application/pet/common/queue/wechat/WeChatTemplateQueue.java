package com.jxywkj.application.pet.common.queue.wechat;

import java.util.LinkedList;

/**
 * @Author LiuXiangLin
 * @Description 微信模板消息队列
 * @Date 14:40 2019/8/27
 * @Param
 * @return
 **/
public class WeChatTemplateQueue {

    private static int QUEUE_MAX_SIZE = 100;
    private static final LinkedList<String> TASK_QUEUE = new LinkedList<>();

    /**
     * 队列
     */

    /**
     * 有参构造
     */
    public WeChatTemplateQueue(LinkedList<String> linkList) {
        TASK_QUEUE.addAll(linkList);
    }

    /**
     * 无参构造
     */
    public WeChatTemplateQueue() {
    }

    /**
     * 当前队列大小
     */
    public int size() {
        return TASK_QUEUE.size();
    }

    /**
     * 队列是否溢出
     */
    private static boolean queueOverFlow() {
        return QUEUE_MAX_SIZE < TASK_QUEUE.size();
    }

    /**
     * 添加一条消息
     */
    public static void produce(String element) {
        /*
         * 如果队列的大小已经达到了最大值
         * 则让线程等待
         * 如果未达到最大值，则顶部添加一条数据，
         * 并唤醒其他所有的线程
         * */
        synchronized (TASK_QUEUE) {
            while (queueOverFlow()) {
                try {
                    TASK_QUEUE.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TASK_QUEUE.addFirst(element);
            TASK_QUEUE.notifyAll();
        }
    }

    /**
     * 是否为空
     */
    public boolean isEmpty() {
        return TASK_QUEUE.isEmpty();
    }

    /**
     * 获取一条消息
     */
    public static String consume() {
        /*
         * 如果队列中没有了数据
         * 则让线程等待
         * */
        synchronized (TASK_QUEUE) {
            while (TASK_QUEUE.isEmpty()) {
                try {
                    TASK_QUEUE.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String result = TASK_QUEUE.removeLast();
            TASK_QUEUE.notifyAll();
            return result;

        }
    }

}

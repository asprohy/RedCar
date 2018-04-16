package com.lyc.car;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2017/12/31.
 */

public class DrawInfoQueue {
    private Queue<DrawInfo> drawInfosQueue = new LinkedList<DrawInfo>();

    private volatile static DrawInfoQueue uniqueDrawInfoQueue;
    private DrawInfoQueue(){}
    public static DrawInfoQueue getInstance(){
        if(uniqueDrawInfoQueue == null){
            synchronized (DrawInfoQueue.class){
                if (uniqueDrawInfoQueue == null){
                    uniqueDrawInfoQueue = new DrawInfoQueue();
                }
            }
        }
        return uniqueDrawInfoQueue;
    }

    public Queue<DrawInfo> getDrawInfosQueue() {
        return drawInfosQueue;
    }

    public void add(DrawInfo drawInfo){
        this.drawInfosQueue.add(drawInfo);
    }

    public DrawInfo poll(){
        return this.drawInfosQueue.poll();
    }

    public void setDrawInfosQueue(Queue<DrawInfo> drawInfosQueue) {
        this.drawInfosQueue = drawInfosQueue;
    }
}

package com.xicheng.concurrent.mashibing;

import java.util.concurrent.*;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-09-28 10:13
 */
public class C41_DelayQueue {

    private static BlockingQueue<MyTask> taskQueue = new DelayQueue<>();

    static class MyTask implements Delayed {

        long runningTime;

        String name;

        public MyTask(long runningTime, String name) {
            this.runningTime = runningTime;
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 当前任务和传入任务的执行顺序
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "runningTime=" + runningTime +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask(now + 1000, "t1");
        MyTask t2 = new MyTask(now + 2000, "t2");
        MyTask t3 = new MyTask(now + 1500, "t3");
        MyTask t4 = new MyTask(now + 2500, "t4");
        MyTask t5 = new MyTask(now + 500, "t5");

        taskQueue.put(t1);
        taskQueue.put(t2);
        taskQueue.put(t3);
        taskQueue.put(t4);
        taskQueue.put(t5);

        System.out.println(taskQueue);

        for (int i = 0; i < 5; i++) {
            System.out.println(taskQueue.take());
        }
    }
}

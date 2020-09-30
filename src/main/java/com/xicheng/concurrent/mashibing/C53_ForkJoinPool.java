package com.xicheng.concurrent.mashibing;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-10-06 00:24
 */
@Slf4j
public class C53_ForkJoinPool {

    private static final int ARRAY_SIZE = 100000;

    /**
     * 10万个数字
     */
    private static int[] numArray = new int[ARRAY_SIZE];

    /**
     * 每个任务最大处理数量，5000
     */
    private static final int TASK_MAX = 5000;

    /**
     * 随机数生成器
     */
    private static Random random = new Random();

    /**
     * 计算结果
     */
    private static int sum = 0;

    /**
     * 随机生成100以内的数字，ARRAY_SIZE最大值可设置 20 000 000 000 / 100 = 200 000 000， 计算结果不会溢出int
     */
    static {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            numArray[i] = random.nextInt(100);
        }
    }

    public static void main(String[] args) {

        // 串行计算
        log.info("serial computing start...");
        sum = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
             sum += numArray[i];
        }
        log.info("serial computing sum: {}", sum);

        // forkjoinpool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 使用RecursiveAction
        sum = 0;
        log.info("RecursiveAction start...");
        AddAction addAction = new AddAction(0, ARRAY_SIZE);
        forkJoinPool.execute(addAction);
        addAction.join();
       /* try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        log.info("RecursiveAction sum: {}", sum);

        // 使用RecursiveTask
        log.info("RecursiveTask start...");
        AddTask addTask = new AddTask(0, ARRAY_SIZE);
        forkJoinPool.execute(addTask);
        log.info("RecursiveTask sum: {}", addTask.join());
    }

    /**
     * 结果不准确，没有任何规律
     */
    private static class AddAction extends RecursiveAction {

        private int start;

        private int end;

        public AddAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= TASK_MAX) {
                for (int i = start; i < end; i++) {
                    sum += numArray[i];
                }
                log.info("{}", sum);
            } else {
                int middle = (start + end) / 2;
                AddAction subTask01 = new AddAction(start, middle);
                AddAction subTask02 = new AddAction(middle, end);
                subTask01.fork();
                subTask02.fork();
            }
        }
    }

    private static class AddTask extends RecursiveTask<Integer> {

        private int start, end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= TASK_MAX) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numArray[i];
                }
                return sum;
            } else {
                int middle = (start + end) / 2;
                AddTask subTask01 = new AddTask(start, middle);
                AddTask subTask02 = new AddTask(middle, end);
                subTask01.fork();
                subTask02.fork();
                return subTask01.join() + subTask02.join();
            }
        }
    }
}

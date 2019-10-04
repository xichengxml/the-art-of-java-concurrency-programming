package com.xicheng.concurrent.mashibing.code026;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 并行计算
 * 可以通过日志的时间统计对比二者的区别
 *
 * @author xichengxml
 * @date 2019-10-04 20:59
 */
@Slf4j
public class T08_ParallelComputing {

    public static void main(String[] args) throws Exception {
        log.info("serial computing start...");
        List<Integer> serialResult = getPrime(0, 200000);
        log.info("serial computing end...");

        log.info("-----------------------------");

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 因为数据越小质数越少，所以四个任务并不是均匀分配
        MyTask task01 = new MyTask(0, 80000);
        MyTask task02 = new MyTask(80001, 120000);
        MyTask task03 = new MyTask(120001, 160000);
        MyTask task04 = new MyTask(160001, 200000);
        Future<List<Integer>> future01 = executorService.submit(task01);
        Future<List<Integer>> future02 = executorService.submit(task02);
        Future<List<Integer>> future03 = executorService.submit(task03);
        Future<List<Integer>> future04 = executorService.submit(task04);
        log.info("parallel computing start...");
        List<Integer> parallelResult01 = future01.get();
        List<Integer> parallelResult02 = future02.get();
        List<Integer> parallelResult03 = future03.get();
        List<Integer> parallelResult04 = future04.get();
        log.info("parallel computing end...");

        log.info("-----------------------------");

        // 对比一下计算结果，确保二者进行的计算是一样的
        List<Integer> parallelResult = new ArrayList<>();
        parallelResult.addAll(parallelResult01);
        parallelResult.addAll(parallelResult02);
        parallelResult.addAll(parallelResult03);
        parallelResult.addAll(parallelResult04);
        log.info("result compare: {}, {}", serialResult.size(), parallelResult.size());
    }

    /**
     * 是否质数
     * @param input
     * @return
     */
    private static boolean isPrime(int input) {
        for (int i = 2; i < input / 2; i++) {
            if (input % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取一定数字范围内的所有质数
     * @param start
     * @param end
     * @return
     */
    private static List<Integer> getPrime(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (isPrime(i)) {
                result.add(i);
            }
        }
        return result;
    }

    private static class MyTask implements Callable<List<Integer>> {

        private int start;

        private int end;

        public MyTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public List<Integer> call() throws Exception {
            return getPrime(start, end);
        }
    }
}

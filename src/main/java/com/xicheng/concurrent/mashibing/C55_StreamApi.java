package com.xicheng.concurrent.mashibing;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 可以通过日志时间对比两者的执行时间
 * 使用日志打印时间不太准确，原因未明
 * 使用日志得出的结果是size越小，极限值1时并行优势越大；size越大串行优势越大，有点奇怪
 *
 * 使用System.out之后时间整体上串行比并行耗时少，应该是这种结果相关性任务串行会比并行速度快；结果无关性任务才建议用并发
 *
 * @author xichengxml
 * @date 2019-10-06 06:10
 */
@Slf4j
public class C55_StreamApi {

    /**
     * 列表长度
     */
    private static final int LIST_SIZE = 10;

    /**
     * 随机数生成器
     */
    private static Random random = new Random();

    /**
     * 列表
     */
    private static List<Integer> list = new ArrayList<>();

    /**
     * 求和
     */
    private static long sum = 0L;

    /**
     * 生成LIST_SIZE个100以内的数字
     */
    static {
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(random.nextInt(100));
        }
    }

    public static void main(String[] args) {
        // 串行steam
        // log.info("serial stream start...");
        System.out.println("serial stream start...");
        long startTime = System.currentTimeMillis();
        // 这里是单线程处理，不需要同步
        list.stream().forEach(x -> sum += x);
        // log.info("serial stream sum: {}", sum);
        System.out.println("serial stream runTime: " + (System.currentTimeMillis() - startTime));

        // 单独for each 比stream().forEach快，why?
        // log.info("for each start...");
        System.out.println("for each start...");
        sum = 0;
        list.forEach(x -> sum += x);
        // log.info("for each sum: {}", sum);
        System.out.println("for each runTime: " + (System.currentTimeMillis() - startTime));

        // 并行stream
        sum = 0;
        System.out.println("parallel stream start...");
        // log.info("parallel stream start...");
        // 因为使用了多线程，所以计算时需要做同步
        list.parallelStream().forEach(C55_StreamApi::calculateSum);
        // log.info("parallel stream sum: {}", sum);
        System.out.println("parallel stream runTime: " + (System.currentTimeMillis() - startTime));
    }

    private static synchronized void calculateSum(int x) {
        sum += x;
    }

}

package com.xicheng.concurrent.mashibing.common;

        import com.google.common.util.concurrent.ThreadFactoryBuilder;

        import java.util.concurrent.*;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-04-21 11:20:29
 */
public class ThreadPoolUtil {

    private static ThreadFactory namedFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();;

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(100, 100, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024), namedFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void executeThread(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }
}

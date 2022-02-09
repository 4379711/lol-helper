package yalong.site.utils;

import java.util.concurrent.*;

/**
 * @author yaLong
 */
public class ThreadPoolUtil {

    public static class MyThreadFactory implements ThreadFactory {
        /**
         * 线程名称
         */
        private final String namePrefix;
        /**
         * 线程数量
         */
        private int counter = 0;

        public MyThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable run) {
            Thread t = new Thread(run, namePrefix + "-Thread-" + counter);
            counter++;
            return t;
        }
    }

    public static ExecutorService getDefaultExecutor(String threadNamePrefix) {
        return new ThreadPoolExecutor(
                1,
                10,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new MyThreadFactory(threadNamePrefix));
    }
}

package org.peak.myth.security.core;

/**
 * <p>
 *  分布式数据库主键生成算法
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:28
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrimaryKeyGenerationAlgorithm {

    private static final long ONE_STEP = 10;
    private static final Lock LOCK = new ReentrantLock();
    private static long lastTime = System.currentTimeMillis();
    private static short lastCount = 0;
    private static int count = 0;

    @SuppressWarnings("finally")
    public static Long nextId()
    {
        LOCK.lock();
        try {
            if (lastCount == ONE_STEP) {
                boolean done = false;
                while (!done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(1);
                        } catch (java.lang.InterruptedException e) {
                        }
                        continue;
                    } else {
                        lastTime = now;
                        lastCount = 0;
                        done = true;
                    }
                }
            }
            count = lastCount++;
        }
        finally
        {
            LOCK.unlock();
            return Long.parseLong(lastTime+""+String.format("%03d",count));
        }
    }
}

package in.xiandan.anycost;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.HashMap;

/**
 * Created by dengyuhan on 2021/3/29 17:40
 */
public class AnyCost {

    private static AnyCost mInstance;

    public static AnyCost getInstance() {
        if (mInstance == null) {
            synchronized (AnyCost.class) {
                if (mInstance == null) {
                    mInstance = new AnyCost();
                }
            }
        }
        return mInstance;
    }

    private boolean mEnabled = true;

    private OnTimingListener mOnTimingListener;

    private final HashMap<String, Long> mTimingCache = new HashMap<>();
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());


    public AnyCost setOnTimingEndListener(OnTimingListener listener) {
        this.mOnTimingListener = listener;
        return this;
    }

    public AnyCost enable(Boolean enabled) {
        this.mEnabled = enabled;
        return this;
    }

    public static void begin(String key) {
        getInstance().internalMark(key);
    }

    public static long end(String key) {
        return end(key, null);
    }

    public static long end(String key, Object extras) {
        return getInstance().internalEnd(key, extras);
    }

    public static long get(String key) {
        return getInstance().internalGet(key);
    }


    /**
     * 标记计时
     *
     * @param key 唯一标识符
     */
    private void internalMark(String key) {
        if (mEnabled) {
            long start = SystemClock.uptimeMillis();
            mTimingCache.put(key, start);
            String threadName = Thread.currentThread().getName();
            mMainHandler.post(() -> {
                if (mOnTimingListener != null) {
                    mOnTimingListener.onTimingBegin(key, threadName);
                }
            });
        }
    }

    /**
     * 结束计时
     *
     * @param key 唯一标识符
     */
    private long internalEnd(String key, Object extras) {
        if (mEnabled) {
            long endTime = internalGet(key);
            mTimingCache.remove(key);
            String threadName = Thread.currentThread().getName();
            mMainHandler.post(() -> {
                if (mOnTimingListener != null) {
                    mOnTimingListener.onTimingEnd(key, threadName, endTime, extras);
                }
            });
            return endTime;
        }
        return 0;
    }

    /**
     * 仅获取 不结束
     */
    private long internalGet(String key) {
        long end = SystemClock.uptimeMillis();
        Long value = mTimingCache.get(key);
        if (value != null) {
            return end - value;
        }
        return 0L;
    }


    public static abstract class OnTimingListener {
        public void onTimingBegin(String key, String threadName) {

        }

        public abstract void onTimingEnd(String key, String threadName, long time, Object extras);
    }


}

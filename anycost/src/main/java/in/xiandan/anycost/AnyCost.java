package in.xiandan.anycost;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiandanin on 2021/3/29 17:40
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

    private List<OnTimingListener> mListeners = new ArrayList<>();

    private final HashMap<String, Long> mTimingCache = new HashMap<>();
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());


    public AnyCost addOnTimingListener(OnTimingListener listener) {
        this.mListeners.add(listener);
        return this;
    }

    public AnyCost removeOnTimingEndListener(OnTimingListener listener) {
        this.mListeners.remove(listener);
        return this;
    }

    public AnyCost removeOnTimingEndListener() {
        this.mListeners.clear();
        return this;
    }

    public AnyCost enable(Boolean enabled) {
        this.mEnabled = enabled;
        return this;
    }

    public static void begin(String key) {
        getInstance().internalBegin(key);
    }

    public static long end(String key) {
        return end(key, null);
    }

    public static long end(String key, Object extras) {
        return getInstance().internalEnd(key, extras);
    }

    public static void end(String key, long endTime, Object extras) {
        getInstance().internalDirectEnd(key, endTime, extras);
    }

    public static long get(String key) {
        return getInstance().internalGet(key);
    }


    /**
     * 标记计时
     *
     * @param key 唯一标识符
     */
    private void internalBegin(String key) {
        if (mEnabled) {
            long start = SystemClock.uptimeMillis();
            mTimingCache.put(key, start);
            String threadName = Thread.currentThread().getName();
            mMainHandler.post(() -> {
                for (OnTimingListener l : mListeners) {
                    l.onTimingBegin(key, threadName);
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
            internalDirectEnd(key, endTime, extras);
            return endTime;
        }
        return 0;
    }

    private void internalDirectEnd(String key, long endTime, Object extras) {
        if (mEnabled) {
            String threadName = Thread.currentThread().getName();
            mMainHandler.post(() -> {
                for (OnTimingListener l : mListeners) {
                    l.onTimingEnd(key, threadName, endTime, extras);
                }
            });
        }
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

package `in`.xiandan.debugtimer

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import java.text.SimpleDateFormat

/**
 * Created by xiandanin on 2021/1/21 16:28
 */
object DebugTimer {
    private var mDebug = false
    private var mTag = javaClass.simpleName

    private var mTimingCache = HashMap<String, Long>()

    private var mListeners = ArrayList<OnTimerEndListener>()

    private val mMainHandler = Handler(Looper.getMainLooper())

    interface OnTimerEndListener {
        fun onTimerEnd(key: String, threadName: String, time: Long)
    }

    fun addOnTimerEndListener(l: OnTimerEndListener) {
        mListeners.add(l)
    }

    fun removeOnTimerEndListener(l: OnTimerEndListener) {
        mListeners.remove(l)
    }

    fun debug(debug: Boolean, logTag: String?) {
        mDebug = debug
        mTag = if (logTag?.isNotEmpty() == true) logTag else javaClass.simpleName
    }


    fun mark(key: String) {
        val markMillis = SystemClock.uptimeMillis()
        mTimingCache.put(key, markMillis)
        if (mDebug) {
            Log.d(mTag, "${key} ${Thread.currentThread().name} 开始计时 ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())}")
        }
    }

    /**
     * 结束计时
     */
    fun end(key: String): Long {
        val get = get(key)
        mTimingCache.remove(key)
        val threadName = Thread.currentThread().name
        mMainHandler.post {
            mListeners.forEach {
                it.onTimerEnd(key, threadName, get)
            }
        }
        return get
    }

    /**
     * 仅获取 不结束
     */
    fun get(key: String): Long {
        val uptimeMillis = SystemClock.uptimeMillis()
        val value = mTimingCache[key]
        if (value != null) {
            return uptimeMillis - value
        }
        return 0L
    }


}
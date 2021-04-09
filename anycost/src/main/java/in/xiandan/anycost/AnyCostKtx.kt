package `in`.xiandan.anycost

import android.os.SystemClock

/**
 * Created by xiandanin on 2021/4/9 13:28
 */
inline fun <T> T.cost(key: String, vararg extras: Any, block: T.() -> Unit): T {
    val start = SystemClock.uptimeMillis()
    block()
    val end = SystemClock.uptimeMillis() - start
    AnyCost.end(key, end, extras)
    return this
}

fun AnyCost.addOnTimingListener(onTimingEndFunc: (key: String, threadName: String, time: Long, extras: Any?) -> Unit): AnyCost {
    addOnTimingListener(object : AnyCost.OnTimingListener() {
        override fun onTimingEnd(key: String, threadName: String, time: Long, extras: Any?) {
            onTimingEndFunc(key, threadName, time, extras)
        }
    })
    return this
}
package `in`.xiandan.anycost

import android.os.SystemClock
import android.text.TextUtils

/**
 * Created by xiandanin on 2021/4/9 13:28
 */
inline fun <T> T.cost(key: String? = null, vararg extras: Any, block: T.() -> Unit): T {
    var costKey = key
    if (TextUtils.isEmpty(costKey)) {
        val stackTrace = Thread.currentThread().stackTrace
        costKey = when {
            stackTrace.size >= 3 -> "${stackTrace[2].className}.${stackTrace[2].methodName}(${stackTrace[2].fileName}:${stackTrace[2].lineNumber})"
            else -> toString()
        }
    }
    val start = SystemClock.uptimeMillis()
    block()
    val end = SystemClock.uptimeMillis() - start
    AnyCost.end(costKey, end, extras)
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
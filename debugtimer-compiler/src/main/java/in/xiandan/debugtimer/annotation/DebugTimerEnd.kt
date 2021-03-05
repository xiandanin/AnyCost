package `in`.xiandan.debugtimer.annotation

/**
 * Created by xiandanin on 2021/3/3 11:43
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class DebugTimerEnd(val key: String) {
}
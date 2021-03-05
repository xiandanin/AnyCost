package `in`.xiandan.debugtimer

import `in`.xiandan.debugtimer.annotation.DebugTimerMark
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_test.setOnClickListener {
            DebugTimer.mark("install")
            Observable.just(Unit)
                    .delay(Random().nextInt(5000).toLong(),TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :Observer<Unit>{
                        override fun onComplete() {
                            tv_log.text = "异步任务结束 耗时${DebugTimer.get("install")}ms"
                        }

                        override fun onSubscribe(d: Disposable) {
                            tv_log.text = "正在执行异步任务"
                        }

                        override fun onNext(t: Unit) {

                        }

                        override fun onError(e: Throwable) {
                        }

                    })
        }

        btn_test_annotation.setOnClickListener {
            Observable.just(Unit)
                    .delay(Random().nextInt(5000).toLong(),TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :Observer<Unit>{
                        override fun onComplete() {
                            tv_log.text = "异步任务结束 耗时${DebugTimer.get("install")}ms"
                        }

                        @DebugTimerMark("install")
                        override fun onSubscribe(d: Disposable) {
                            tv_log.text = "正在执行异步任务"
                        }

                        override fun onNext(t: Unit) {

                        }

                        override fun onError(e: Throwable) {
                        }

                    })
        }
    }
}
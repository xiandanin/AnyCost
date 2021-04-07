package `in`.xiandan.anycost.example

import `in`.xiandan.anycost.AnyCost
import `in`.xiandan.anycost.annotation.AnyCostMark
import `in`.xiandan.anycost.example.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val log = StringBuffer()
        AnyCost.getInstance().enable(true).setOnTimingEndListener(object : AnyCost.OnTimingListener() {
            override fun onTimingBegin(key: String?, threadName: String?) {

            }

            override fun onTimingEnd(key: String?, threadName: String?, time: Long, extras: Any?) {
                log.append("key: ${key}, Thread: ${threadName}, cost：${time}ms\n")
                tv_log.text = log.toString()
            }
        })

        btn_test.setOnClickListener {
            val text = btn_test.text
            btn_test.isEnabled = false
            btn_test.text = "正在执行异步任务"
            Observable.just(Unit)
                    .map {
                        AnyCost.begin("manual_test")
                        Thread.sleep(Random().nextInt(5000).toLong())
                        AnyCost.end("manual_test")
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        btn_test.isEnabled = true
                        btn_test.text = text
                    }
        }

        btn_test_annotation.setOnClickListener {
            val text = btn_test_annotation.text
            btn_test_annotation.isEnabled = false
            btn_test_annotation.text = "正在执行异步任务"
            Observable.just(Unit)
                    .map { testDelay() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        btn_test_annotation.isEnabled = true
                        btn_test_annotation.text = text
                    }
        }
    }

    @AnyCostMark("auto_test")
    private fun testDelay() {
        Thread.sleep(Random().nextInt(5000).toLong())
    }
}
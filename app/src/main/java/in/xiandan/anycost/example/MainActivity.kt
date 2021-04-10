package `in`.xiandan.anycost.example

import `in`.xiandan.anycost.AnyCost
import `in`.xiandan.anycost.addOnTimingListener
import `in`.xiandan.anycost.annotation.AnyCostMark
import `in`.xiandan.anycost.cost
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val log = StringBuffer()

        AnyCost.getInstance().addOnTimingListener { key, threadName, time, extras ->
            log.append("key: ${key}, cost：${time}ms\n")
            tv_log.text = log.toString()
        }

        btn_test.setOnClickListener {
            val text = btn_test.text
            btn_test.isEnabled = false
            btn_test.text = "正在执行"
            GlobalScope.launch {
                AnyCost.begin("manual")
                delay(Random().nextInt(3000).toLong())
                AnyCost.end("manual")
                withContext(Dispatchers.Main){
                    btn_test.isEnabled = true
                    btn_test.text = text
                }
            }
        }

        btn_test_annotation.setOnClickListener {
            val text = btn_test_annotation.text
            btn_test_annotation.isEnabled = false
            btn_test_annotation.text = "正在执行"
            GlobalScope.launch {
                annotationDelay()
                withContext(Dispatchers.Main){
                    btn_test_annotation.isEnabled = true
                    btn_test_annotation.text = text
                }
            }
        }

        btn_test_kotlin.setOnClickListener {
            val text = btn_test_kotlin.text
            btn_test_kotlin.isEnabled = false
            btn_test_kotlin.text = "正在执行"
            GlobalScope.launch {
                cost("kotlin"){
                    delay(Random().nextInt(3000).toLong())
                }
                withContext(Dispatchers.Main){
                    btn_test_kotlin.isEnabled = true
                    btn_test_kotlin.text = text
                }
            }
        }
    }

    @AnyCostMark("annotation")
    private fun annotationDelay() {
        Thread.sleep(Random().nextInt(3000).toLong())
    }

}
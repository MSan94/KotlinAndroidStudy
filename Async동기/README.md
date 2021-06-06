# 동기 vs 비동기


- 동기 
  - 작업을 순서대로 진행
  - 위에서 아래로 탑 다운

- 비동기
  - 쓰레드를 만들어서 작업을 따로 처리


## 안드로이드에서 Async 다루기
- AsyncTask 상속
  - onPreExcute : 쓰레드 출발하기전 할 작업
  - doIntBackground : 쓰레드가 할 작업
  - onPregressUpdate : 작업중간중간 메인 쓰레드로 온다.
  - onPostExcute : 작업을 다 마친 후 메인 쓰레드

## Async의 장점
- Main Thread를 기다리게 할 필요가 없다.
- 네트워크 작업의 경우 많이 사용

## Astnc의 단점
- 재사용 불가
- 구현된 Activity가 종료될 경우 같이 종료되지 않음
  - onPause()를 통해 task를 cancel 시켜야함
- AsyncTask는 하나만 실행할 수 있다. ( 병렬 처리 불가능 )

## 예제 코드
```
package com.example.asynctest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val start: Button by lazy { findViewById(R.id.start) }
    private val stop: Button by lazy { findViewById(R.id.stop) }
    private val progressbar: ProgressBar by lazy { findViewById(R.id.progressbar) }
    private val ment: TextView by lazy { findViewById(R.id.ment) }
    var task: BackgroundAsyncTask? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        start.setOnClickListener {
            task = BackgroundAsyncTask(progressbar, ment)
            task?.execute()
        }

        stop.setOnClickListener {
            task?.cancel(true)
        }
    }

    override fun onPause() {
        task?.cancel(true)
        super.onPause()
    }
}

class BackgroundAsyncTask(
        val progressbar: ProgressBar,
        val progressText: TextView
) : AsyncTask<Int, Int, Int>() {

    var percent: Int = 0

    override fun onPreExecute() {
        percent = 0
        progressbar.setProgress(percent)
    }

    override fun doInBackground(vararg params: Int?): Int {
        // 완료 됐는지
        while (isCancelled() == false) {
            percent++
            if (percent > 100) {
                break;
            } else {
                publishProgress(percent)
            }

            try {
                Thread.sleep(100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return percent
    }

    override fun onProgressUpdate(vararg values: Int?) {
        progressbar.setProgress(values[0] ?: 0)
        progressText.setText("퍼센트 : " + values[0])
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        progressText.setText("작업이 완료됨.")
    }

    // 취소 눌렸을떄
    override fun onCancelled() {
        progressbar.setProgress(0)
        progressText.setText("작업이 취소됨.")
    }

}
```

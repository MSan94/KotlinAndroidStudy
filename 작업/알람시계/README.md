- MainActivity
```
package com.example.aop_part2.alarmprj

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.companion.CompanionDeviceManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //step0 뷰 초기화
        initOnOffButton()
        initChangeAlarmTimeButton()


        //step1 데이터 가져오기
        val model = fetchDataFromSharedPreferences()
        //step2 뷰에 데이터 그려주기
        renderView(model)

    }

    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.onOffButton)
        onOffButton.setOnClickListener {
            //데이터 확인
            val model = it.tag as? AlarmDisplayModel ?: return@setOnClickListener //형변환 -> 실패시 NULL
            val newModel = saveAlarmModel(model.hour, model.minute, model.onOff.not())
            renderView(newModel)

            // 온오프에 따라 작업 처리
            if(newModel.onOff){
                //켜짐 -> 알람등록
                val calendar = Calendar.getInstance().apply{
                    set(Calendar.HOUR_OF_DAY, newModel.hour)
                    set(Calendar.MINUTE, newModel.minute)
                    //지나간 시간에 대한 알람을 등록하면 다음날
                    if(before(Calendar.getInstance())){
                        add(Calendar.DATE, 1) //다음날로 미룸
                    }
                }

                // 알람매니저에 생성
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                // 실행될 인텐트 정의
                val intent = Intent(this, AlarmReceiver::class.java)
                // pendingIntent으로 감싸서 전달
                val pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT) // 기존꺼가 있으면 새걸로 업데이트
                // 알람매니저에 등록
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY, //하루있다가 실핸
                    pendingIntent
                )

            }else{
                //꺼짐 -> 알람 제거
                cancelAlarm()
            }
        }
    }

    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.changeAlarmTimeButton)
        changeAlarmButton.setOnClickListener {
            // TimePickDialog
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { picker, hour, minute ->
                // 데이터저장
                val model = saveAlarmModel(hour,minute,false)
                // 뷰 업데이트
                renderView(model)
                // 기존 알람 삭제
                cancelAlarm()

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }
    }

    private fun saveAlarmModel(
        hour : Int,
        minute : Int,
        onOff : Boolean
    ) : AlarmDisplayModel{
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = onOff
        )

        val sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)
        // with -> sharedPreferences.edit()와 함께할 수 있는 작업을 스코프에 저장
        with(sharedPreferences.edit()){
            putString(ALARM_KEY, model.makeDataForDB())
            putBoolean(ONOFF_KEY, model.onOff)
            commit()
        }
        // commit없이 -> sharedPreferences.edit{}

        return model
    }

    private fun fetchDataFromSharedPreferences() : AlarmDisplayModel{
        val sharedPreferences = getSharedPreferences(SHARED_PRREFERENCES_NAME, Context.MODE_PRIVATE)
        val timeDBValue = sharedPreferences.getString(ALARM_KEY, "9:30") ?: "9:30"
        val onOffDBValue = sharedPreferences.getBoolean(ONOFF_KEY,false)
        val alarmData = timeDBValue.split(":")
        val alarmModel = AlarmDisplayModel(
            hour = alarmData[0].toInt(),
            minute = alarmData[1].toInt(),
            onOff = onOffDBValue
        )
        //보정 -> 실제 UI는 알람이 안켜져있고, 알람이 등록되있는데 sharedPreferences 꺼져있을 수 있어서
        val pendingIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_CODE, Intent(this, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
        if((pendingIntent == null) and alarmModel.onOff){
            // 알람은 꺼져있는데, 데이터를 켜져있는 경우
            alarmModel.onOff = false
        }else if((pendingIntent != null) and alarmModel.onOff.not()){
            // 알람은 등록되어있는데, 데이터를 알람이 등록되어있지 않을 경우
            pendingIntent.cancel()
        }
        return alarmModel
    }

    private fun renderView(model : AlarmDisplayModel){
        findViewById<TextView>(R.id.ampmTextView).apply{
            text = model.ampmText
        }
        findViewById<TextView>(R.id.timeTextView).apply{
            text = model.timeText
        }
        findViewById<Button>(R.id.onOffButton).apply{
            text = model.onOffText
            tag = model
        }
    }

    private fun cancelAlarm(){
        val pendingIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_CODE, Intent(this, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
        pendingIntent?.cancel()
    }

    // 자바의 static
    companion object{
        private const val SHARED_PRREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFF_KEY = "onOff"
        private const val ALARM_REQUEST_CODE = 1000
    }
}
```

- AlarmReceiver
```
package com.example.aop_part2.alarmprj

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent?) {
        //API26이상에선 노티피케이션 채널 필요
        createNotificationChannel(context)
        notifyNotification(context)
    }

    private fun createNotificationChannel(context : Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // O = 26버전
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "기상 알람",
                NotificationManager.IMPORTANCE_HIGH//얼마나 중요한 노티피케이션인지 -> 진동상황, 무음상황에서 동작 여부
            )
            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context : Context){
        with(NotificationManagerCompat.from(context)){
            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("알람")
                .setContentText("일어날 시간입니다.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(NOTIFICATION_ID, build.build())
        }
    }

    companion object{
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
    }
}
```
- AlarmDisplayModel
```
package com.example.aop_part2.alarmprj

data class AlarmDisplayModel(
    val hour : Int,
    val minute : Int,
    var onOff : Boolean
){

    val timeText : String
        get(){
            val h = "%02d".format(if ( hour < 12 ) hour else hour - 12)
            val m = "%02d".format(minute)
            return "$h:$m"
        }
    val ampmText : String
        get() {
            return if(hour < 12) "AM" else "PM"
        }
    
    val onOffText : String
        get(){
            return if(onOff) "알람 끄기" else "알람 켜기"
        }

    fun makeDataForDB() : String{
        return "$hour:$minute"
    }

}
```

- activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/custom_black"
    tools:context=".MainActivity">

    <View
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@+id/onOffButton"
        app:layout_constraintDimensionRatio="H,1:1"
        android:background="@drawable/background_white_wing"
        android:layout_marginStart="50dp"
        android:layout_marginEnd="50dp"
        android:layout_width="0dp"
        android:layout_height="0dp"/>

    <TextView
        android:id="@+id/timeTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="09:30"
        android:textSize="50sp"
        android:textColor="@color/white"
        app:layout_constraintBottom_toTopOf="@+id/ampmTextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_chainStyle="packed" />

    <TextView
        android:id="@+id/ampmTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="AM"
        android:textSize="25sp"
        android:textColor="@color/white"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintBottom_toTopOf="@+id/onOffButton"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/timeTextView" />


    <Button
        android:id="@+id/onOffButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="@string/on_alarm"
        app:layout_constraintBottom_toTopOf="@+id/changeAlarmTimeButton"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <Button
        android:id="@+id/changeAlarmTimeButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="@string/change_time"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

- Manifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.aop_part2.alarmprj">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AlarmPrj">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <receiver android:name=".AlarmReceiver" android:exported="false"/>
    </application>

</manifest>
```

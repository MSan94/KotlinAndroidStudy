# 2021.05.10 BroadcastReceiver

## BroadcastReceiver란?
- 메시지 도착, 전화, 배터리부족 등 특정 작업이 발생할 경우 그것을 받고자 하는 곳에 알려주는 기능
- 즉, '방송하다' 라는 의미로 해석

## 설정방법
- AndroidMenifest에 <receiver></receiver> 설정 (정적)
- 코드에서 Receiver 등록 (동적)

## 정적으로 BroadcastReceiver 등록
```
class MainActivity : AppCompatActivity() {
    private lateinit var broad : BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = Intent(this,MyReceiver::class.java)
        intent.putExtra("data" , "Hello")
        sendBroadcast(intent)
    }

}
```
```
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var data = intent?.getStringExtra("data")
        Toast.makeText(context,"$data",Toast.LENGTH_SHORT).show()
    }
}
```
```
        <receiver
            android:name=".MyReceiver"
            android:enabled="true"
            android:exported="true">
        </receiver>

```

## 동적으로 BroadcastReceiver 등록
- 인텐트필터 생성 및 액션 등록
- Broadcast 익명클래스 생성 및 구현
- Broadcast, Intent 등록
- 등록한 Broadcast 종료
```
class MainActivity : AppCompatActivity() {
    private lateinit var broad : BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
        }
        broad = MyReceiver()
        registerReceiver(broad,filter)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broad)
    }

}
```
```
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action.equals(Intent.ACTION_SCREEN_ON)){
            Toast.makeText(context,"화면켜짐!!",Toast.LENGTH_SHORT).show()
        }
    }
}
```
## 동적 리시버 해제
- onCreate()에서 등록한 리시버는 onDestory()에서 해제
- onResume()에서 등록한 리시버는 onPause()에서 해제

## Broadcast 전달 2가지 방법
- sendBroadcase(Intent)
    - 리시버들에게 순서없이 브로드캐스트를 전송하므로 빠르다.
- sendOrderedBroadcast(Intent, String)
    - 한번에 하나의 리시버에게만 브로드캐스트 전송
    - IntentFilter의 android:priority의 값을 참고하여 순서대로 전달
    - 이전에 보낸 리시버가 모든 처리를 끝내야 다음 리시버로 이벤트 전달로 인한 느린속도
    -

## 정적 vs 동적
- 정적 리시버
    - 한번 등록하면 쉽게 해체 불가 -> 계속적으로 유지
- 동적 리시버
    - 등록과 해체 유연
    - 등록한 해당 컴포넌트의 생명주기에 영향을 받는다.
    - 컴포넌트 안에 변수,메서드에 대한 접근이 유연
    - 등록과 해체가 유연한 만큼 해체에 신경 써야 한다.

## 오래걸리는 작업 처리
- 리시버는 이벤트를 받으면 onReceive() 호출
- 오래걸리는 작업은 다른 쓰레드 or 스케쥴로 작업을 등록하여 처리
```
override fun onReceive(...){
    val pendingResult : PendingResult = goAsync()
    val asyncTask = Tast(pendingResult, intent)
    asyncTask.execute()
}

-- inner class
    private 
```


## 화면 on/off 이벤트 받기
- BroadcastReceiver에서 action을 받아 화면이 켜질경우 Toast 메시지 출력
```
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Receiver", "${intent.action}")
        if(intent.action.equals(Intent.ACTION_SCREEN_ON)){
            Toast.makeText(context,"화면켜짐!!",Toast.LENGTH_SHORT).show()
        }
    }
}
```
- filter와 registerReceiver()를 통해 이벤트 설정 및 등록
```
class MainActivity : AppCompatActivity() {
    private lateinit var broad : BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this,MyReceiver::class.java)
        var filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        broad = MyReceiver()
        registerReceiver(broad,filter)
    }

}
```
- Manifest 설정
```
 <receiver
    android:name=".MyReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>
    </intent-filter>
</receiver>
```
- Log
```
2021-05-10 11:01:11.110 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_ON
2021-05-10 11:01:11.180 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_OFF
2021-05-10 11:01:15.350 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_ON
2021-05-10 11:01:17.583 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_OFF
2021-05-10 11:01:18.647 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_ON
2021-05-10 11:01:24.713 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_OFF
2021-05-10 11:01:25.958 24262-24262/com.example.broadcastreceiver D/Receiver: android.intent.action.SCREEN_ON
```

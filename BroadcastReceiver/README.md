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


## 정적 vs 동적
- 정적 리시버
    - 한번 등록하면 쉽게 해체 불가 -> 계속적으로 유지
- 동적 리시버
    - 등록과 해체 유연
    - 등록한 해당 컴포넌트의 생명주기에 영향을 받는다.
    - 컴포넌트 안에 변수,메서드에 대한 접근이 유연
    - 등록과 해체가 유연한 만큼 해체에 신경 써야 한다.

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

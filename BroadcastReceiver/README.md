# 2021.05.10 BroadcastReceiver

## BroadcastReceiver란?


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

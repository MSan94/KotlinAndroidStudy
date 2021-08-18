# 코루틴
- 스레드를 경량화
- 동시성 프로그래밍
- 코루틴에서 스레드는 단지 코루틴이 실행되는 공간을 제공하는 역할, 실행 중인 스레드를 중단시키지 않기 때문에 하나의 스레드에 여러 개의 코루틴이 존재가능
- 스레드는 스레드가 발생하면 이용중인 스레드가 잠시 멈추고 다음 스레드가 처리하도록 우선순위를 넘겨야 하지만
- 코루틴은 이런 컨텍스트 스위칭을 하나의 스레드에서 처리하므로 성능 저하가 적고, 동일한 구조에서는 스레드보다 훨씬 적은 자원 소모
![image](https://user-images.githubusercontent.com/81352078/129821917-37030854-576d-4179-81f6-331d13d943e8.png)
![image](https://user-images.githubusercontent.com/81352078/129822243-a208ac19-f12f-4b46-ae0d-71382d071172.png)

## 코루틴 설정
- 버전에 따른 코루틴 설정 ( 최신버전 : https://developer.android.com/kotlin/coroutines?hl=ko )
  ```
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9
  ```
  
## 코루틴 스코프
- 코루틴은 정해진 스코프 안에서 실행되는데 이것을 코틀린 스코프라고 한다.
```
GlobalScope.launch{
  // 코루틴 실행 코드
}
```
- 코루틴에는 2가지 스코프가 존재
  - 글로벌 스코프
    - 앱의 생명 주기와 함께 동작하기 때문에 앱이 실행되는 동안은 별도의 생명 주기 관리가 필요하지 않다.
    - 만약 앱의 시작부터 종료까지 또는 장시간 실행되어야 할때 사용
  - 코루틴 스코프
    - 버튼을 클릭해서 서버의 정보를 가져오거나 파일을 여는 용도, 즉 필요할 때만 사용시 코루틴 스코프 사용
- 코루틴 스코프에는 괄호안에 Dispatchers.IO 라는 상수값이 입력되어 있다.
  - 이것을 디스패처라고 하는데 코루틴이 실행될 스레드를 지정하는 것
```
binding.btnDownload.setOnClickListener{
  CoroutineScope(Dispatchers.IO).launch{
    // 실행코드
  }
}
```

## 디스패처 종류
- Dispatchers.Default : cpu를 많이 사용하는 작업을 백그라운드 스레드에서 실행하도록 최적화 되어 있는 디스패처, 안드로이드의 기본 스레드풀을 사용
- Dispatchers.IO : 이미지 다운, 파일 입출력 등의 입출력에 최적화
- Dispatchers.Main : 안드로이드의 기본 스레드에서 코루틴을 실행하고 UI와 상호작용에 최적화, 텍스트 뷰에 글자입력 할 경우 사용
- Dispatchers.Unconfined : 자신을 호출한 컨텍스트를 기본으로 사용하는데, 중단 후 다시 실행하는 시점에 컨텍스트가 바뀌면 자신의 컨텍스트도 다시 실행하는 컨텍스트를 따라감

## launch와 상태 관리
- 코루틴은 launch와 async로 시작할 수 있다.
- launch는 상태를 관리, async는 상태를 관리하여 연산 결과까지 반환
- launch는 호출하는 것만으로 코루틴을 생성할 수 있고, 반환되는 job을 변수에 저장해두고 상태 관리용으로 사용 가능
- cancel
  - 코루틴의 동작을 멈추는 상태관리 메서드, 하나의 스코프에 여러 코루틴이 있다면 하위의 코루틴도 모두 정지
  ```
  val job = CoroutineScope(Dispatchers.Default).launch{
    val job1 = launch {
      for(i in 0..10){
        delay(500)
        Log.d("코루틴","결과 = $i")
      }
    }
  }
  binding.btnStop.setOnClickListener { job.cancel() }
  ```
- join
  - 스코프 안에 선언된 여러 개의 launch 블록은 모두 새로운 코루틴으로 분기되면서 동시에 처리되기 때문에 순서를 정할 수 없다
  - 이럴때 join()을 이용하여 각각의 코루틴이 순차적으로 실행되게 한다
  ```
  CoroutineScope(Dispatchers.Default).launch(){
    launch {
      for(i in 0..5){
        delay(500)
        Log.d("코루틴", "결과 = $i")
      }
    }
  }.join()
  
  launch {
      for(i in 0..5){
        delay(500)
        Log.d("코루틴", "결과2 = $i")
      }
  }
  ```
  - join()에 의해 앞 코루틴 실행이 완료 후 두번째 코루틴 실행

## async와 반환값 처리
- async는 코루틴 스코프의 연산 결과를 받아서 사용할 수 있다.
- 2개의 코루틴을 async로 선언하고, 결과값을 처리하는 곳에서 await 함수를 사용하면 결과 처리가 완료된 후에 await를 호출한 줄의 코드가 실행
```
CoroutineScope(Dispatchers.Default).async{
  val deferred1 = async {
    delay(500)
    350
  }
  val deferred2 = async {
    delay(1000)
    200
  }
  Log.d("코루틴", "연산 결과 = ${deferred1.await() + deferred2.await()}")
}
```

## suspend
- 코루틴 안에서 suspend 키워드로 선언된 함수가 호출되면 이전까지의 코드 실행이 멈추고, suspend 함수의 처리가 완료된 후 멈춰있던 스코프의 다음 코드가 실행
```
suspend fun subRoutine(){
  for(i in 0..10) {
    Log.d("subRoutine", "$i")
  }
}

CoroutineScope(Dispatchers.Main).launch {
  // 코드1
  subRoutine()
  // 코드2
}
```
- 코드1 실행 -> subRoutine() 실행 -> subRoutine() 모두 실행 된 후 코드2 실행
- 여기서 subRoutine()은 suspend 키워드를 붙였기 때문에 CoroutineScope 안에서 자동으로 백그라운드 스레드처럼 동작
- **subRoutine()이 실행되면서 호출한 측의 코드를 잠시 멈췄지만, 스레드의 중단은 없다**

## withContext로 디스패처 분리
- suspend 함수를 코루틴 스코프에서 호출할 때 호출한 스코프와 다른 디스패처를 사용할 때가 있다.
- ex) 호출 측 코루틴은 Main 디스패처에서 UI를 제어하는데, 호출되는 suspend 함수는 디스크에서 파일을 읽어와야 하는 경우
- 이럴때 withContext를 사용해서 호출되는 suspend 함수의 디스패처를 IO를 변경가능
```
suspend fun readFile() : String {
  return "파일내용"
}
CoroutineScope(Dispatchers.Main).launch{
  // 화면처리
  val result = withContext(Dispatchers.IO){
    readFile()
  }
  ...
}
```

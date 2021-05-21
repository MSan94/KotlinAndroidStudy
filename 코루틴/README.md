# 코루틴 2021.05.21

## 코루틴 vs 스레드
- 코루틴 : 동시성
  - 작업이 빠르게 이루어시는 형태로 마치 2개가 동시에 이루어 지듯한 것 처럼 작업
  - 논리적 개념, 싱글코어, 멀티코어 동작
- 스레드 : 병렬성
  - 물리적 개념, 멀티코어 동작


![image](https://user-images.githubusercontent.com/81352078/119115919-ac95ec80-ba62-11eb-8b49-d20a108efc87.png)

## 특징
- 코루틴
  - TASK 단위 : Object(Coroutine)
    - 각 작업에 Object(Coroutine)을 할당
    - Coroutine은 객체를 담는 JVM Heap에 적재
  - Context Switching => No Context Switching
    - 코드를 통해 Switching 시점 보장
    - Suspend is NonBlocking : Coroutine1이 Coroutine2의 결과가 나올 때까지 기다려야 한다면 Coroutine1은 Suspend 되지만, Coroutine1을 수행하던 Thread는 유효
    - Coroutine2도 Coroutine1과 동일한 Thread에서 실행 가능
 
- 스레드
  - Task 단위 = Thread
    - 각 작업에 Thread를 할당
    - 각 Thread는 자체 Stack 메모리를 가지며, JVM Stacck 영역 차지
  - Context Switching
    - Blocking : Thread1이 Thread2의 결과가 나올 때까지 기다려야 한다면 Thread1은 Blocking되어 사용하지 못함

## Suspend(중단함수) & Coroutine Dispatcher
- Suspend(중단함수)
  - 앞에 suspend 키워드를 붙여서 함수를 구성하는 방법
  - 람다를 구성하여 다른 일시 중단 함수를 호출 ex) runBlocking { ... }
- Coroutine Dispatcher
  - 코루틴을 시작하거나 재개할 스레드를 결정하기 위한 도구
  - 모든 Dispatcher는 CoroutineDispatcher 인터페이스를 구현해야 한다.

## Coroutine Builder
- async()
  - 결과가 예상되는 코루틴 시작에 사용 (결과 반환)
  - 전역으로 예외 처리 가능
  - 결과, 예외 반환 가능한 Defered<T> 반환
  ```
  fun main() = runBlocking{
    val name : Defered<String> = async { getFirstName() }
    val lastName : Defered<String> = async { getLastName() }
    println(" Hello, ${name.await()} ${lastName.await()} ")
  }
  suspend fun getFirstName() : String {
    delay(1000)
    return "안"
  }
  suspend fun getLastName() : String{
    delay(1000)
    return "명성"
  }
  // runBlocking 내에서 suspend 함수를 이용할 때 Defered로 미리 객체를 반환할 수 있다.
  // await = 각각 1초가 지나서 반환되는 String을 받아 출력
  // 1초걸림
  ```
  
- launch()
  - 결과를 반환하지 않는 코루틴 시작에 사용 (결과 반환 x)
  - 자체/자식 코루틴 실행을 취소할 수 있는 Job 반환
  ```
  fun dispatcherEx(){
    launch(Dispatcher.Main){
      val param = Param()
      try{
        withContext(Dispatchers.IO){
          val response = RetrofitUtil.apiService.call(param)
          if(response.isSuccessful){
            val body = response.body()
              withContext(Dispatchers.Main){
                body?.let { searchResponse ->
                  setData(searchResponse.searchPoiInfo.pois)
                }
              }
          }
       }
   }
  ```

- runBlocking()
  - Blocking 코드를 일시중지(Suspend) 가능한 코드로 연결하기 위한 용도
  - main함수나 Unit Test에 많이 사용
  - 코루틴의 실행이 끝날때까지 현재 스레드를 차단
  ```
  fun main() = runBlocking{
    val name = getFirstName()
    val lastNmae = getLastName()
    println("Hello, $name $lastName")
  }
  suspend fun getFirstName() : String {
    delay(1000)
    return "안"
  }
  suspend fun getLastName() : String{
    delay(1000)
    return "명성"
  }
  // 2초 걸림
  ```

## 코루틴 코드
```
fun coroutinesEx(){
  val param = Param()
  val response1 = func1(param)
  val response2 = func2(response1)
  val response3 = func3(response2)
  ...
}
```

## 의존성
```
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:$version'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:$version'
```

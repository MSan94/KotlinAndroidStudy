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

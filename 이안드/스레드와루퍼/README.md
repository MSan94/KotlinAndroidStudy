# 메인 스레드 ( UI 스레드 )
- 안드로이드 시스템은 새로운 앱을 실행하면 새로운 리눅스 프로세스를 시작
- 기본적인 액티비티를 비롯한 모든 컴포넌트는 단일 프로세스 및 데인 스레드에서 실행
- 안드로이드 메인 스레드의 특징과 제약사항
  - 화면의 UI를 그리는 처리 담당
  - 안드로이드 UI 툴킷의 구성요소와 상호작용하고, UI 이벤트를 사용자에게 응답하는 스레드
  - UI 이벤트 및 작업에 대해 수 초 내에 응답하지 않으면 ANR( application Not Responding) 팝업 표시
  - 따라서 오래 걸리는 코드는 새로운 스레드를 생성

## 백그라운드 스레드
- 네트워크 작업, 파일 업로드와 다운로드, 이미지 처리, 데이터 로딩 등은 짧은 시간 안에 끝난다고 해도 처리 시간을 미리 계산 불가
- Thread 객체
  ```
  class WorkerThread : Thread() {
    override fun run(){
      ..// 
    }
  }
  ```
- Runnable 인터페이스
  ```
  class WorkerRunnable : Runnable {
    override fun run(){
      ...//
    }
  }
  
  override fun onCreate(savedInstanceState : Bundle?){
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    var thread = Thread(WorkerRunnable())
    thread.start()
  }
  ```
- 람다식 Runnable 익명 객체
  ```
  Thread{
    .../
  }.start()
  ```
  
- 코틀린 제공 thread()
  ```
  thread(start=true){
    ..//
  }
  ```

## 메인 스레드와 백그라운드 스레드
- ***백그라운드 스레드는 UI 구성 요소에 접근하면 안된다***

# 핸들러와 루퍼
- 안드로이드 메인 스레드와 백그라운드 스레드 및 스레드 간 통신을 위해 핸들러와 루퍼를 제공
- 핸들러와 루퍼의 작동 원리
  - 메인 스레드는 내부적으로 루퍼를 가지며 루퍼는 Message Queue를 포함
  - Message Queue는 다른 스레드 혹은 스레드 자기 자신으로부터 전달받은 메시지를 보관하는 Queue
  - 루퍼는 Message Queue에서 메시지, Runnable 객체를 차례로 꺼내서 핸들러가 처리하도록 전달
  - 핸들러는 루퍼로부터 받은 메시지, Runnable 객체를 처리하거나 메시지를 받아 Message Queue에 넣는 스레드간 통신 장치
![image](https://user-images.githubusercontent.com/81352078/129783237-84a298f6-8d76-42ee-b2c7-2405e5576a9a.png)

## 루퍼
- 루퍼는 MainActivity가 실행됨과 동시에 for문 하나가 무한루프 돌고 있는 서브 스레드
- 대기하다가, 자신의 큐에 쌓인 메시지를 핸들러에 전달
- 여러 개의 백그라운드에서 큐에 메시지를 입력하면, 입력된 순서대로 하나씩 거내서 핸들러에 전달

## 핸들러
- 루퍼가 있는 메인 스레드에서 주로 사용되며, 새로 생성된 스레드들과 메인 스레드와의 통신을 담당
- 핸들러는 루퍼를 통해 전달되는 메시지를 받아 처리하는 일종의 명령어 처리기로 사용
- 루퍼는 앱이 실행되면 자동으로 하나 생성되어 무한루프를 돌지만, 핸들러는 개발자가 직접 생성

## 메시지
- 루퍼의 큐에 값을 전달하기 위해 사용되는 클래스
- 객체에 미리 정의해둔 코드를 입력하고 큐에 담아두면 루퍼가 꺼내서 핸들러에 전달

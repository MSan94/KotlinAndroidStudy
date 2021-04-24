# KotlinAndroidStudy
코틀린을 이용한 Android 개발 공부 레파지토리 입니다.

# 2021-04-24  Activity & XML 

## Activity란 무엇일까?
- 하나의 화면을 가지고 있는 어플리케이션의 한 요소
- Activity의 생명주기
  - Activity는 6가지 콜백으로 집합되어 있다 : onCreate, onStart, onResume, onPause, onStop, onDestory
  - ![image](https://user-images.githubusercontent.com/81352078/115957873-e943f600-a53f-11eb-8d42-f04de43189ba.png)
  - onCreate() 
    - 어플리케이션을 실행시 가장 먼저 실행되며 필수적으로 구현
    - 활동의 전체 생명주기 동안 단 한번만 발생해야 한다.
    - 인터페이스 선언, 멤버 변수 정의, 일부 UI 구성들 활동에 관한 기본 설정
    - setContentView(), 바이딩을 통해 xml 레이아웃 리소스 지정
    - SavedInstanceState 파라미터를 수신, 이 파라미터는 Activity의 이전상태를 저장하는 Bundle 객체 , 처음 생성시 null
    ```
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      gameState = savedInstanceState?.getString(GAME_STATE_KEY)
      setContentView(R.layout.main_activity)
      textView = findViewById(R.id.text_view)
    }
    
    @Override
      public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (savedInstanceState != null) {
          gameState = savedInstanceState.getString(GAME_STATE_KEY);
      }
      setContentView(R.layout.main_activity);
      textView = (TextView) findViewById(R.id.text_view);
     }
    ```
  - onStart()
    - 활동이 시작되면 호출
    - 활동이 사용자에게 표시되고 , 앱은 활동을 포그라운드에 보내 상호작용 준비
  - onResume()
    - onStart이후 활동이 재개됨에 따라 포그라운드에 표시되고 호출
    - App이 사용자와 상호작용하는 부분
    - 어떤 이벤트가 발생하여 앱에서 포커스가 떠날때까지 머무른다.
    - 방해되는 이벤트 발생시 onPause()를 호출하여 일시정지
    - 중지상태에서 재개되면 다시 onResume() 호출


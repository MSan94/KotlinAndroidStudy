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
  - onPause()
    - 사용자가 활동을 떠난것을 나타내는 첫번째 신호 (소멸x)
    - 활동이 포그라운드에 있지 않게 되었단 것을 의미
    - 일부 이벤트가 앱을 방해시, 대화상자등으로 인해 활동이 부분적으로 보이지만 포커스 상태가 아닌경우
    - onPause()상태에서 배터리 수명에 영향을 줄 수 있는 모든 리소스 해제하는 것이 좋다
    - 잠깐 실행이 되는 부분으로, 저장작업을 수행하기에는 시간이 부족하다 ( 데이터 저장, 네트워크 호출, DB트랜잭션 실행X ) -> onStop()가 더 좋다.
  - onStop()
    - 활동이 사용자에게 더 이상 표시되지 않으면 중단 상태에 들어가고, onStop() 호출 ex) 새활동이 화면 전체를 차지할 경우
    - 실행할 필요가 없는 기능을 모두 정지 가능
    - 앱이 사용자에게 보이지 않는 동안 필요없는 리소스를 해제하거나 조정해야 한다. 
    - cpu를 많이 소모하는 작업을 종료
  - onDestory() 
    - 활동이 소명되지 전 호출
    - 소명시 ViewModel 객체를 이용하여 Activity의 뷰 데이터 보관
    - Activity에 있는 데이터를 더이상 쓰지 않는다면 onDestory()에서 모두 

## 익명함수란 무엇일까
  - 익명함수
    - 이름이 없는 함수/클래스
    - 이름을 만들어줄 필요가 없다.
    - 한번만 사용
    ```
     // 람다 방식
     private val textView : TextView by lazy { findViewById(R.id.textView) }
     textView.setOnClickListener{
         Log.d("MainActivity", "클릭됨")
     }
     
     // 익명함수 방식
     textView.setOnClickListener(object : View.OnClickListener{
        override fun onClick(v : View?){
          Log.d("MainActivity", "클릭됨")
        }
      })
      
      // 이름이 필요한 경우(click)
      var click = object : View.OnClickListener{
        override fun onClick(v : View?){
        }
      }
      hello.setOnClickListener(click)
    ```

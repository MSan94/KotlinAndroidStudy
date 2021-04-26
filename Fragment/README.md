# 2021.04.25 Fragment

## Fragment란?
- 하나의 Activity에 모든 것을 표현하기 힘들기에 여개 개의 화면을 가지도록 만들기 위한 개념
- 하나의 Activity에서 Fragment를 통해 역할(화면)을 분할
- Fragment는 Activity에 종속적이면 LifeCycle이 존재한다.

![image](https://user-images.githubusercontent.com/81352078/116002246-c09d2880-a633-11eb-8449-a89e6385284d.png)

## Fragment의 생명주기
- onAttach -> onCreate -> onCreateView -> onActivityCreated -> onStart -> onResume -> onPause -> onStop -> onDestoryView -> onDestory -> onDetach

## onAttach()
- 프래그먼트가 Activity에 attach될 때 호출
- 인자로 context가 주어지며, 부모 액티비티에서 리스너 인터페이스를 상속받으면 형 변환을 통해 가져올 수 있다.
```
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
```

## onCreate()
- UI를 제외한 리소스들을 초기화
- 일반적인 onCreate()
```
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
```

## onCreateView()
- 레이아웃들을 inflate하며, View 객체를 얻을 수 있다.
- 일반적으로 Activity에서는 OnCreate()에서 Button, Text들을 초기화하지만, 프래그먼트는 onCreateView()에서 초기화
```
    // View를 그리는 역할
    // 프래그먼트가 인터페이스를 처음 그릴 때 호출
    // inflater : 뷰를 그려주는 역할
    // container : 부모 뷰
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootVeiw = inflater.inflate(R.layout.fragment_one,container,false)
        return rootVeiw
    }
```

## onViewCreated()
- onCreateView에서 return 해준 View를 가지고 있다.
- view.findViewById()를 통해 사용

## onActivityCreated()
- Activity와 Fragment의 뷰가 모두 생성된 상태
- View를 변경하는 작업 가능

## onStart()
- 사용자에게 Fragment가 보여지도록 한다.

## onResume()
- 사용자와 상호작용

## onPause, onStop()
- 프래그먼트의 부모 액티비티가 아닌 다른 액티비티가 포그라운드로 나오게 되면 onPause()
- 다른 액티비티에 의해 화면을 완전히 가리게 되면 onStop()

## onDestoryView()
- 프래그먼트과 관련된 View 제거

## onDestory()
- onDestoryView에서 View를 제거 후 호출

## onDetach()
- 프래그먼트가 액티비티로부터 해제

## Fragment와 Activity간 생명주기 호출 순서
- F : Fragment
```
2021-04-12 02:01:48.942 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onAttach
2021-04-12 02:01:48.942 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onCreate
2021-04-12 02:01:48.942 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onCreateView
2021-04-12 02:01:48.944 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onViewCreated
2021-04-12 02:01:48.944 6939-6939/com.example.aop_part2.fragment D/life_Cycle: onCreate
2021-04-12 02:01:48.947 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onActivityCreated
2021-04-12 02:01:48.948 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onStart
2021-04-12 02:01:48.948 6939-6939/com.example.aop_part2.fragment D/life_Cycle: onStart
2021-04-12 02:01:48.949 6939-6939/com.example.aop_part2.fragment D/life_Cycle: onResume
2021-04-12 02:01:48.949 6939-6939/com.example.aop_part2.fragment D/life_Cycle: F onResume
```

## Fragment 사용 방법
- XML에 의한 ViewComponent추가
    - activity_main.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World"
        android:textSize="50dp" />

    <!-- 아이디를 반드시 지정-->
    <fragment
        android:id="@+id/fragment_one"
        android:name="com.example.aop_part2.fragment.fragment.FragmentOne"
        android:layout_width="match_parent"
        android:layout_height="300dp"/>
    </LinearLayout>
    ```
    - fragment_one.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/purple_200">
    </LinearLayout>
    ```
    - fragmentOne.kt
    ```
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container,false)
    }
    ```
- 코드를 이용한 동적 추가
    - fragmentManager, fragmentTransaction을 사용
    - activity_main.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <LinearLayout
        android:id="@+id/original"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:background="@color/black"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="버튼"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/original" />

    <LinearLayout
        android:id="@+id/fragment1"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/button"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```
    - fragment_one.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="@color/purple_200">

    <TextView
        android:id="@+id/hello"
        android:textSize="30dp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:text="Hello!"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <Button
        android:text="text변경"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/hello"
        android:id="@+id/change_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```
    - MainActivity.kt
    ```
    class MainActivity : AppCompatActivity() {
        private val button : Button by lazy {findViewById(R.id.button)}
        private val fragmentOne = FragmentOne()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            button.setOnClickListener {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment1, fragmentOne)
                fragmentTransaction.commit()
            }
        }
    }
    ```
    - FragmentOne.kt
    ```
    class FragmentOne : Fragment(){

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_one, container, false)
            return rootView
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val change_btn : Button by lazy {view.findViewById(R.id.change_btn)}
            val hello : TextView by lazy {view.findViewById(R.id.hello)}
            change_btn.setOnClickListener {
                hello.text = "Clicked!"
            }
        }
    }
    ```
    
# 2021.04.25 FragmentManager
## 프레그먼트 관리
- FragmentManager
    - 프래그먼트 프랜잭션 (Fragment Transaction)
        - 액티비티가 사용자의 입력 이벤트에 따라 프래그먼트를 추가 및 삭제 그리고 교체 작업을 수행하게 도와준다.
        - 프래그먼트 백스텍(Fragment Backstack)을 통해 저장
    - 액티비티와의 통신
        - 단일 프래그먼트에 대한 세부적인 작업 가능
    
        - 프래그먼트 내 구성요소들 하나하나에 접근 할 수 있도록 도와준다.
        - 특정 이벤트 발생 시 프래그먼트에서 적절한 UI 동작을 할 수 있도록 구현
- FragmentTransaction
    - 프래그먼트 백 스택 관리, 프래그먼트 전환 애니메이션 설정등을 수행
    - 프래그먼트 트랜잭션 설정
        - 선언
            - Kotlin : val fragmentManager = supportFragmentManager
            - Java : FragmentManager fragmentManager = getSupportFragmentManager()
        - 시작
            - kotlin : val fragmentTransaction = fragmentManager.beginTransaction()
            - Java : FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
        - 끝
            - 공통 : fragmentManager.commit()
    - 프래그먼트 백스택 (Fragment BackStack)
        - 현재 실행하려는 트랜잭션의 상태를 기억해두기 위해 만들어진 개념
        - Back Key를 통해 프래그먼트를 이전 상태로 되돌릴 수 있다.
        - transaction.addToBackStack(null)
    - 액티비티와의 통신
        - 액티비티와 프래그먼트는 연결이 되어 있다면 서로의 자원에 직접적으로 접근 가능
        - Activity -> Fragment, Fragment -> Activity 방법은 다르다.
    - Activity -> Fragment 자원 접근
        - FragmentManager에 속한 findFragmentById() 또는 findFragmentByTag()로 원하는 프래그먼트의 참조를 가져올 수 있다.
        - findFragmentById() : 레이아웃이 있는 프래그먼트 참조
        - findFragmentByTag() : 레이아웃이 없는 프래그먼트 참조
        - kotlin : val fragment : FragmentOne = supportFragmentManager.findFragmentById(R.id.프래그먼트컨테이너) as FragmentOne
        - Java : FragmentOne fragment  (FragmentOne) getSupportFragmentManager().findFragmentById(R.id.프래그먼트컨테이너);
        - **꼭 프래그먼트 매니저를 통해 참조값을 받아와야 한다.**
     - Fragment -> Activity 자원 접근
        - getActivity
        - kotlin : val button = activity?.findViewById(R.id.button)
        - Java : TextView textView = (TextView) getActivity().findViewById(R.id.textView1);

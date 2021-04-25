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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootVeiw = inflater.inflate(R.layout.fragment_one,container,false)
        return rootVeiw
    }
```

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
- 
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


## 디스플레이(display), 윈도우(window), 서피스(surface), 뷰(view), 뷰 그룹(view group), 뷰 컨테이너(view container), 레이아웃(layout)
- 디스플레이
  - windowManager와 함께 Display 객체를 통해 사이즈를 구할 수 있다.
  - kotlin
  ```
  val display = windowManager.defaultDisplay
  val size = Point()
  display.getRealSize(size)
  val width = size.x
  val height = size.y
  ```
  - java
  ```
  Display display = getWindowManager().getDefaultDisplay();
  Point size = new Point();
  display.getRealSize(size);
  int width = size.x;
  int height = size.y;
  ```
- 윈도우
  - ...
- 서피스
  - 하나의 그래픽 버퍼로
  - canvas가 아닌 surface(가상메모리화면)를 가진다.
    - Main Thread가 surface를 감지해서 스레드에게 **그리기 허용 여부**를 알려주며, 이는 SurfaceHolder.Callback으로 수행
  - 처음 생성된 직후 **surfaceCreated(SurfaceHolder holder)가** 호출되며, 이때부터 그리기가 허용
  - **surfaceDestoryed(SurfaceHolder holder)는** 표면이 파괴되기 직전에 호출되며 리턴 후에는 그리기 불가
  - **surfaceChanged (SurfaceHolder holder, int format, int width, int height)는** 표면의 색상이나 포맷 변경시 최소한 한번 호출
- View
  - 기본 화면을 구성하는 모든 기본 화면의 구성요소
  - View는 View를 포함할 수 있고, 중첩적으로 사용할 수 있다.
- ViewGroup
  - 여러 View를 묶기위한 요소로 View의 배치를 위해 사용
  - ViewGroup는 View를 상속받고 있기에 View의 속성을 그대로 쓴다.
  - View의 직접적 자식으로 FrameLayout, GridLayout, LinearLayout, RelativeLayout등이 있다.
- ViewContainer
  - 다른 View를 포함하고 있거나 포함할 수 있는 View
  - 일반적으로 ViewGroup를 상속하면서 Layout이 아닌 클래스를 지칭한다.
  - 종류
    - ScrollView
    - HorizontalScrollView
    - ListView
    - TabHost
    - GridView
    - Gallery
    - ViewFilpper
    - SlidingDrawer
    - DatePicker
    - TimePicker
- Layout
  - 앱에서 사용자 인터페이스를 위한 구조를 정의
  - UI 요소를 XML로 선언, 런타임에 레이아웃 요소 인스턴스화 로 선언하는 방법이 2개다.
![image](https://user-images.githubusercontent.com/81352078/116339457-6ad5a580-a818-11eb-97cb-6b318910a4c5.png)


Q12. 액티비티간 데이터 전달에서 임의의 클래스 객체를 바로 전달하지 못하는 이유는 무엇이고 전달하기 위해서는 어떤 처리가 필요한가?
액티비티간 전달할 수 있는 데이터의 type은 보통 기본형으로 정해져있습니다. 그 이유는 인텐트를 이용하여 액티비티의 데이터를 전달하는 과정에서 현재 실행중인 앱 프로세스가 시스템 프로세스로 실행중인 액티비티 매니저 서비스 프로세스에게 인텐트를 전달합니다. 이 경우 프로세스간 통신이기 때문에 인텐트에 있는 값들을 복사하여 넘기는 방식으로 처리되기 때문에 객체 주소를 바로 넘기지 못하는 문제가 발생합니다. 따라서 이 문제를 해결하기 위해 자신이 임의로 만든 클래스 객체를 전달하기 위해서는 Serilizable이나 Parcelable 인터페이스를 상속받아 객체를 직렬화하여 넘기는 방식을 사용해야합니다.

어플리케이션(application)과 컨텍스트(context)에 대해서 설명하시오.

안드로이드에서 로그(log)를 출력하는 방법과 종류를 설명하시오.

뷰 홀더 패턴(view holder pattern)에 대해서 설명하시오.

 나인패치(9patch)란 무엇인가?
 
 안드로이드의 쓰레드(thread)와 쓰레드 핸들러(thread handler)에 대해서 설명하시오.
 
  NDK에 대해서 설명하라.
  
   NDK에서 로그캣(logcat)에 로그를 출력하기 위해 어떻게 해야하는가?
   
 Q31. 안드로이드의 컨텍스트(context) 객체에 대해 설명하시오.
Q32. 액티비티의 4가지 launchMode에 대해서 비교 설명하시오.

 ARM64-v8a(64), ARMv7, ARMx86 등.. ABI(Application binary interface)가 무엇인가요?
 
 4. 바이트 코드를 안드로이드에서 바로 실행할 수 있나요 ?
> 바로 실행할 수 없다. Java 바이트 코드를 실행하려면 JVM (Java Virtual Machine)이 필요하지만,  안드로이드는 JVM 대신 Dalvik VM 을 사용하여,  메모리, 배터리 수명 및 성능에 더 초점을 맞춰 개발이 되었기 때문에 실행할수 없다.(라이센스 문제도 있었다고 한다.) 또한  dx 라는 안드로이드 도구를 사용하여 Java 클래스 파일을 Dalvik 실행 파일(.dex 파일)로 바꿔 실행한다.
Dalvik VM : 32비트만 지원 (JIT 컴파일러 - 실행하면 만들어 놓고)
ART VM : 32비트, 64 비트 모두 지원 (AOT 컴파일러 - 미리 만들어 놓고)

5. 빌드타입이 무엇인가요?

> 빌드타입이란 현재 사용하고 있는 라이브러리, 모듈 등의 빌드 방법을 정의하는 것이다. 안드로이드 앱이 패키징 되고, 빌드 될 때 그래들을 사용하여 빌드 타입을 정의할 수있다. 빌드할 때 추가되어야하는 리소스를 분리하여 적용할 수 있다. 또한 build variant 와 다양하게 조합해서 제품의 flavours 나 build type 을 정의할 수 있다.
Ex) flavor = [local, dev, stage, real]
Ex) buildType = [debug, release]
localDebug, localRelease, devDebug, devRelease, stageDebug, stageRelease, realDebugm realRelease 등.

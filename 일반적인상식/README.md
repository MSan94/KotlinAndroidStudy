디스플레이(display), 윈도우(window), 서피스(surface), 뷰(view), 뷰 그룹(view group), 뷰 컨테이너(view container), 레이아웃(layout)에 대해서 설명하시오.


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

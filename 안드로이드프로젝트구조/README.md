
# 2021.04.28 안드로이드 프로젝트 구조
![image](https://user-images.githubusercontent.com/81352078/116336304-49be8600-a813-11eb-9fec-0978d6b82c15.png)

## Manidest.xml
- 앱의 구성요소나 권한등의 정보를 정의하고, 앱에 대한 정보를 담고 있다.

## JAVA
- 자바 소스 파일이 들어있는 폴더로 app/src/main 경로에 존재
- 패키지명과 동일한 하위 폴더들이 만들어짐
- UI동적 처리 및 컨트롤, 백엔드 수행

## res
- 앱에 사용되는 자원들을 넣는 폴더
- drawable : 이미지
- layout : 레이아웃
- mipmap : 아이콘 이미지 파일
- values : 공통 리소스 ex)string.xml , color.xml ...

## gradle
- 빌드 배포 도구
- 안드로이드 스튜디오와 빌드 시스템이 서로 독립적
  - 안드로이드 스튜디오는 코드의 편집만 담당
  - gradle을 통해 모두 빌드한다.
- plugins 
  - 안드로이드 플러그인 사용을 gradle에 적용 , top-level에 선언
- android {..}
  - 안드로이드와 관련된 빌드 설정
- compileSdkVersion, buildToolsVersion
  - compileSdkVersion
    - 앱 컴파일 시 사용할 sdk 버전 지정
  - buildToolsVersion
    - 사용할 빌드툴의 버전
- defaultConfig {..}
  - AndroidManifest.xml에서 사용하는 설정들에 대해서 동적인 옵션을 주고 싶을 때 포함
- buildType {..}
  - 빌드 타입 지정
- dependencies {...}
  - 라이브러리, 의존성등을 추가

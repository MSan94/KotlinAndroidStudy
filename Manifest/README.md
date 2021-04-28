
# 2021.04.28 안드로이드 Manifest
- 컴퓨팅에서 집합의 일부 또는 논리정연한 단위인 파일들의 그룹을 위한 메타데이터를 포함하는 파일
- 안드로이드 프로젝트에 반드시 포함되어야 하며, Source Set의 루트에 위치
- 앱의 대한 필수적인 정보를 안드로이드 빌드 툴과 AndroidOS, 구글플레이에 제공

## Manifes의 역할
- 패키지 이름 지정
- 4대컴포넌트 실행 조건 기술
- 앱과 장치 또는 구성요소들과의 상호작용 권한 설정
- 라이브러리 기술

## Manifest 담을 수 있는 정보
- 앱 패키지 이름
- 컴포넌트
- 권한
- 하드웨어 및 소프트웨어 특징

## 패키지 이름 및 애플리케이션 ID
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.myapplication">
</manifest>
```
- 패키지의 이름은 App Resource 접근 시 사용되는 R 클래스의 네임스페이스로 적용  ex) com.example.myapplication.R
- 매니페스트 파일 내 선언된 상대경로에 적용  ex) com.example.myapplication.MainActivity

## 앱 컴포넌트
- 4대 컴포넌트를 앱에서 사용한다면 manifest에 등록해야한다.
```
<activity> , <service> , <receiver> , <provider>
```
- 4대 컴포넌트는 각 인텐트에 의해 활성화 되며, 앱이 인텐트를 시스템에 발행 시 각 앱의 manifest에 등록 intent-filter에 기초하여 처리할 수 있는 인텐트 컴포넌트를 찾는다.

## 권한
```
<manifest ... >
    <uses-permission android:name="android.permission.SEND_SMS"/>
    ...
</manifest>
```
- 안드로이드에서 카메라, 인터넷과 같은 민감한 시스템 기능을 사용 시 권한이 필요하다.

## 하드웨어 및 소프트웨어 정보
```
<manifest ... >
    <uses-feature android:name="android.hardware.sensor.compass"
                  android:required="true" />
    ...
</manifest>
```
- 앱이 필요하는 하드웨어나 소프트웨어 특징을 명시할 수 있다.
- 사진어플 같은 경우 카메라 기능이 있는 사람만 다운가능하게 하듯이 설정할 수 있다.
- <uses-sdk> 태그를 통해 sdk 버전을 명시할 수 있지만, build.gradle에 minSdkVersion 같은 기능이 있기에 sdk는 build.gradle에서 정의

# 권한
- 권한은 '권한 명세' , '기능명세'가 있다.
- 권한 명세 : 해당 데이터나 기능의 사용 여부를 설정
- 기능 명세 : 해당 기능이 있는 안드로이드 폰에서만 내려받을 수 있도록 플레이 스토어에서 내려받는 것을 

## 권한 명세
- 매니페스트에 추가
```
<uses-permission android:name="android.permission.INTERNET"/> // 인터넷 접근 권한
<uses-permission android:name="android.permisson.ACCESS_WIFI_STATE"/> // 와이파이 접근 권한
```

## 기능 명세
- 매니페스트에 따로 추가하지 않아도 해당 기능을 사용할 때 시스템이 자동으로 부여
- <uses-feature /> 태그 사용
- ex) 앱 카메라 기능을 추가하는 순간 자동으로 AndroidManifest.xml 파일에 <uses-feature android:name="android.hardware.camera" android:required="true"/>가 명세
- required 옵션을 false로 작성하면 카메라가 없는 스마트폰에서도 검색하고 설치할 수 이싿.
 
## 권한의 보호 수준
- 권한은 '일반권한' , '위험권한' , '서명권한' 세가지의 보호 수준으로 나눈다.
- 일반권한
  - 매니페스트에 명세하여 설치 시 사용자에게 권한 승인을 묻는 팝업창을 보여준다
  - ACCESS_NETWORK_STATE, BLUETOOTH... 등등
- 위험권한
  - 앱이 사용자의 개인정보와 관련된 데이터나 기능을 액세스 하거나 다른 앱 및 기기의 작동에 영향을 줄 우려가 있는 권한
  - Gradle Scripts 디렉터리에 있는 build.gradle 파일의 targetSdkVersion이 23이상 설정
  - 매니페스트에 권한을 명세하고, 부가적으로 소스 코드에 권한 요청 및 처리 로직 작성
  - <USES-PERMISSION ANDROID:NAME="ANDROID.PERMISSION.access_fine_location"/>
  - READ_CALENDAR, WRITE_calendar, camera, read_contacts 등등..
- 서명권한
  - 권한을 사용하려는 앱이 권한을 정의하는 앱과 동일한 인증서로 서명된 경우 시스템은 권한을 자동으로 부여

# 네이버 API

## MapView
- Fragment를 사용하면 수명주기를 가지고 있기에 장점이있지만
- MapView는 커스텀마이징이 가능한 반면 액티비티의 생명주기를 그대로 넘겨줘야함
- 액티비티에서는 fragment나 mapview 아무거나 사용해도 되지만
- Fragment내에서 map 사용 시 MapView 사용해야함!

## 대시보드
- https://console.ncloud.com/dashboard

## Application 등록하기
- AI·Naver Service로 개발을 위한 Application을 등록
- Application 등록을 위해선 결제수단 설정
- 정상적으로 등록되면 Application 하나당 유일한 Client ID와 Client Secret 값이 생성
- 이 값은 API를 호출할 때 HTTP 헤더값에 포함해서 전송해야 호출이 가능

## Map 사용
- https://www.ncloud.com/product/applicationService/maps
![image](https://user-images.githubusercontent.com/81352078/119538355-ffa2d300-bdc5-11eb-8136-052a315bcdd9.png)
![image](https://user-images.githubusercontent.com/81352078/119538444-1517fd00-bdc6-11eb-9a90-6282dc219595.png)

- 프로젝트 수준 gradle
```
allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven {
            url 'https://naver.jfrog.io/artifactory/maven/'
        }
    }
}
```
- App 수준 gradle
```
implementation 'com.naver.maps:map-sdk:3.11.0'
```

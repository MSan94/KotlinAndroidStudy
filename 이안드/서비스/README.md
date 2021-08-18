# 서비스
- 화면이 없는 액티비티 -> 서비스가 메인 스레드를 사용하기 때문
- 서비스가 백그라운드에서 동작하는 컴포넌트로 알려져있지만, 실제로는 서비스만으로는 백그라운드에서 작용 X

## 서비스의 실행 방식
- Started Service와 Bound Service 두 가지 형태로 실행
- 최종적으로 앱이 꺼져도 실행되는 서비스는 Foreground Service 형태로 만들어야 한다.

## Started Service
- startService() 메서드 사용
- 액티비티와 상관없이 독립적으로 동작할 때 사용
- 액티비티의 종료와 무관하게 동작하므로 일반적으로 많이 사용하는 서비스
- 이미 동작 중인 상태에서 서비스의 재시작을 요청할 경우 새로 만들지 않고 생성되어 있는 서비스의 메서드 호출
![image](https://user-images.githubusercontent.com/81352078/129927800-07ad6e82-88bc-42a2-9a27-057fab0c1c36.png)

## Bound Service
- bindService() 사용
- 액티비티와 값을 주고받을 경우 사용
- 여러 개의 액티비티가 같은 서비스를 사용할 수 있어서 기존에 생성되어 있는 서비스를 바인딩해서 재사용 가능
- 액티비티와 값을 주고받을 필요가 있을 때 사용하고 값을 주고받기 위한 인터페이스를 제공
- but, 인터페이스의 사용이 복잡하고 연결된 액티비티가 종료되면 서비스도 같이 종료되기 떄문에 특별한 경우를 제외하곤 사용X
- 택티비티 화면이 떠 있는 상태에서 백그라운드 처리도 함께할 경우에는 Started Service보다 효율이 좋은 수 있다.
![image](https://user-images.githubusercontent.com/81352078/129928125-f4383bb6-292b-42de-87a5-d23ebd878afa.png)

## 서비스 만들기
- Started Service : New -> Service -> Service 선택
![image](https://user-images.githubusercontent.com/81352078/129928218-b6651767-2827-420a-a0b5-5256c5ec5187.png)
```
class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}

// 매니페스트
<service android:name=".service.MyService"/>
```

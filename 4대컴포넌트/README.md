# 2021.04.27 안드로이드 4대 컴포넌트

## 안드로이드의 4대 컴포넌트란?!
![image](https://user-images.githubusercontent.com/81352078/116172163-2a592780-a745-11eb-888b-bde690a24d28.png)
- 액티비티, 서비스, 콘텐트 제공자, 방송 수신자를 통해 총 4개의 컴포넌트가 있다.
- 컴포넌트들은 하나의 **독립**된 형태로 존재하며, 정해진 역할을 수행한다.
- 각 컴포넌트들은 **Intent** 객체를 통해 상호 통신을 한다.
- 모든 컴포넌트를 꼭 사용해야 하는것은 아니지만 **액티비티**는 필수 요소로 반드시 하나 이상 존재해야 한다.

## Activity
- 사용자가 애플리케이션과 상호작용하는 단일 화면
- Intent를 통해 다른 애플리케이션의 택티비티를 호출가능
- 2개 이상의 액티비티를 동시에 Display 불가
- 1개 이상의 View or ViewGroup을 포함

## Service
- 백그라운드에서 작업을 처리하기 위한 기능
- 백드라운드의 기능은 앱을 사용하며 파일을 다운로드하고, 노래를 트는 행위를 가능하게 해준다
- 메인 Thread에서 동작하며 Service 내 별도의 Thread를 생성하여 작업을 처리
- 네트워크와 연동이 가능하며, UI 없이 백그라운드에서 수행
- Activity와 Service는 UiThread 스레드로 실행
- 앱이 종료되어도 이미 시작된 Service는 백그라운드에서 계속 동작
```
public class MyService extends Service{
  public void onCreate(){
    Thread t = new Thread(){
      void run(){}
    };
    t.start();
  }
  public void onDestory(){
  }
}
```
- 서비스의 생명주기
  - 로컬 서비스 구현
    - onCreate() : 서비스 생성시 호출
    - onStart() : startService() 메소드에 의해 서비스 시작 시 호출
    - onDestory() : 서비스 종료 시 호출
    
  ![image](https://user-images.githubusercontent.com/81352078/116173533-b704e500-a747-11eb-9ef4-fe39e24ebe17.png)
  
  - 원격 서비스 구현
    - onCreate() : 서비스 생성시 호출
    - onBind() : bindService() 메서드에 의해 서비스 시작 시 호출
    - onUnbind() : 서비스와 연결이 끊겼을 시 
    - onDestory() : 서비스 종료 시 호출
    
  ![image](https://user-images.githubusercontent.com/81352078/116173567-ca17b500-a747-11eb-8004-c34913d87461.png)

## BroadCast Receiver
- Android OS에서 발생하는 이벤트와 정보를 핸들링
- 배터리부족, 메시지 알림, 네트워크 해제등의 정보를 받아 동작
```
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
```
## Content Provider
- 데이터를 관리하고 다른 애플리케이션의 데이터를 제공
- 특정 애플리케이션이 사용되고 있는 DB를 공유하기 위해 사용하며, 애플리케이션간 데이터 공유를 위한 표준 인터페이스 제공
- DB,WEB,파일 등을 통해 데이터 관리
- 외부 애플리케이션이 실행중인 애플리케이션 내 DB에 접근 못하도록 할 수 있으며, 공개 및 공유 하고 싶은 데이터만 공유할 수 있다
- 주로 영상, 사진등 용량이 큰 데이터 공유에 사용
- Read, Write의 대한 권한(Permission)이 있어야 접근 가능
- CRUD 원칙 준수

![image](https://user-images.githubusercontent.com/81352078/116173878-5033fb80-a748-11eb-8db5-9ca3c6eb1db2.png)
```
public class MyProvider extends ContentProvider {
    public int delete(Uri uri, String selection, String[] selectionArgs) { 
        return 0;
    }
    public String getType(Uri uri) { 
        return null;
    }
    public Uri insert(Uri uri, ContentValues values) { 
        return null;
    }
    public boolean onCreate() { 
        return false;
    }
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) { 
        return null;
    }
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) { 
        return 0;
    }    
}
```
## MVC로 보는 안드로이드 구조
![image](https://user-images.githubusercontent.com/81352078/116173339-5bd2f280-a747-11eb-9c6d-aa3d3002533d.png)

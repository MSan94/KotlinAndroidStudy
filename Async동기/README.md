# 동기 vs 비동기


- 동기 
  - 작업을 순서대로 진행
  - 위에서 아래로 탑 다운

- 비동기
  - 쓰레드를 만들어서 작업을 따로 처리


## 안드로이드에서 Async 다루기
- AsyncTask 상속
  - onPreExcute : 쓰레드 출발하기전 할 작업
  - doIntBackground : 쓰레드가 할 작업
  - onPregressUpdate : 작업중간중간 메인 쓰레드로 온다.
  - onPostExcute : 작업을 다 마친 후 메인 쓰레드

## Async의 장점
- Main Thread를 기다리게 할 필요가 없다.
- 네트워크 작업의 경우 많이 사용

## Astnc의 단점
- 재사용 불가
- 구현된 Activity가 종료될 경우 같이 종료되지 않음
  - onPause()를 통해 task를 cancel 시켜야함
- AsyncTask는 하나만 실행할 수 있다.

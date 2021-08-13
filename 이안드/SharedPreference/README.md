# 파일 입출력
- 내부 저장소 ( 앱별 저장 공간 )
  - 설치한 앱에 제공되는 디렉터리
  - A앱을 설치할 경우 /data/data/A 디렉터리가 생성되며, A앱은 해당 디렉터리에 한해서만 특별한 권한 없이 R/W 할 수 있다.
  - 주로 앱에서만 사용되는 데이터를 저장
- 외부 저장소
  - 모든 앱이 함께 사용할 수 있는 공간
  ```
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/> //읽기
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> //쓰기
  ```
  - 외부 저장소에 기록되는 내용은 사용자가 앱을 제거한 뒤에도 저장되어야 하는 데이터 OR 다른 앱도 접근할 수 있는 데이터
  - ex) 화면 캡처나 다운로드한 파일
```
    def preference_version = "1.1.1"
    implementation "androidx.preference:preference-ktx:$preference_version"
```

## 내부 저장소 파일 읽기
- 파일 사용
  - File 클래스를 생성하여 정보를 얻거나 기능을 사용한다.
  - File은 파일 또는 디렉터리의 경로를 생성자에 입력해서 생성
  ```
  val file = File("경로")
  ```
  - 파일의 경로와 파일명을 입력해서 생성할 수도 있다. 파일의 경로는 컨텍스트가 가지고 있는 filesDir 프로퍼티를 통해 내부 저장소의 files 디렉터리에 접근
  ```
  val file = File(baseContext.filesDir,"파일명")
  //액티비티의 경우 filesDir이 기본 프로퍼티
  val file = File(filesDir,"파일명")
  ```
- 파일 존재 여부 : exists
```
if(file.exists()){
  .../
}
```
- FILE의 생성자에 전달된 경로가 파일인지 확인 : isFile
```
if(file.isFile){
  .../
}
```
- File의 생성자에 전달된 경로가 디렉터리인지 확인 : isDirectory
```
if(file.isDirectory){
  .../
}
```
- 해당 경로에 파일이 존재하지 않으면 createNewFile()로 파일을 생성하며 보통 exists()와 함께 사용
```
if(!file.exist()){
  file.createNewFile()
}
```
- 디렉터리 생성 : mkdirs()
```
if(!file.exists()){
  file.mkdirs()
}
```
- 파일이나 디렉터리 삭제 : delete()
```
file.delete()
```
- 파일 또는 디렉터리의 절대 경로 반환 : absolutePath
- 절대경로는 시스템의 루트(/)부터 시작하는 경로
```
${file.absolutePath}
```

# SharedPreferences
- 내부 저장소를 이용하기 위한 라이브러리
- 저장(putString) -> 저장소 -> (getString)사용
- 인텐트처럼 키와 값을 설정하여 저장할 수 있다.
- 데이터는 xml 형식으로 된 파일로 저장되며 앱이 종료되어도 남아 있다.

## SharedPreferences 사용
- 값을 저장하기 위해서는 마지막에 꼭 apply()를 해줘야 하며, 읽을때는 사용 X
- 저장
  - 1) SharedPreference 생성
  - 2) Editor 꺼내기
  - 3) putInt() , putString() 메서드로 저장
  - 4) apply()로 반영
- 읽기
  - 1) SharedPreference 생성
  - 2) getInt() , getString() 메서드로 값 읽기

## getSharedPreferences()
- Context를 가지고 있는 모든 컴포넌트에서 접근과 호출이 가능하다.
- getSharedPreferences(이름, 모드)를 액티비티에서 호출하면 SharedPreferences가 반환
```
val shared = getSharedPreferences("이름", Context.MODE_PRIVATE)
```
- 첫 번째 파라미터에는 입력된 데이터가 저장될 파일명, 두번째 파라미터는 파일 접근 권한 설정
- MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE가 있으며 API LEVEL 17 이후로는 보안문제로 PRIVATE만 사용

## getPreferences()
- 개별 액티비티에서 사용하거나 액티비티가 하나 뿐인 앱이면 getPreferences()를 호출한다.
- 호출하는 액티비티의 이름으로 저장 파일이 생성
```
var preference = getPreferences(Context.MODE_PRIVATE)
```

## Editor로 데이터를 저장하고 불러오기
- SharedPreferences로 데이터를 저장하기 위해서는 Editor 인터페이스를 사용
- Editor 인터페이스는 edit() 메서드를 호출해서 사용
```
val shared = getSharedPreferences("이름", Context.MODE_PRIVATE)
val editor = shared.edit()
```
- 데이터를 저장할 때는 입력될 값의 타입에 맞는 Editor의 메서드를 사용해서 저장할 수 있다.
- 마지막에 apply() 메서드를 호출해야 실제 파일에 반영
```
val shared = getSharedPreferences("이름", Context.MODE_PRIVATE)
val editor = shared.edit()
editor.putString("키","값")
editor.apply()

shared.getString("키","값")
```
- 그외 메서드
  - remove(String key) : 해당 키의 데이터 삭제
  - clear() : 모든 데이터 삭제
  - apply() : 변경한 업데이트를 파일에 비동기적으로 저장
  - commit() : 변경한 업데이트를 파일에 동기적으로 저장, 동기 작업이므로 UI 스레드에서 호출하는 것을 피해야 한다.
    - 메인 스레드에서 사용하므로 잠시 화면이 멈출 수 있다.
 

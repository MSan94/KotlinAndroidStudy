
# 2021.04.28 인플레이션
- XML 레이아웃 파일에 비치된 리소스들이 setContentView()나 LayoutInflator 객체 등을 통해 메모리상에 실제로 객체화되어 어플리케이션에 보여지는 과정
- setContentView() 호출 전 XML의 리소스를 참조하려면 NullException 발생 -> xml 리소스가 객체화 되기 전
- setContentView() 용도
  - XML 레이아웃 메모리상 객체화
  ```
  public void setContentView(int layoutResId)
  ```
  - 화면에 나타내는 뷰 지정
  ```
  public void setContentView(View view, [ViewGroup.LayoutParams params])
  ```
## 인플레이션의 순서
1) XML 레이아웃의 내용이 프로젝트가 빌드되는 시점에 이진코드로 컴파일되어 애플리케이션에 포함
2) 실행 시점에 컴파일 된 이진코드가 메모리상에 객체화


## LayoutInflater
- LayoutInflater 객체는 일부 뷰만 객체화
- 시스템 서비스로 제공되므로 사용하려면 시스템 서비스 이용
```
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
inflater.inflate(R.layout.id, container, true)
```

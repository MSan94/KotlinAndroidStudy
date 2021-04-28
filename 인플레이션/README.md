
# 2021.04.28 인플레이션
- XML 레이아웃 파일에 비치된 리소스들이 setContentView()나 LayoutInflator 객체 등을 통해 메모리상에 실제로 객체화되어 어플리케이션에 보여지는 과정
- setContentView() 호출 전 XML의 리소스를 참조하려면 NullException 발생 -> xml 리소스가 객체화 되기 전
- setContentView() 용도
  - XML 레이아웃 메모리상 객체화
  - 화면에 나타내는 뷰 지정

## LayoutInflater
- LayoutInflater 객체는 일부 뷰만 객체화
- 시스템 서비스로 제공되므로 사용하려면 시스템 서비스 이용
```
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
inflater.inflate(R.layout.id, container, true)
```

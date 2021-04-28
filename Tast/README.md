
# 2021.04.28 Android Task
- Task는 어플리케이션에서 실행되는 액티비티를 보관하고, 관리하며 Stack형태의 연속된 Activity로 구성
- LIFO 형식으로 나중에 적재된 Activity 일수록 가장 먼저 사용
- ex) 1page > 2page > 3page -> back버튼 ->  3page > 2page > 1page
- 최초 적재 액티비티는 Root Activity라고 하며 어플리케이션 런처로부터 시작
- 마지막 적재되는 액티비티는 Top Activity라고 하며 현재 화면에 활성화 되어있는 액티비티
- Task의 Stack내 존재하는 액티비티들은 모두 묶어 BackGround, ForeGround로 함께 이동
  - 홈버튼 클릭 -> task interrupt = background 이동
  - 홈버튼 길게 클릭 -> recent task = foreground 이동
- Flag를 사용하여 Task내 액티비티의 흐름을 제어할 수 있다.

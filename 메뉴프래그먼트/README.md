# 2021.05.12 뻘짓거리고 만들었던 메뉴프래그먼트.. 제대로 만들어보기

## 디렉터리 구조
![image](https://user-images.githubusercontent.com/81352078/118011176-af218380-b38a-11eb-92c6-2e9bd2a49041.png)

## home, chat, mypage 메뉴를 만들어보자

### menu 폴더
- res 하위에 Android Resource Directory를 통해 menu 폴더생성
- bottom_navigation_menu.xml에 각 home, chat, mypage item 생성
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/home"
        android:icon="@drawable/ic_baseline_home_24"
        android:title="홈"/>

    <item android:id="@+id/chatList"
        android:icon="@drawable/ic_baseline_chat_24"
        android:title="채팅" />

    <item android:id="@+id/myPage"
        android:icon="@drawable/ic_baseline_person_pin_24"
        android:title="나의 정보" />
</menu>
```
- icon 속성을 통해 아이콘 추가 가능!

### layout 폴더
- activity_main.xml을 수정해준다
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

        <FrameLayout
            android:id="@+id/fragmentContainer"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintBottom_toTopOf="@+id/bottomNavigationView"
            android:layout_width="0dp"
            android:layout_height="0dp"/>

        <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/bottomNavigationView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            app:itemIconTint="@drawable/selector_menu_color"
            app:itemTextColor="@color/black"
            app:itemRippleColor="@null"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.0"
            app:layout_constraintStart_toStartOf="parent"
            app:menu="@menu/bottom_navigation_menu" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
- com.google.android.material.bottomnavigation.BottomNavigationView를 통해 메뉴 영역 설정
  - itemIconTint = 버튼 눌렸을때, 안눌렸을때 색 설정
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
      <item android:color="@color/black" android:state_checked="true"/>
      <item android:color="@color/gray_cc" android:state_checked="false"/> <!--  선택되지 않았을때 -->
    </selector>
    ```
  - itemRippleColor = 버튼 눌렸을 때 퍼짐효과 제거

### 각 Fragment 생성
- ChatListFragment, HomeFragment, MyPageFragment 생성
```
package com.example.aop_part2.joongo.chatlist

import androidx.fragment.app.Fragment
import com.example.aop_part2.joongo.R

class ChatListFragment : Fragment(R.layout.fragment_chatlist) {

}
```

### MainActivity
```
package com.example.aop_part2.joongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.aop_part2.joongo.chatlist.ChatListFragment
import com.example.aop_part2.joongo.home.HomeFragment
import com.example.aop_part2.joongo.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BottomLayoutButton을 눌렸을 때
        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        //초기 프래그먼트
        replaceFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatList -> replaceFragment(chatListFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // 트랜잭션 작업 설정
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }

}
```

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

## HomeFragment 작성
```
package com.example.aop_part2.joongo.home

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part2.joongo.R
import com.example.aop_part2.joongo.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    //ViewBinding 걸어주기
    private var binding : FragmentHomeBinding? = null
    //어댑터
    private lateinit var articleAdapter : ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        // ListAdapter로 RecyclerView 구현
        articleAdapter = ArticleAdapter()

        // 임의로 데이터 추가
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply{
            add(ArticleModel("0","aaaa",100000,"5000원",""))
            add(ArticleModel("0","bbbb",200000,"10000원",""))
            add(ArticleModel("0","cccc",300000,"20000원",""))
            add(ArticleModel("0","dddd",400000,"30000원",""))
            add(ArticleModel("0","eeee",500000,"40000원",""))
            add(ArticleModel("0","ffff",600000,"50000원",""))
        })

        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

    }
}
```
- 모델
```
package com.example.aop_part2.joongo.home

data class ArticleModel(
    val sellerId : String,
    val title : String,
    val createAt : Long,
    val price : String,
    val imageUrl :String
)
```
- 어댑터
```
package com.example.aop_part2.joongo.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aop_part2.joongo.databinding.ItemArticleBinding
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter : ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(articleModel : ArticleModel){

            //날짜로 바꾸기
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createAt)

            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price
            if(articleModel.imageUrl.isNotEmpty()) {
                Glide.with(binding.thumbnailImageView)
                    .load(articleModel.imageUrl)
                    .into(binding.thumbnailImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>(){
            // 현재아이템과 새아이템이 같은지
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createAt == newItem.createAt
            }
            // 현재 노출하고 있는 아이템과 새아이템과 equals 비교
            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}
```
- 아이템 xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingStart="16dp"
    android:paddingTop="16dp"
    android:paddingEnd="16dp">

    <ImageView
        android:id="@+id/thumbnailImageView"
        android:layout_width="110dp"
        android:layout_height="110dp"
        android:layout_marginBottom="16dp"
        android:scaleType="center"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/titleTextView"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:maxLines="2"
        android:textColor="@color/black"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/thumbnailImageView"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/dateTextView"
        app:layout_constraintStart_toStartOf="@id/titleTextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/titleTextView"
        android:layout_marginTop="6dp"
        android:layout_width="0dp"
        android:layout_height="wrap_content" />

    <TextView
        app:layout_constraintStart_toStartOf="@id/titleTextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/dateTextView"
        android:textSize="16sp"
        android:textStyle="bold"
        android:layout_marginTop="6dp"
        android:id="@+id/priceTextView"
        android:layout_width="0dp"
        android:layout_height="wrap_content" />

    <View
        android:layout_width="0dp"
        android:layout_height="1dp"
        android:background="@color/gray_ec"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

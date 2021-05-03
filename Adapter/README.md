# 2021.05.03 어댑터란?

## Adapter
- 데이터 테이블을 목록 형태로 보여주기 위해 사용
- 데이터를 다양한 형식의 리스트 형식으로 보여주기 위해 **데이터**와 **리스트 뷰** 사이에 존재
- 하나의 Object(객체)로서, 보여지는 View와 그 View에 올릴 Data를 연결하는 브릿지 역할
- 데이터의 원본을 받아 관리하며, 어댑터뷰가 출력할 수 있는 형태로 데이터를 제공하는 중간 개체 역할
- 즉, Adapter는 **어댑터뷰가 출력할 수 있는 데이터로 만들어 놓은 공간을 의미** 
- AdapterView는 **데이터를 출력하는 역할**

## AdapterView
- 정보를 효과적으로 처리하기 위해, View에 정보를 직접 주입하지 않고, Adapter라는 중간 매개체를 이용한다.
- AdapterView는 ViewGroup을 상속받으며, 내부적으로 많은 View를 담을 수 있다.
- ex) LiveView, GridView, Spinner, Gallery...

## ListView
- 데이터를 리스트 모양으로 보여주며 리스트 중 하나를 선택하는 용도
  - 리스트뷰에 나열할 내용을 String 배열로 미리 만든다.
  - 리스트뷰에 변수를 생성하고 XML의 <ListView>에 대응
  - ArrayAdapter<String>형의 변수를 선언
  - 리스트뷰의 모양과 내용을 String 배열로 채움 
  - 위에서 생성한 ArrayAdapter를 ListView에 적용
  - 리스트뷰의 항목을 클릭할 시 동작하는 리스너 작성
- activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <ListView
        android:id="@+id/ListView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </ListView>

</androidx.constraintlayout.widget.ConstraintLayout>
```
- MainActivity.kt
```
package com.example.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val list = arrayOf("스폰지밥","코난","심슨","검정고무신","원피스")
    private lateinit var listView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.ListView)
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener(){ _: AdapterView<*>, _: View, i: Int, _: Long ->
            Toast.makeText(this,"${list[i]}", Toast.LENGTH_SHORT).show()
        }

    }
}
```
![image](https://user-images.githubusercontent.com/81352078/116846485-53852680-ac23-11eb-8aab-69a6988b455a.png)


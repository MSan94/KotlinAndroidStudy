# Fragment
- 프래그먼트는 서로 다른 크기의 화면을 가진 기기에서 하나의 액티비티로 서로 다른 레이아웃을 구성
- 프래그먼트를 삽입하는 과정은 하나의 트랜잭션으로 관리
- begin transaction > add fragment > commit transaction


## MainActivity
```
class FragmentActivity : AppCompatActivity() {
    val binding by lazy { ActivityFragmentBinding.inflate(layoutInflater) }

    lateinit var listFragment:ListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment()
        
        // 생성된 프래그먼트에 값 전달
        binding.btnSend.setOnClickListener {
            listFragment.setValue("전달할 값")
        }
    }

    fun setFragment(){
        // 프래그먼트 생성 시 값 전달
        // arguments를 통해 전달하는데 이것은 프래그먼트 기본 프로퍼티로 선언 없이 사용 가능
        // arguments에 전달하면 생성된 프래그먼트에서 arguments로 꺼낼 수 있다.
        var bundle = Bundle()
        bundle.putString("key1","List Fragment")
        bundle.putInt("key2", 20200801)
        listFragment = ListFragment()
        listFragment.arguments = bundle
        // add(레이아웃, 프래그먼트) : 프래그먼트를 레이아웃에 추가
        // replace(레이아웃 , 프래그먼트) : 레이아웃에 삽입되어 있는 프래그먼트와 교체
        // remove(프래그먼트) : 지정한 프래그먼트 삭제
        // FrameLayout을 사용할 경우 프랜잭션 필요하지만, Fragment를 사용하면 불필요
        supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, listFragment)
                .commit()
    }

    // 프래그먼트 전환
    fun goDetail(){
        val detailFragment = DetailFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout,detailFragment)
            .addToBackStack("detail")
            .commit()
        // addToBackStack으로 프래그먼트 트랜잭션을 백스택에 담을 수 있다.
        // 뒤로가기 버튼으로 트랜잭션 전체를 마치 액티비티처럼 제거가능
    }
    fun goBack(){
        onBackPressed()
    }
}
```
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <TextView
        android:id="@+id/textView_Title"
        android:text="Activity"
        android:gravity="center"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="wrap_content"
        android:layout_height="50dp"/>
    <Button
        android:text="SEND"
        android:id="@+id/btnSend"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toEndOf="@+id/textView_Title"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <FrameLayout
        android:id="@+id/frameLayout"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView_Title"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="match_parent"
        android:layout_height="0dp"/>

    <!-- 레이아웃에서 프래그먼트 추가 -->
<!--    <fragment-->
<!--        tools:layout="@layout/fragment_list"-->
<!--        app:layout_constraintStart_toStartOf="parent"-->
<!--        app:layout_constraintEnd_toEndOf="parent"-->
<!--        app:layout_constraintTop_toBottomOf="@+id/textView_Title"-->
<!--        app:layout_constraintBottom_toBottomOf="parent"-->
<!--        android:layout_width="match_parent"-->
<!--        android:layout_height="0dp"/>-->

</androidx.constraintlayout.widget.ConstraintLayout>
```


## ListFragment
```
package com.example.appstudy.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appstudy.FragmentActivity
import com.example.appstudy.R
import com.example.appstudy.databinding.FragmentListBinding

class ListFragment : Fragment() {


    var fragmentActivity : FragmentActivity? = null //액티비티를 담아둘 변수 선언
    lateinit var binding:FragmentListBinding

    // 액티비티가 프래그먼트를 요청하면 onCreateView()를 통해 뷰를 만들어서 보여줌
    // inflater : 레이아웃 파일을 로드하기 위한 레이아웃 인플레이터를 기본으로 제공
    // container : 프래그먼트 레이아웃이 배치되는 부모 레이아웃 ( 액티비티의 레이아웃)
    // savedInstanceState : 상태 값 저장을 위한 보조 도구, 액티비티의 onCreate의 파라미터와 동일하게 동작
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.inflate(inflater,container,false)
        binding.btnNext.setOnClickListener { fragmentActivity?.goDetail() }

        // 액티비티에서 넘겨준 값 받기
        binding.textTitle.text = arguments?.getString("key1")
        binding.textValue.text = "${arguments?.getInt("key2")}"

        return binding.root //onCreateView()의 반환값이 View이므로 root를 넘겨줌
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // onAttach()를 통해 넘어온 Context를 캐스팅하여 액티비티를 담는다. (부모의 전체가 담겨있다.)
        // context의 타입이 액티비티를 확인하고 fragmentActivity 프로퍼티에 저장
        if(context is FragmentActivity) fragmentActivity = context
    }
    
    // 생성되어 있는 프래그먼트에 값 전달
    fun setValue(value:String){
        binding.textFromActivity.text = value
    }
}
```
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context=".fragment.ListFragment">

    <!-- TODO: Update blank fragment layout -->
    <TextView
        android:id="@+id/textView_Sample"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="List" />

    <Button
        android:id="@+id/btnNext"
        android:text="Next"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView_Sample"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <TextView
        android:id="@+id/textTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btnNext"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@id/textValue"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <TextView
        android:id="@+id/textValue"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btnNext"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toEndOf="@+id/textTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <TextView
        android:id="@+id/textFromActivity"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@id/textTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

## DetailFragment
```
package com.example.appstudy.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appstudy.FragmentActivity
import com.example.appstudy.MainActivity
import com.example.appstudy.R
import com.example.appstudy.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    lateinit var fragmentActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener { fragmentActivity.goBack() }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentActivity = context as FragmentActivity // 형변환
    }

}
```
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="#ff0000"
    android:clickable="true"
    tools:context=".fragment.DetailFragment">

    <TextView
        android:id="@+id/textView_Sample"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Detail" />

    <Button
        android:id="@+id/btnBack"
        android:text="Back"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView_Sample"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

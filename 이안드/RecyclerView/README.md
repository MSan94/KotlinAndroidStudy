# RecyclerView
- 스피너의 확장된 형태
- 리사이클러뷰 처럼 목록을 표시하는 컨테이너들은 표시될 데이터와 아이템 레이아웃을 어댑터에서 연결하므로 어댑터의 어떤 아이템 레이아웃을 사용하느냐에 따라 표시되는 모양을 다르게 만들 수 있다.

RecyclerViewActivity
```
package com.example.appstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.appstudy.databinding.ActivityRecyclerviewBinding
import com.example.appstudy.recycler.CustomAdapter
import com.example.appstudy.recycler.Memo

/**
 * 스피너의 확장된 형태
 * 리사이클러뷰 처럼 목록을 표시하는 컨테이너들은 표시될 데이터와 아이템 레이아웃을 어댑터에서 연결하므로
 * 어댑터의 어떤 아이템 레이아웃을 사용하느냐에 따라 표시되는 모양을 다르게 만들 수 있다.
 *
 * - 어댑터
 *
 */

class RecyclerViewActivity : AppCompatActivity() {
    val binding by lazy { ActivityRecyclerviewBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 사용할 데이터를 생성
        val data : MutableList<Memo> = loadData()
        var adapter = CustomAdapter()
        adapter.listData = data
        binding.recyclerView.adapter = adapter //어댑터 연결
        // 리사이클러뷰를 화면에 보여주는 형태를 결정하는 레이아웃 매니저 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.layoutManager = GridLayoutManager(this,3)
        //binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        // 3가지 레이아웃 매니저
        // LinearLayoutManager
        // 세로 : 기본으로 세로 스크롤 : LinearLayoutManager(this)
        // 가로 : 컬럼 개수를 지정해서 개수만큼 그리드 : (this,LineayLayoutManager.HORIZONTAL, false)

        // GridLayoutManager
        // 데이터 사이즈에 따라 그리드의 크기가 결정, 두번째 파라미터에 한 줄에 몇개아이템을 표시할 지 정의
        // GridLayoutManager(this, 3)

        // StaggeredGridLayoutManager
        // 세로 스크롤 : 컨텍스트를 사용하지 않아 this 필요 x, 첫 번째 파라미터는 한줄에 표시되는 아이템 개수, 두번째는 세로방향 설정
        // StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL OR HORIZONTAL)
    }

    fun loadData() : MutableList<Memo>{
        val data : MutableList<Memo> = mutableListOf()
        for(no in 1..100){
            val title = "제목입니다. $no"
            val date = System.currentTimeMillis()
            var memo = Memo(no,title,date)
            data.add(memo)
        }
        return data
    }
}
```


adapter
```
package com.example.appstudy.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appstudy.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

/**
 * 아이템 레이아웃은 ViewHolder 자체에서 만들어 지지 않고 어탭터가만들어 넘겨줌
 * 어댑터에서 넘겨주는 바인등을 Holder 클래스의 생성자에게서 받아 ViewHolder의 생성자로 넘겨주는 구조
 * ViewHolder의 생성자는 바인딩이 아닌 View를 필요로 하기에 binding.root를 전달
 * binding은 Holder 클래스 안에서 전역변수로 사영되야 하기에 val 키워드 사용
 */
class CustomAdapter : RecyclerView.Adapter<Holder>(){
    var listData = mutableListOf<Memo>() // 어댑터 목록 변수를 하나 선언

    // 화면에 그려지는 아이템 개수만큼 레이아웃 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // LayoutInflater.from : from에는 파라미터로 context를 전달, 넘겨주는 parent에서 꺼내기 가능
        // 두번쨰는 parent, 세번쨰는 false
        // inflater : 바인딩을 생성할 때 사용하는 인플레이터
        // parent : 생성되는 바인딩이 속하는 부모 뷰
        // attachToRoot : true면 attach해야 하는 대상으로 root를 지정하고 아래 붙임
        //                false면 뷰의 최상위 레이아웃의 속성을 기본으로 적용
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }
    // 생성된 아이템 레이아웃에 값 입력 후 목록에 출력
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // listData에서 현재 위치에 메모를 꺼내서 memo변수에 저장 후 홀더에 전달
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    // 목록에 보여줄 아이템의 개수
    override fun getItemCount(): Int {
        return listData.size
    }
}

class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    // 클릭 이벤트
    init {
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "클릭 : ${binding.textTitle.text}",Toast.LENGTH_SHORT).show()
        }
    }
    fun setMemo(memo : Memo){
        binding.textNo.text = "${memo.no}"
        binding.textTitle.text = "${memo.title}"
        var sdf = SimpleDateFormat("yyyy/MM/dd")
        var formattedDate = sdf.format(memo.timestamp)
        binding.textDate.text = formattedDate
    }
}
```


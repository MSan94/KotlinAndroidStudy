# 2021.05.16 swipe 애니메이션

![image](https://user-images.githubusercontent.com/81352078/118389547-6d802980-b665-11eb-8dfe-fff9ea86332c.png)
- https://github.com/yuyakaido/CardStackView

## 설정
```
implementation "com.yuyakaido.android:card-stack-view:2.3.4"
```

## Model Class
```
data class CardItem(
    val userId : String,
    var name : String
)

```

## layout 설정
```
    <com.yuyakaido.android.cardstackview.CardStackView
        android:id="@+id/cardStackView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="match_parent"
        android:layout_height="300dp"/>

```

## Activity 설정
```
    class LikeActivity : AppCompatActivity() , CardStackListener { .. }
    
    ...
    private val adapter = CardItemAdapter()
    private val CardItems = mutableListOf<CardItem>()
    ...
    
    private fun initCardStackView(){
        val stackView = findViewById<CardStackView>(R.id.cardStackView)
        stackView.layoutManager = CardStackLayoutManager(this)
        stackView.adapter = adapter
    }
```

## Adapter
```
package com.example.aop_part2.tinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CardItemAdapter : ListAdapter<CardItem, CardItemAdapter.ViewHolder>(DiffUtil) {

    //뷰바인딩 x
    inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view){

        fun bind(cardItem : CardItem){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<CardItem>(){
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}
```

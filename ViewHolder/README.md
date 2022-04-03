# ViewHolder
- 각 뷰를 보관하는 Holder 객체
- ListView / RecyclerView는 inflate를 최소화 하기 위해 뷰를 재사용하며 성능저하를 방지하기 위해 itemView에 각 요소를 바로 엑세스 할 수 있도록 저장해두고 사용하기 위한 객체

※ inflate? : xml 로 쓰여있는 View의 정의를 실제 VIew 객체로 만드는 것을 말함.

## RecyclerView의 ViewHoldr
- https://developer.android.com/guide/topics/ui/layout/recyclerview
- Recycler.Adapter를 상속 받을 때 ViewHolder Type을 지정해야 한다.
- Recycler.Adapter를 상속하면, onCreateViewHolder() , onBindViewHolder()등 몇개의 메소드를 오버라이딩 해야 한다.
- ***OnCreateViewHolder(ViewGroup parent, int viewType)*** 이 호출되면서 새로운 View를 생성할 때 실행되어 ViewHolder를 리턴
- ***onBindViewHolder(ViewHolder holder, int position)*** 에서 ViewHolder의 내용을 변경

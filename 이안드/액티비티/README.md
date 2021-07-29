# Activity

## Context
- 액티비티, 서비스 등의 컴포넌트와 스피너, 리사이클러뷰와 같은 화면 요소를 사용하기 위함
- 시스템을 사용하기 위한 정보 ( 프로퍼티 )와 도구 ( 메서드 )가 담겨 있는 클래스
- 대부분의 컨텍스트는 컴포넌트 실행 시 함께 생성되고, 생성된 컴포넌트가 가지고 있는 메서드를 호출해서 각각의 도구들을 사용할 수 있다.
- 즉, 안드로이드의 Context는 앱을 실행하기 위해 잘 짜여진 설계도의 개념으로 앱에서 사용하는 기본 기능이 담겨 있는 Class
- 액티비티는 컨텍스트를 상속받아 구현한다.
- 액티비티처럼 컨텍스트를 상속받은 컴포넌트들은 코드상에서 baseContext를 호출하는 것만으로 안드로이드의 기본 기능을 사용할 수 있다.

## Context의 종류
- 1) 애플리케이션 컨텍스트 ( Application Context )
  - 애플리케이션과 관련된 핵심 기능을 담고 있는 클래스
  - 앱을 통틀어서 하나의 인스턴스만 생성
  - 액티비티나 서비스 같은 컴포넌트에서 applicationContext를 직접 호출해서 사용할 수 있는데 호출하는 지점과 관계없이 모두 동일한 컨텍스트가 호출
- 2) 베이스 컨텍스트 ( Base Context )
  - 안드로이드의 4대 메이저 컴포넌트인 액티비티, 서비스, 컨텍트 프로바이더, 브로드캐스트 리시버의 기반 클래스
  - 각각의 컴포넌트에서 baseContext 또는 this로 컨텍스트를 사용할 수 있고, 컴포넌트의 개수만큼 컨텍스트도 함께 생성되기 때문에 호출되는 지점에 따라 서로 다른 컨텍스트가 호출


## Intent 
- 개발자가 어떤 의도를 가지고 메서드를 실행할 것인지를 인텐트에 담아서 안드로이드에 전달하면 안드로이드는 해당 인텐트를 해석하고 실행
- 1) 실행할 대상의 액티비티 이름과 전달할 데이터를 담아서 인텐트를 생성
- 2) 생성한 인텐트를 startActivity() 메서드에 담아서 호출하면 액티비티 매니저에 던달
- 3) 액티비티 매니저는 인텐트를 분석해서 지정한 액티비티를 실행
- 4) 전달된 인텐트는 최종 목적지인 타깃 액티비티까지 전달
- 5) 타깃 액티비티에서는 전달받은 인텐트에 데이터가 있다면 이를 꺼내서 사용 가능
- MainActivity
```
class MainActivity : AppCompatActivity(){
  val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
  .... onCreate(..){
    super onCreate(savedInstanceState)
    setContentView(binding.root)
    val intent = Intent(this, SubActivity::class.java)  // 인텐트 타겟 생성
    intent.putExtra("form1", "Hello Bundle") // 값 설정
    binding.btnStart.setOnClickListener { startActivity(intent) 
    
    
    // SubActivity 종료되면 MainActivity에 값 전달
    // ResultCode : 호출 시에 메인 액티비티에서 입력되는 코드 startActivityForResult 메서드에 인텐트와 함께 입력해서 호출한 코드를 구분
    // resultCode : 결과 처리 서브 액티비티에서 입력되는 코드 -> RESULT_OK, RESULT_CANCELED
    // data : 결과 처리 후 서브 액티비티가 넘겨주는 인텐트
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
      super.onActivityResult(requestCode, resultCode, data)   
      if(resultCode == RESULT_OK){
        val message = data?.getStringExtra("returnValue")
      }
    }
}
```

- SubActivity
```
class SubActivity : AppCompatActivity(){
  val binding by lazy { ActivitySubBinding.inflate(layoutInflater) }
  .... onCreate(..){
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    var text = intent.getStringExtra("from1")
    
    // SubActivity 종료되면 MainActivity에 값 전달
    binding.btnClose.setOnClickListener{
      val returnIntent = Intent()
      returnIntent.putExtra("returnValue", binding.editMessage.text.toString())
      setResult(RESULT_OK, resultIntent) // RESULT_OJ, RESULT_CANCELED , 첫번째 파라미터는 상태값, 두번째는 전달하려는 인텐트
      finish()
    }
  }
}
```



## 복습


```
package com.example.appstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.appstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = Intent(this, SubActivity::class.java);
        intent.putExtra("name", "안명성")
        intent.putExtra("age", "26")
        binding.btnMove.setOnClickListener{
            startActivityForResult(intent,1000)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TestValue", " 호출 ${requestCode} , ${resultCode}")
        if(requestCode == 1000 && resultCode == RESULT_OK){
            Log.d("TestValue", "성공")
            binding.textViewVal.text = data?.getStringExtra("returnValue")
        }else{
            Log.d("TestValue", "실패")
        }
    }
}
```


```
package com.example.appstudy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appstudy.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    val binding by lazy { ActivitySubBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")

        binding.textViewName.text = name
        binding.textViewAge.text = age

        binding.btnFinish.setOnClickListener {
            val returnVal = Intent()
            returnVal.putExtra("returnValue", "안녕~~~")
            setResult(RESULT_OK,returnVal)
            finish()
        }
    }
}
```

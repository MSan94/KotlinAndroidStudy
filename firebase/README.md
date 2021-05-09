# 2021-05-09 Firebase

## RealTime DB 설정
- 생성과정 생략
- 프로젝트 생성 후 google-service.json 설치 
- Authentication 시작
- project - app에 복사 ( 프로젝트에서 google-service.json을 설치하고 복사해도 Realtime DB 생성 시 google-service.json을 다시받아 복사 붙여넣기 )
![image](https://user-images.githubusercontent.com/81352078/117570862-14654280-b107-11eb-8394-027c072a280d.png)

## Firebase Email & password 로그인 및 회원가입
- 먼저 로그인이 되어있지 않을 경우 MainActivity에서 로그인창으로 이동
  - currentUser에 로그인 정보 저장 여부를 확인할 수 있다.
```
    override fun onStart() {
        super.onStart()
        //로그인 정보 저장 -> currentUser
        if(auth.currentUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
```
- Firebase 인스턴스 가져오기
```
private lateinit var auth : FirebastAuth
auth = Firebast.auth
```
- addTextChangedListener를 통해 email, id 입력값이 있는지 확인
  - 없다면 버튼 비활성
```
    private fun initEmailAndPasswordEditText() {
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        // 텍스트 입력시마다 리스너로 이벤트 내려옴
        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable
        }
        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable
        }
    }
```
- 회원가입
```
    private fun initSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            //회원가입
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
```
- 로그인
```
    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password) //이메일과 패스워드 파라미터로 파이어베이스 signin 기능 사용
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
```
![Uploading image.png…]()

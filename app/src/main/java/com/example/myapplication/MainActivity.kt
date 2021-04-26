package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() , FragmentOne.onDataPassListener {
    private val button : Button by lazy {findViewById(R.id.button)}
    private val fragmentOne = FragmentOne()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = Bundle()
        bundle.putString("Key", "Hello")
        fragmentOne.arguments = bundle

        button.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment1, fragmentOne)
            fragmentTransaction.commit()
        }
    }

    override fun onDataPass(data: String?) {
        Toast.makeText(this,"$data", Toast.LENGTH_SHORT).show()
    }

}
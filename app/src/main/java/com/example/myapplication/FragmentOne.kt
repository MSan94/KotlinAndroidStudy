package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentOne : Fragment(){

    interface onDataPassListener{
        fun onDataPass(data : String?)
    }
    lateinit var dataPassListener: onDataPassListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPassListener = context as onDataPassListener //형변환
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_one, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataPassListener.onDataPass("프래그먼트입니다.")

        val change_btn : Button by lazy {view.findViewById(R.id.change_btn)}
        val hello : TextView by lazy {view.findViewById(R.id.hello)}
        change_btn.setOnClickListener {
            hello.text = "Clicked!"
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        var data = arguments?.getString("Key")
        Toast.makeText(context,"$data",Toast.LENGTH_SHORT).show()
        super.onActivityCreated(savedInstanceState)
    }
}
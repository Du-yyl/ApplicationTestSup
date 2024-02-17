package com.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.applicationtest.databinding.ActivityMainBinding
import com.viewModel.MainViewModel
import com.framework.foundation.ActivityBase

class MainActivity : ActivityBase() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        这两种方式只是使用方式不同，但是整体效果相同
//        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 函数绑定
        functionBind()

        // 数据绑定
        dataBind()
    }

    /**
     * 数据绑定
     */
    private fun dataBind() {

    }

    private fun functionBind() {

    }
}
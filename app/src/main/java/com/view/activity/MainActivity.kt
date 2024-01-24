package com.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.applicationtest.R
import com.example.applicationtest.databinding.ActivityMainBinding
import com.viewModule.MainViewModule
import com.framework.foundation.ActivityBase
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService

class MainActivity : ActivityBase() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
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
        viewModel.weatherIndex.observe(this) {
            binding.textView.text = it.toString()
        }
    }

    private fun functionBind() {

        binding.btn.setOnClickListener {
            viewModel.updateData()
        }
        binding.btn1.setOnClickListener {
            viewModel.saveData()
        }

        binding.btn3.setOnClickListener {
            viewModel.updateDataProto()
        }
        binding.btn4.setOnClickListener {
            viewModel.getDataProto()
        }

        binding.btn5.setOnClickListener {
            viewModel.updateSaveUserList()
        }
        binding.btn6.setOnClickListener {
            viewModel.getUserListData()
        }
        binding.btn7.setOnClickListener {
            viewModel.updateList()
        }


        // ShareP数据操作
        binding.btn8.setOnClickListener {
            viewModel.sharePUpdata()
        }
        binding.btn9.setOnClickListener {
            viewModel.sharePGetData()
        }

        // RoomDatabase 数据操作
        binding.btn10.setOnClickListener {
            viewModel.roomDataUpdate()
        }
        binding.btn11.setOnClickListener {
            viewModel.getRoomData()
        }

    }
}
package com.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.applicationtest.databinding.ActivityLoginBinding
import com.framework.foundation.ActivityBase
import com.viewModule.LoginViewModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @time :2024/1/17 21:39 20
 * @className :Login
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class LoginActivity : ActivityBase() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // 数据源绑定
        dataBind()
        // 数据绑定 初始化
        viewModel.loadData()

        // 检查是否勾选自动登陆
        Log.d(
            "LoginActivity",
            "onCreate: viewModel.autoLogin.value ${viewModel.autoLogin.value}"
        )
        if (viewModel.autoLogin.value == true) {

            loginJump()
        }

        // 函数绑定
        functionBind()
    }

    /**
     * 数据绑定
     */
    private fun dataBind() {
        viewModel.rememberMe.observe(this) {
            binding.loginRememberMe.isChecked = it
        }
        viewModel.autoLogin.observe(this) {
            binding.loginAutoLogin.isChecked = it
        }
        viewModel.whetherCanAutoLogin.observe(this) {
            if (it) {
                loginJump()
            }
        }
        viewModel.username.observe(this) {
            binding.loginEditUsernameValue.text = Editable.Factory.getInstance().newEditable(it)
        }
        viewModel.password.observe(this) {
            binding.loginEditPasswordValue.text = Editable.Factory.getInstance().newEditable(it)
        }
    }

    /**
     * 函数绑定
     */
    private fun functionBind() {
        binding.loginLoginButton.setOnClickListener(this::login)
        binding.loginRememberMe.setOnCheckedChangeListener(this::rememberMeCheckedChanged)
        binding.loginAutoLogin.setOnCheckedChangeListener(this::autoLoginCheckedChanged)
    }

    private fun autoLoginCheckedChanged(compoundButton: CompoundButton?, b: Boolean) {
        viewModel.autoLogin.value = b
    }

    /**
     * 记住我 复选框更改
     */
    private fun rememberMeCheckedChanged(compoundButton: CompoundButton?, b: Boolean) {
        viewModel.rememberMe.value = b
    }

    /**
     * 自动登陆
     */
    private fun loginJump() {
        // 创建 Intent 对象，指定当前 Activity 和目标 Activity
        val intent = Intent(this@LoginActivity, MainActivity::class.java)

        // (可选) 通过 Intent 传递数据
//               intent.putExtra("key", "value")

        // 定义动画
        val options = ActivityOptions.makeCustomAnimation(
            this@LoginActivity,
            com.example.applicationtest.R.anim.login_animation_start,
            com.example.applicationtest.R.anim.login_animation_end
        )

        // 开启新的任务队列，不能通过返回回到上一部
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        // 启动目标 Activity
        startActivity(intent, options.toBundle())
//                    startActivity(intent)

    }

    /**
     * 登录按钮
     */
    private fun login(view: View?) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // 登录
                val loginResult = viewModel.login(
                    binding.loginEditUsernameValue.text.toString(),
                    binding.loginEditPasswordValue.text.toString()
                )

                if (loginResult) {
                    loginJump()
                } else {
                    withContext(Dispatchers.Main) {
                        Log.d("LoginActivity", "login: 登陆失败")
                        Toast.makeText(this@LoginActivity, "登陆失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
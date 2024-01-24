package com.viewModule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.constant.DataStorePrefValue.PasswordValue
import com.constant.DataStorePrefValue.RememberMeValue
import com.constant.DataStorePrefValue.UserNameValue
import com.constant.DataStorePrefValue.AutoLoginValue
import com.dataStorage.dataStore.DataStorePreferencesHelper
import com.dataStorage.roomData.RoomDatabaseHelper
import com.dataStorage.roomData.services.UserService
import kotlinx.coroutines.launch

/**
 * @time :2024/1/18 11:16 08
 * @className :LoginViewModule
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class LoginViewModule : ViewModel() {
    /**
     * 记住我
     */
    val rememberMe = MutableLiveData<Boolean>().apply {
        value = false
    }

    /**
     * 自动登陆
     */
    val autoLogin = MutableLiveData<Boolean>().apply {
        value = false
    }
    /**
     * 自动登陆
     */
    val whetherCanAutoLogin = MutableLiveData<Boolean>().apply {
        value = false
    }

    /**
     * 用户名
     */
    val username = MutableLiveData<String>().apply {
        value = ""
    }

    /**
     * 记住我
     */
    val password = MutableLiveData<String>().apply {
        value = ""
    }


    /**
     * 从 DataStore 初始化数据
     */
    fun loadData() {
        viewModelScope.launch {
            DataStorePreferencesHelper.getInstance().also {
                rememberMe.value = it.getValue(RememberMeValue, false)
                username.value = it.getValue(UserNameValue, "")
                password.value = it.getValue(PasswordValue, "")
                autoLogin.value = it.getValue(AutoLoginValue, false).also {
                    // 获取数据后直接返回页面
                    whetherCanAutoLogin.value = it
                }
            }
        }
    }

    /**
     * 登录
     */
    fun login(username: String, password: String): Boolean {
        Log.d("LoginViewModule", "login() called with: username = $username, password = $password")
        val userService = UserService(RoomDatabaseHelper.getInstance().userDatabaseDao)

        return userService.login(username, password).also {
            viewModelScope.launch {
                DataStorePreferencesHelper.getInstance().also {
                    var usernameSaveValue = ""
                    var passwordSaveValue = ""
                    var rememberMeSaveValue = false
                    var autoLoginSaveValue = false
                    if (rememberMe.value == true) {
                        usernameSaveValue = username;
                        passwordSaveValue = password;
                        rememberMeSaveValue = true
                        autoLoginSaveValue = autoLogin.value == true
                    }

                    Log.d(
                        "LoginViewModule", "login: 登陆保存数据库的值：用户名：$usernameSaveValue," +
                                "密码：$passwordSaveValue, " +
                                "记住我: $rememberMeSaveValue, " +
                                "自动登陆： $autoLoginSaveValue"
                    )

                    // 数据整理完毕，保存数据库
                    it.saveValue(UserNameValue, usernameSaveValue)
                    it.saveValue(PasswordValue, passwordSaveValue)
                    it.saveValue(RememberMeValue, rememberMeSaveValue)
                    it.saveValue(AutoLoginValue, autoLoginSaveValue)

                }
            }
        }
    }
}
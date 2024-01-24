package com.viewModule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.android.datastore.UserPreferences
import com.dataStorage.dataStore.DataStorePreferencesHelper
import com.dataStorage.dataStore.DataStoreProtoHelper
import com.dataStorage.dataStore.storageServices.UserListRepository
import com.dataStorage.dataStore.storageServices.UserRepository
import com.dataStorage.roomData.RoomDatabaseHelper
import com.dataStorage.roomData.entity.User
import com.dataStorage.roomData.services.UserService
import com.dataStorage.sharedPref.SharePrefHelper
import com.enums.UserTypeEnum
import com.expand.toJson
import com.utils.JsonUtil
import com.utils.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 * @time :2024/1/3 17:15 57
 * @className :MainViewMoudle
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */


class MainViewModule : ViewModel() {

    private var index = 1

    private var _flag = false

    /**
     * 提供监视的属性
     */
    val weatherIndex = MutableLiveData<Int>(1)

    private val instance = DataStorePreferencesHelper.getInstance()

    // ------------------------------------------------------------------

    fun updateData() {

        index++
        weatherIndex.postValue(index)

        viewModelScope.launch {
            instance.saveValue("set_v", index)
        }
        Log.d("MainViewModule", "updateData: 更新数据 index: $index")
    }

    fun saveData() {
        viewModelScope.launch {
            val i = instance.getValue("set_v", 0)
            Log.d("MainViewModule", "saveData: 保存数据 获取数据： $i")
        }
    }

    // ------------------------------------------------------------------

    fun updateDataProto() {
        val userProto = DataStoreProtoHelper.getInstance().getUserProto()
        _flag = !_flag

        Log.d("MainViewModule", "updateDataProto: 修改数据 $userProto")
        viewModelScope.launch {
            UserRepository(userProto).updateUser(1, "张三", _flag)
        }
    }

    fun getDataProto() {
        val userProto = DataStoreProtoHelper.getInstance().getUserProto()
        val flow = UserRepository(userProto).getUser
        Log.d("MainViewModule", "getDataProto: 点击")
        viewModelScope.launch {
            val first = flow.first()
            Log.d("MainViewModule", "getDataProto: ${first.sex}")
        }
    }


    // ------------------------------------------------------------------


    /**
     * 批量修改数据
     */
    fun updateSaveUserList() {
        val userListProto = DataStoreProtoHelper.getInstance().getUserListProto()
        val userListRepository = UserListRepository(userListProto)

        // 单个用户信息
        val userPreferences = UserPreferences.newBuilder().apply {
            age = index++
            userName = "张三"
            sex = true
        }
        val userBuild = userPreferences.build()
        viewModelScope.launch {
            userListRepository.addUser(userBuild)
        }

    }

    /**
     * 批量获取数据
     */
    fun getUserListData() {
        val userListProto = DataStoreProtoHelper.getInstance().getUserListProto()
        val userListRepository = UserListRepository(userListProto)

        viewModelScope.launch {
            userListRepository.removeUser { index, userPreferences ->
                (userPreferences.age % 2) != 0
            }

            val newUsers = userListRepository.getAllUsers()
            for (allUser in newUsers) {
                Log.d(
                    "MainViewModule",
                    "getUserListData: allUser: ${allUser.age} - ${allUser.userName}"
                )
            }
        }
    }

    fun updateList() {
        val userListProto = DataStoreProtoHelper.getInstance().getUserListProto()
        val userListRepository = UserListRepository(userListProto)
        viewModelScope.launch {
            userListRepository.modifyUser { index, userPreferences ->
                if (userPreferences.age < 5) {
                    val build = userPreferences.toBuilder().apply {
                        userName = "王武"
                    }.build()

                    return@modifyUser build
                }
                userPreferences
            }
            val newUsers = userListRepository.getAllUsers()
            for (allUser in newUsers) {
                Log.d(
                    "MainViewModule",
                    "getUserListData: allUser: ${allUser.age} - ${allUser.userName}"
                )
            }
        }

    }


    //===================SharedPreferences数据操作===================================================
    fun sharePUpdata() {
        _flag = !_flag
        val preferences = SharePrefHelper.getInstance()
        val edit = preferences.edit()
        viewModelScope.launch {
            edit.putString("key1", "字符串")
            edit.putBoolean("key2", _flag)
            edit.putInt("key3", index++)

            if (edit.commit()) {
                Log.d("MainViewModule", "sharePUpdata: 数据保存成功")
            } else {
                Log.d("MainViewModule", "sharePUpdata: 数据保存失败，请稍后重试")
            }
        }
    }

    fun sharePGetData() {
        val preferences = SharePrefHelper.getInstance()
        val string = preferences.getString("key1", "默认值")
        val boolean = preferences.getBoolean("key2", false)

        Log.d("MainViewModule", "sharePGetData: key1: $string")
        Log.d("MainViewModule", "boolean: key2: $boolean")
    }


    // Room Database
    fun roomDataUpdate() {
        val databaseHelper = RoomDatabaseHelper.getInstance()
        val userService = UserService(databaseHelper.userDatabaseDao)
        databaseHelper.userDatabaseDao.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val insert = userService.insert(
                        User(
                            name = "张三",
                            userName = "Admin",
                            userType = UserTypeEnum.Admin,
                            password = "123456"
                        )
                    )
                    Log.d("MainViewModule", "roomDataUpdate: insert:$insert")
                }
            }
        }
    }

    fun getRoomData() {
        val databaseHelper = RoomDatabaseHelper.getInstance()
        val userService = UserService(databaseHelper.userDatabaseDao)
        databaseHelper.userDatabaseDao.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val users = userService.findAll()
                    for (user in users) {
                        Log.d("MainViewModule", "getRoomData: user: ${user.toJson()}")
                    }
                }
            }
        }
    }
}
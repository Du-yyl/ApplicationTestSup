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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @time :2024/1/25 8:56 07
 * @className :DataStorageFragmentViewModule
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class DataStorageFragmentViewModel : ViewModel() {

    private var index = 1

    private var _flag = false

    /**
     * 提供监视的属性
     */
    val weatherIndex = MutableLiveData<Int>(1)

    // ------------------------------------------------------------------

    /**
     * DataStorePref更新数据
     */
    fun dataStorePrefUpdate() {
        index++
        weatherIndex.postValue(index)

        viewModelScope.launch {
            DataStorePreferencesHelper.getInstance().saveValue("set_v", index)
        }
        Log.d("MainViewModule", "updateData: 更新数据 index: $index")
    }

    /**
     * DataStorePref保存数据
     */
    fun dataStorePrefSaveData() {
        viewModelScope.launch {
            val i = DataStorePreferencesHelper.getInstance().getValue("set_v", 0)
            Log.d("MainViewModule", "saveData: 保存数据 获取数据： $i")
        }
    }

    // ------------------------------------------------------------------

    /**
     * DataStoreProto更新数据
     */
    fun dataStoreProtoUpdate() {
        val userProto = DataStoreProtoHelper.getInstance().getUserProto()
        _flag = !_flag

        Log.d("MainViewModule", "updateDataProto: 修改数据 $userProto")
        viewModelScope.launch {
            UserRepository(userProto).updateUser(1, "张三", _flag)
        }
    }

    /**
     * DataStoreProto保存数据
     */
    fun dataStoreProtoSaveData() {
        val userProto = DataStoreProtoHelper.getInstance().getUserProto()
        val flow = UserRepository(userProto).getUser
        viewModelScope.launch {
            val first = flow.first()
            Log.d("MainViewModule", "getDataProto: ${first.sex}")
        }
    }


    // ------------------------------------------------------------------


    /**
     * DataStoreProto增加数据
     */
    fun dataStoreProtoAdd() {
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
     * DataStoreProto批量获取数据
     */
    fun dataStoreProtoGetList() {
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

    /**
     * DataStoreProto通过数组更新数据
     */
    fun dataStoreProtoUpdateForList() {
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

    /**
     * Room获取数据
     */
    fun roomDataInsert() {
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

    /**
     * Room获取数据
     */
    fun roomDataFind() {
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
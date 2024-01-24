package com.dataStorage.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.asLiveData
import com.codelab.android.datastore.UserList
import com.codelab.android.datastore.UserPreferences
import com.constant.ConstName.USER_LIST_PROTO_NAME
import com.constant.ConstName.USER_PROTO_NAME
import com.dataStorage.dataStore.data.UserListPreferencesSerializer
import com.dataStorage.dataStore.data.UserPreferencesSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import splitties.init.appCtx

/**
 * @time :2024/1/7 14:56 43
 * @className :DataStoreProtoHelper
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class DataStoreProtoHelper private constructor() {

    private val Context.protoUserDataStore: DataStore<UserPreferences> by dataStore(
        fileName = USER_PROTO_NAME,
        serializer = UserPreferencesSerializer
    )

    private val Context.protoUserListDataStore: DataStore<UserList> by dataStore(
        fileName = USER_LIST_PROTO_NAME,
        serializer = UserListPreferencesSerializer
    )



    companion object {

        private var INSTANCE: DataStoreProtoHelper? = null

        fun getInstance(): DataStoreProtoHelper {
            return INSTANCE ?: synchronized(this) {
                DataStoreProtoHelper().also {
                    INSTANCE = it
                }
            }
        }
    }

    /**
     * 获取用户数据对象
     */
    fun getUserProto(): DataStore<UserPreferences> = appCtx.protoUserDataStore

    /**
     * 获取用户列表数据对象
     */
    fun getUserListProto(): DataStore<UserList> = appCtx.protoUserListDataStore

}
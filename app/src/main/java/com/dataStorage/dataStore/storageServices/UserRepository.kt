package com.dataStorage.dataStore.storageServices

import androidx.datastore.core.DataStore
import com.codelab.android.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * @time :2024/1/7 16:49 05
 * @className :UserRepository
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class UserRepository(private val dataStore: DataStore<UserPreferences>) {

    /**
     * 获取用户信息
     */
    val getUser: Flow<UserPreferences>
        get() = dataStore.data

    suspend fun updateUser(age: Int, name: String, sex: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setAge(age).setUserName(name).setSex(sex)
                .build()
        }
    }

}


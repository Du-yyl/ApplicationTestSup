package com.dataStorage.dataStore.storageServices

import androidx.datastore.core.DataStore
import com.codelab.android.datastore.UserList
import com.codelab.android.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * @time :2024/1/7 21:13 26
 * @className :UserListRepository
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class UserListRepository(private val dataStore: DataStore<UserList>) {
    val getUsers: Flow<UserList>
        get() = dataStore.data

    suspend fun addUser(newUser: UserPreferences) {
        dataStore.updateData { currentUserList ->
            // 在此处更新用户数组
            currentUserList.toBuilder()
                .addUsers(newUser)
                .build()
        }
    }

    /**
     * 增加多个元素
     * @param newUsers 要增加的元素列表
     * @param defaultIndex 是否按照默认 index 新增，如果指定为 false 则使用 [newUsers] 的下标进行元素新增
     */
    suspend fun addUser(newUsers: List<UserPreferences>, defaultIndex: Boolean = true) {
        if (newUsers.isEmpty()) return

        dataStore.updateData {
            val builder = it.toBuilder()
            newUsers.forEachIndexed { index, userPreferences ->
                if (defaultIndex) {
                    builder.addUsers(userPreferences)
                } else {
                    builder.addUsers(index, userPreferences)
                }
            }
            builder.build()
        }
    }

    /**
     * 删除单个元素
     */
    suspend fun removeUser(index: Int) {
        dataStore.updateData { currentUserList ->
            // 在此处更新用户数组
            currentUserList.toBuilder()
                .removeUsers(index)
                .build()
        }
    }

    /**
     * 通过循环依次判断是否需要删除
     * @param action 删除前会调用该函数，返回结果为 true 则删除指定内容 反之不删除
     */
    suspend fun removeUser(action: (index: Int, userPreferences: UserPreferences) -> Boolean) {
        val allUses = getAllUsers()
        if (allUses.isEmpty()) return

        // 删除元素个数【元素删除后后续元素依次提前】
        var removeCount = 0
        dataStore.updateData {
            val builder = it.toBuilder()

            allUses.forEachIndexed { index, userPreferences ->
                if (action(index, userPreferences)) {
                    builder.removeUsers(index - removeCount)
                    removeCount++
                }
            }

            builder.build()
        }
    }

    suspend fun modifyUser(index: Int, newUser: UserPreferences) {
        dataStore.updateData { currentUserList ->
            // 在此处更新用户数组
            val modifiedBuilder = currentUserList.toBuilder()
            modifiedBuilder.setUsers(index, newUser)
            modifiedBuilder.build()
        }
    }

    /**
     * 通过数组更新数据
     */
    suspend fun modifyUser(action: (index: Int, userPreferences: UserPreferences) -> UserPreferences) {
        val allUses = getAllUsers()
        if (allUses.isEmpty()) return

        dataStore.updateData { currentUserList ->
            val modifiedBuilder = currentUserList.toBuilder()
            allUses.forEachIndexed { index, userPreferences ->
                val user = action(index, userPreferences)
                // 更新数据
                modifiedBuilder.setUsers(index, user)
            }
            modifiedBuilder.build()
        }
    }

    suspend fun getAllUsers(): List<UserPreferences> {
        return dataStore.data.firstOrNull()?.usersList ?: emptyList()
    }

    /**
     * 清空所有数据
     */
    suspend fun clearUsers() {
        dataStore.updateData {
            it.toBuilder().clearUsers().build()
        }
    }

}


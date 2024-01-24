package com.dataStorage.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.constant.ConstName
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import splitties.init.appCtx
import java.lang.Exception

/**
 * @time :2024/1/4 15:50 08
 * @className :DataStorePreferencesHelper
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class DataStorePreferencesHelper private constructor() {

    val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = ConstName.USER_PREFERENCES_NAME
    )

    companion object {

        private var INSTANCE: DataStorePreferencesHelper? = null

        fun getInstance(): DataStorePreferencesHelper {
            return INSTANCE ?: synchronized(this) {
                DataStorePreferencesHelper().also {
                    INSTANCE = it
                }
            }
        }
    }

    /**
     * 保存单个数据
     */
    suspend inline fun <reified T> saveValue(keyName: String, value: T) {
        val instance = getInstance()
        Log.d("DataStorePreferencesHelper", "saveValue: appCtx: $appCtx")
        appCtx.preferencesDataStore.edit {
            when (value) {
                is Int -> it[intPreferencesKey(keyName)] = value
                is String -> it[stringPreferencesKey(keyName)] = value
                is Boolean -> it[booleanPreferencesKey(keyName)] = value
                else -> throw Exception("不支持的类型")
            }
        }
    }

    /**
     * 获取单个数据
     */
    suspend inline fun <reified T> getValue(keyName: String, defaultValue: T): T {
        return appCtx.preferencesDataStore.data.map {
            when (defaultValue) {
                is Int -> it[intPreferencesKey(keyName)] ?: defaultValue
                is String -> it[stringPreferencesKey(keyName)] ?: defaultValue
                is Boolean -> it[booleanPreferencesKey(keyName)] ?: defaultValue
                else -> throw Exception("类型不匹配")
            }
        }.first() as T
    }

}
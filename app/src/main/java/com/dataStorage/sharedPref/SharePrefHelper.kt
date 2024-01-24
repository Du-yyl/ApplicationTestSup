package com.dataStorage.sharedPref

import android.content.Context
import android.content.SharedPreferences
import com.constant.ConstName.SHARE_PREFERENCES_NAME
import com.dataStorage.dataStore.DataStorePreferencesHelper
import splitties.init.appCtx

/**
 * @time :2023/9/13 9:24 33
 * @className :ShareHelper
 * @package :My Application
 * @author :weiyp
 * @description :
 * <p>
 */
class SharePrefHelper private constructor(){
    companion object {
        @Volatile
        private var INSTANCE: SharedPreferences? = null

        fun getInstance(): SharedPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: appCtx.getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE).also {
                    INSTANCE = it
                }
            }
        }
    }
}

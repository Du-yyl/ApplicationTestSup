package com.constant

import com.dataStorage.sharedPref.SharePrefHelper

/**
 * @time :2024/1/4 15:57 12
 * @className :ConstName
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

/**
 * 常量名字
 */
object ConstName {
    /**
     * dataStore 数据库名字
     */
    const val USER_PREFERENCES_NAME = "my_dataStore"

    /**
     * User Proto 存储文件名字
     */
    const val USER_PROTO_NAME = "userProto.pb"

    /**
     * Proto 列表数据存储温暖名字
     */
    const val USER_LIST_PROTO_NAME = "userListProto.pb"

    /**
     * SharedPreferences 存储文件名称
     */
    const val SHARE_PREFERENCES_NAME = "appConfig"

    /**
     * 数据库名称
     */
    const val DATABASE_NAME = "yuanll_01.db"

    /**
     * 程序默认密钥
     */
    const val APP_DEFAULT_KEY_ALIAS = "DefaultKeyAlias"
}
package com.dataStorage.roomData

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.constant.ConstName.DATABASE_NAME
import com.dataStorage.roomData.dao.UserDao
import com.dataStorage.roomData.entity.User
import splitties.init.appCtx

/**
 * @time :2024/1/13 18:48 50
 * @className :RoomDatabaseHelper
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
@Database(
    // 有哪些表，多张表用 ',' （逗号） 隔开
    entities = [User::class],
    // 指定数据库版本号，后续数据库的升级正是依据版本号来判断的
    version = 3,
    exportSchema = false,
    // 自动迁移（数据库升级）
    autoMigrations = []
)
abstract class RoomDatabaseHelper : RoomDatabase() {
    /**
     * 用户 Dao
     */
    abstract val userDatabaseDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabaseHelper? = null

        fun getInstance(context: Context = appCtx): RoomDatabaseHelper {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        /* context = */  context.applicationContext,
                        /* klass = */  RoomDatabaseHelper::class.java,
                        /* 数据库名字 = */ DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
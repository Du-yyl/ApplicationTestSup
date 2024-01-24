package com.dataStorage.roomData.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.enums.UserTypeEnum

@Entity(
    tableName = "user_table",
//    用户名不能重复
    indices = [Index(value = ["user_name"], unique = true)],
)
data class User(
    // 标记主键
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0,

    val name: String,

    val password: String,

    /**
     * 用户名不能重复
     */
    @ColumnInfo("user_name")
    val userName: String,

    @ColumnInfo("user_type")
    val userType: UserTypeEnum
)

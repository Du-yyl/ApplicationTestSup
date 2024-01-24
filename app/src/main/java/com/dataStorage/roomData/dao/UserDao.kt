package com.dataStorage.roomData.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dataStorage.roomData.entity.User
import com.enums.UserTypeEnum

/**
 * @time :2024/1/13 19:24 22
 * @className :UserDao
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

@Dao
interface UserDao {
    @Insert
    fun insert(user: User): Long

    @Insert
    fun insert(users: List<User>)

    @Update
    fun update(user: User): Int

    @Update
    fun update(users: List<User>)

    @Delete
    fun delete(user: User): Int

    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun findById(id: Long): User?

    @Query("SELECT * FROM user_table")
    fun findAll(): List<User>

    /**
     * 通过名称查询用户【用户名称不能重复】
     */
    @Query("SELECT * FROM user_table WHERE user_name = :userName")
    fun findByUsername(userName: String): User?

    /**
     * 查询指定类型的用户
     */
    @Query("SELECT * FROM user_table WHERE user_type = :userTypeEnum")
    fun findByUsertype(userTypeEnum: UserTypeEnum): List<User>

}
package com.dataStorage.roomData.services

import android.R.attr
import com.dataStorage.roomData.dao.UserDao
import com.dataStorage.roomData.entity.User
import com.enums.UserTypeEnum


/**
 * @time :2024/1/15 16:16 18
 * @className :UserService
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class UserService(private val userDao: UserDao) {

    fun insert(user: User) = userDao.insert(user)

    fun insert(users: List<User>) = userDao.insert(users)

    fun update(user: User) = userDao.update(user)

    fun update(users: List<User>) = userDao.update(users)

    fun delete(user: User) = userDao.delete(user)

    fun findById(id: Long) = userDao.findById(id)

    fun findAll() = userDao.findAll()

    /**
     * 通过名称查询用户【用户名称不能重复】
     */
    fun findByUsername(userName: String) = userDao.findByUsername(userName)

    /**
     * 查询指定类型的用户
     */
    fun findByUsertype(userTypeEnum: UserTypeEnum) = userDao.findByUsertype(userTypeEnum)

    /**
     * 登录
     */
    fun login(userName: String, password: String): Boolean {
        // 登录逻辑，可能涉及数据库查询等操作
        userDao.findByUsername(userName).also {
            it ?: return false
            return it.password == password
        }
    }
}
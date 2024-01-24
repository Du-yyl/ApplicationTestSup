package com.framework.foundation

import android.app.Application
import android.util.Log
import com.dataStorage.roomData.RoomDatabaseHelper
import com.dataStorage.roomData.entity.User
import com.dataStorage.roomData.services.UserService
import com.enums.UserTypeEnum
import com.exceptions.GlobalExceptionHandler
import com.expand.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import splitties.systemservices.launcherApps

/**
 * @time :2024/1/7 15:18 26
 * @className :ApplicationBase
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
open class ApplicationBase : Application() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        val coolText =
            """
  ___    ___ ___  ___  ________  ________   ___       ___          
 |\  \  /  /|\  \|\  \|\   __  \|\   ___  \|\  \     |\  \         
 \ \  \/  / | \  \\\  \ \  \|\  \ \  \\ \  \ \  \    \ \  \        
  \ \    / / \ \  \\\  \ \   __  \ \  \\ \  \ \  \    \ \  \       
   \/  /  /   \ \  \\\  \ \  \ \  \ \  \\ \  \ \  \____\ \  \____  
 __/  / /      \ \_______\ \__\ \__\ \__\\ \__\ \_______\ \_______\
|\___/ /        \|_______|\|__|\|__|\|__| \|__|\|_______|\|_______|
\|___|/                                                            
                                                                   """.trimIndent()
        Log.d("ApplicationBase", coolText)
        Log.d("ApplicationBase", "程序启动")
        // 全局异常捕捉
        GlobalExceptionHandler.setupExceptionHandler(this)

        // 数据库初始化
        initData()
    }

    /**
     * 数据初始化
     */
    private fun initData() {

        mainScope.launch {
            withContext(Dispatchers.IO) {
                val userService = UserService(RoomDatabaseHelper.getInstance().userDatabaseDao)
                val admin = userService.findByUsertype(UserTypeEnum.Admin).toMutableList()
                admin.ifEmpty {
                    val user = User(
                        name = "Admin",
                        userName = "Admin",
                        userType = UserTypeEnum.Admin,
                        password = "123456"
                    )
                    userService.insert(user)
                    admin.add(user)
                }
                val normal = userService.findByUsertype(UserTypeEnum.Normal).toMutableList()
                normal.ifEmpty {
                    val user = User(
                        name = "Normal",
                        userName = "Normal",
                        userType = UserTypeEnum.Normal,
                        password = "123456"
                    )
                    userService.insert(
                        user
                    )
                    normal.add(user)
                }
                val developer = userService.findByUsertype(UserTypeEnum.Developer).toMutableList()
                developer.ifEmpty {
                    val user = User(
                        name = "Developer",
                        userName = "Developer",
                        userType = UserTypeEnum.Developer,
                        password = "123456"
                    )
                    userService.insert(
                        user
                    )
                    developer.add(user)
                }

                Log.d("ApplicationBase", "initData: 数据库初始化完毕")
                Log.d("ApplicationBase", "initData: 用户数据：Admin:= ${normal.toJson()}")
                Log.d("ApplicationBase", "initData: 用户数据：Admin:= ${admin.toJson()}")
                Log.d("ApplicationBase", "initData: 用户数据：Admin:= ${developer.toJson()}")

            }
        }
    }
}
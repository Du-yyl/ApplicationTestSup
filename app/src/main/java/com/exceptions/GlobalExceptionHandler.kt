package com.exceptions

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * @time :2024/1/15 17:16 15
 * @className :GlobalExceptionHandler
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class GlobalExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("GlobalExceptionHandler", "uncaughtException: 出现未处理异常!", e)
        Toast.makeText(context, "出现未捕获异常,查看日志", Toast.LENGTH_SHORT).show()
        // 关闭软件
//        exitProcess(0)
    }

    companion object {
        // 在 Application 类的 onCreate 方法中设置全局异常处理器
        fun setupExceptionHandler(context: Context) {
            Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(context))
        }
    }
}
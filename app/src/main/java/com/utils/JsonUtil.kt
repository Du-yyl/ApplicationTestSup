package com.utils

import android.provider.ContactsContract.Data
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.sql.Date

/**
 * @time :2024/1/8 14:10 52
 * @className :JsonUtil
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

// 例子

data class Person(val name: String, val age: Int)

object JsonUtil {

    // 使用委托属性保证 Gson 在第一次访问时被初始化
    val gson: Gson by lazy {
        GsonBuilder().create()
    }

    /**
     * 将对象序列化为 JSON 字符串
     * 如果对象为 null，返回空 JSON 对象 "{}"
     */
    fun <T> toJson(data: T?): String {
        // 使用 Elvis 操作符，如果 data 为 null，返回空 JSON 对象
        return gson.toJson(data ?: Any())
    }

    /**
     * 将 JSON 字符串反序列化为指定类型的对象
     * 如果解析失败，返回 null
     */
    inline fun <reified T> fromJson(json: String): T? {
        return try {
            gson.fromJson(json, T::class.java)
        } catch (e: JsonSyntaxException) {
            Log.e("JsonUtil", "fromJson: Json解析失败", e)
            // 解析失败，返回 null
            null
        }
    }
}

fun main() {

}
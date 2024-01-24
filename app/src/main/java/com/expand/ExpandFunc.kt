package com.expand

import com.utils.JsonUtil


/**
 * @time :2024/1/8 14:02 48
 * @className :ExpandFunc
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

/**
 * 转换为 JSON
 */
fun Any.toJson () = JsonUtil.toJson(this)

/**
 * 从字符串转换为 对象
 */
inline fun <reified T> Any.fromJson(json:String):T?{
    return JsonUtil.fromJson<T>(json)
}

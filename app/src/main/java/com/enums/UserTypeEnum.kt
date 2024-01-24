package com.enums

/**
 * @time :2024/1/15 15:49 35
 * @className :UserTypeEnum
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
enum class UserTypeEnum(val value: Int) {
    /**
     * 普通账户
     */
    Normal(0),

    /**
     * 管理员
     */
    Admin(10),

    /**
     * 开发者
     */
    Developer(99)
}
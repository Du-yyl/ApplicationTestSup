package com.viewModel

/**
 * @time :2024/1/25 14:28 01
 * @className :WellPlateElementApaterViewModel
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */


/**
 * 试验对象单元素适配器
 */
data class AdapterWellPlateElementViewModel(
    val text: String
) {
    // 自动生成 hashCode 方法，用于标识对象
    override fun hashCode(): Int {
        return text.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdapterWellPlateElementViewModel

        if (text != other.text || hashCode() != other.hashCode()) return false

        return true
    }

}
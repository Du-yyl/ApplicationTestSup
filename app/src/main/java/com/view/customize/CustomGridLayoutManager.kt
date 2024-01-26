package com.view.customize

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

/**
 * @time :2024/1/25 16:33 39
 * @className :RecyclerViewCustom
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

/**
 * 自定义 GridLayout 管理器，可关闭滚动
 * @param spanCount 最小为 1
 */
class CustomGridLayoutManager(context: Context, spanCount: Int) :
    GridLayoutManager(context, if (spanCount <= 0) 1 else spanCount) {
    /**
     * 返回可以滚动
     */
    private var isScrollEnabled: Boolean = true

    // 设置是否启用滚动
    fun setScrollEnabled(enabled: Boolean) {
        this.isScrollEnabled = enabled
    }

    override fun canScrollHorizontally(): Boolean {
        // 控制水平滚动
        return isScrollEnabled && super.canScrollHorizontally()
    }

    override fun canScrollVertically(): Boolean {
        // 控制垂直滚动
        return isScrollEnabled && super.canScrollVertically()
    }
}

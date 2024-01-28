package com.view.components

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.view.adapter.WellPlateAdapter
import com.view.customize.CustomGridLayoutManager
import com.viewModel.AdapterWellPlateElementViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @time :2024/1/25 17:07 34
 * @className :GridWellPlateComponent1
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class GridWellPlateComponent1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    /**
     * 多少列
     */
    private var spanCount = 0

    /**
     * 一次最多更新多少视图
     */
    private val dataBatches = 10

    /**
     * 数据列表
     */
    val adapterList = mutableListOf<AdapterWellPlateElementViewModel>()

    /**
     * 适配器
     */
    val wellPlateAdapter = WellPlateAdapter(adapterList)

    init {
        spanCount =
            attrs?.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "spanCount", 1)
                ?: 1

        Log.d("GridWellPlateComponent1", "spanCount: $spanCount")
        // 禁用滚动
        layoutManager = CustomGridLayoutManager(context, 3).apply {
            setScrollEnabled(false)
        }

        // 绑定适配器
        adapter = wellPlateAdapter

    }


    fun updateRowOrColumn(row: Int, column: Int) {
        Log.d(
            "GridWellPlateComponent1",
            "updateRowOrColumn() called with: row = $row, column = $column"
        )
        val adapterList = mutableListOf<AdapterWellPlateElementViewModel>()

        layoutManager = CustomGridLayoutManager(context, column).apply {
            setScrollEnabled(false)
        }

        val count = row * column

        for (i in 0 until count) {
            adapterList.add(AdapterWellPlateElementViewModel(i.toString()))
        }

        wellPlateAdapter.updateData(adapterList)

        Log.d("GridWellPlateComponent1", "updateRowOrColumn: 渲染完毕")
    }
}

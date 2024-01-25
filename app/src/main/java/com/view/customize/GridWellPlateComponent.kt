package com.view.customize

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.room.RewriteQueriesToDropUnusedColumns
import org.w3c.dom.Text

/**
 * @time :2024/1/25 9:54 23
 * @className :GridWellPlateComponent
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class GridWellPlateComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private var rows: Int = 0
    private var columns: Int = 0

    init {
        // 从传递的对象获取数据
        rows = attrs?.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "gridRows", 1)
            ?: 1
        columns =
            attrs?.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "gridColumns", 1)
                ?: 1

        Log.d("GridWellPlateComponent", "获取到数据: rows: $rows, columns:$columns")

        // 为父对象数据赋值
        rowCount = rows
        columnCount = columns

        // 添加子元素
        createElements()
    }

    /**
     * 添加子元素
     */
    private fun createElements() {
        removeAllViews()

        for (i in 0 until rows * columns) {
            val textView = createElement(i.toString())

            val params = LayoutParams().apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                columnSpec = spec(UNDEFINED, 1f)
                rowSpec = spec(UNDEFINED, 1f)
            }

            textView.layoutParams = params
            addView(textView)
        }
    }

    /**
     * 创建单个元素
     */
    fun createElement(string: String): TextView {
        return TextView(context).apply {
            text = string
            layoutParams = LayoutParams().apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                columnSpec = spec(UNDEFINED, 1f)
                rowSpec = spec(UNDEFINED, 1f)
                setMargins(0, 0, 8, 8)
            }
            background = ColorDrawable(Color.BLUE)
        }
    }

    /**
     * 更新行和列数据
     */
    fun updateRowsOrColumns(newRows: Int, newColumns: Int) {
        Log.d(
            "GridWellPlateComponent",
            "updateRowsOrColumns() called with: newRows = $newRows, newColumns = $newColumns"
        )
        if (newRows == 0 || newColumns == 0) {
            removeAllViews()
            rows = newRows
            columns = newColumns
        }

        val diffRows = newRows - rows
        val diffColumns = newColumns - columns

        if (diffRows > 0) {
            for (i in 0 until diffRows * columns) {
                val element = createElement(i.toString())
                addView(element)
            }
        } else if (diffRows < 0) {
            removeViews(0, -diffRows * columns)
        }

        if (diffColumns > 0) {
            for (i in 0 until diffColumns * columns) {
                val element = createElement(i.toString())
                addView(element)
            }
        } else if (diffColumns < 0) {
            removeViews(rows * diffColumns, diffColumns)
        }

        rows = newRows
        columns = newColumns

    }
}
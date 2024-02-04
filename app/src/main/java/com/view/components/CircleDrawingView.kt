package com.view.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.applicationtest.R
import splitties.views.InputType.Companion.text
import kotlin.math.cos
import kotlin.math.sin

/**
 * @time :2024/1/29 9:40 05
 * @className :CircleDrawingView
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

class CircleDrawingView : View {
    private val minCellSize = 50f
    private val cellSpacing = 40f
    private val paint = Paint()

    /**
     * 自定义图形
     */
    private val customShapePath = createCustomShapePath()

    val rect = Rect()

    val padding = 20 // 设置边框与元素之间的间距
    private var selectedCell = -1
    private var numRows = 5
    private var numColumns = 7

    /**
     * 最小文字大小
     */
    private val minTextSize = 220f

    // 计算实际绘制区域，考虑边框的间距
    val actualWidth = width.toFloat() / numColumns - padding * 2
    val actualHeight = height.toFloat() / numRows - padding * 2

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 计算每个形状的尺寸
        val cellWidthSize = calculateWidthCellSize()
        val cellHeightSize = calculateHeightCellSize()
        for (row in 0 until numRows) {
            for (col in 0 until numColumns) {
                // 在这里可以根据需求绘制不同的图形，这里用的是圆形
//                canvas.drawCircle(shapeCenterX, shapeCenterY, cellSize / 2, paint)

                // 绘制正方形
//                val halfSize = cellSize / 2
//                canvas.drawRect(shapeCenterX - halfSize, shapeCenterY - halfSize,
//                    shapeCenterX + halfSize, shapeCenterY + halfSize, paint)

                // 正五边形
//                canvas.save()
//                canvas.translate(shapeCenterX, shapeCenterY)
//                canvas.drawPath(customShapePath, paint)
//                canvas.restore()


                // 引用 xml 进行图形绘制
                val shapeCenterX =
                    col * width.toFloat() / numColumns + (width.toFloat() / numColumns) / 2
                val shapeCenterY =
                    row * height.toFloat() / numRows + (height.toFloat() / numRows) / 2

                // 绘制选定的形状
//                ContextCompat.getDrawable(context, R.drawable.custom_shape)?.let {
//                    it.setBounds(
//                        (shapeCenterX - it.intrinsicWidth / 2).toInt(),
//                        (shapeCenterY - it.intrinsicHeight / 2).toInt(),
//                        (shapeCenterX + it.intrinsicWidth / 2).toInt(),
//                        (shapeCenterY + it.intrinsicHeight / 2).toInt()
//                    )
//                    it.draw(canvas)
//                }

                // 获取矢量图形的实际尺寸
                val vectorDrawable = ContextCompat.getDrawable(context, R.drawable.custom_shape)
                val vectorDrawable1 = ContextCompat.getDrawable(context, R.drawable.custom_shape_1)
                val originalWidth = vectorDrawable?.intrinsicWidth ?: 0
                val originalHeight = vectorDrawable?.intrinsicHeight ?: 0

                // 计算缩放比例
                val scaleX = (width.toFloat() / numColumns) / originalWidth
                val scaleY = (height.toFloat() / numRows) / originalHeight

                // 设置缩放变换
                canvas.save()
                canvas.translate(shapeCenterX, shapeCenterY)
                canvas.scale(scaleX, scaleY)

                //
                val halfWidthSize = cellWidthSize / 2
                val halfHeightSize = cellHeightSize / 2

                // 在每一行和每一列的外边缘绘制行号和列号
                if (col == 0) {
                    // 绘制行号
                    val text = if (row == 0) "" else if (row < 10) " $row" else "$row"
                    paint.textSize = if (cellWidthSize < minTextSize) minTextSize else cellWidthSize
                    canvas.drawText(
                        text,
                        -cellWidthSize,
                        halfHeightSize * 2,
                        paint
                    )
//                    canvas.drawText(text, shapeCenterX - halfSize - 50, shapeCenterY/2, paint)
                } else if (row == 0) {
                    // 绘制列号
                    val text = if (col < 10) " $col" else "$col"
                    paint.textSize =
                        if (halfHeightSize < minTextSize) minTextSize else halfHeightSize
                    Log.d("CircleDrawingView", "onDraw: $halfWidthSize")
                    canvas.drawText(
                        text,
//                        numRows
//                        (-cellWidthSize / 1.2).toFloat(),
                        (-cellWidthSize * 1.2).toFloat(),
//                        (-cellWidthSize * 2.2).toFloat(),
                        halfHeightSize * 2,
                        paint
                    )
//                    canvas.drawText(text, shapeCenterX, shapeCenterY - halfSize - 20, paint)
                } else if (row != 0 && col != 0) {
                    // 绘制选定的形状
                    if (row % 2 == 0) {
                        vectorDrawable?.let {
                            rect.set(
                                -originalWidth / 2, -originalHeight / 2,
                                originalWidth / 2, originalHeight / 2
                            )
                            it.bounds = rect
                            it.draw(canvas)
                        }
                    } else {
                        vectorDrawable1?.let {
                            rect.set(
                                -originalWidth / 2, -originalHeight / 2,
                                originalWidth / 2, originalHeight / 2
                            )
                            it.bounds = rect
                            it.draw(canvas)
                        }
                    }
                }

                // 恢复画布状态
                canvas.restore()
            }
        }
    }

    /**
     * 更新行和列
     */
    fun updateRowOrColumn(row: Int, column: Int) {
        numRows = row
        numColumns = column
        invalidate()
    }

    /**
     * 正五边形
     */
    private fun createCustomShapePath(): Path {
        // 创建自定义形状的Path
        val path = Path()
        val radius = 50f
        val angle = Math.toRadians(360.0 / 5) // 五边形

        path.moveTo(radius, 0f) // 移动到起始点

        for (i in 1 until 5) {
            val x = (radius * cos(angle * i)).toFloat()
            val y = (radius * sin(angle * i)).toFloat()
            path.lineTo(x, y)
        }

        path.close() // 闭合Path

        return path
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 计算点击的形状
                val clickedCol = (event.x / (calculateWidthCellSize() + cellSpacing)).toInt()
                val clickedRow = (event.y / (calculateWidthCellSize() + cellSpacing)).toInt()
                val clickedCell = clickedRow * numColumns + clickedCol

                if (clickedCell >= 0 && clickedCell < numRows * numColumns) {
                    // 更新选定的形状
                    selectedCell = clickedCell
                    invalidate()
                }
            }
        }
        return true
    }

    private fun calculateWidthCellSize(): Float {
        // 计算每个形状的尺寸，使其适应页面宽度
        val availableWidth = width.toFloat()
        val maxColumns = (availableWidth / (minCellSize + cellSpacing)).toInt()
        val columns = if (numColumns < maxColumns) numColumns else maxColumns
        return (availableWidth - (columns - 1) * cellSpacing) / columns
    }


    private fun calculateHeightCellSize(): Float {
        // 计算每个形状的尺寸，使其适应页面高度
        val availableHeight = height.toFloat()
        val maxRows = (availableHeight / (minCellSize + cellSpacing)).toInt()
        val rows = if (numColumns < maxRows) numColumns else maxRows
        return (availableHeight - (rows - 1) * cellSpacing) / rows
    }
}
package com.view.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.PathParser
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import com.example.applicationtest.R
import org.xmlpull.v1.XmlPullParser
import splitties.init.appCtx
import splitties.resources.color
import kotlin.math.cos
import kotlin.math.min
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
    /**
     * 元素最小大小
     */
    private val minCellSize = 50f

    /**
     * 元素间距
     */
    private val cellSpacing = 40f

    /**
     * 主要用于渲染文字
     */
    private val paint = Paint()

    /**
     * 自定义图形
     */
    private val customShapePath = createCustomShapePath()

    /**
     * 空矩形
     */
    private val rect = Rect()

    /**
     * 选中元素的下标
     */
    private var selectedCell = -1

//    6     4
//    12    8
//    24    16

    /**
     * 行数【使用时会包含数字和字母展示列，所以数据会加一，但是获取该数据不会包含数字和字母展示列】
     */
    private var numRows = 9

    /**
     * 列数
     */
    private var numColumns = 13

    /**
     * 最小文字大小
     */
    private val minTextSize: Float = 50 * resources.displayMetrics.density

    // 计算实际绘制区域
    val actualWidth = width.toFloat() / numColumns * 2
    val actualHeight = height.toFloat() / numRows * 2

    /**
     * 选中列表
     */
    val checkedList: MutableLiveData<MutableSet<Int>> = MutableLiveData<MutableSet<Int>>().apply {
        value = mutableSetOf()
    }

    /**
     * 是否显示行号和列号
     */
    val isDisplayRowsAndColumns = true

    // 获取矢量图形的实际尺寸【自定义图形】
    val checked = ContextCompat.getDrawable(context, R.drawable.custom_shape_2)
    val unchecked: Drawable? = ContextCompat.getDrawable(context, R.drawable.custom_shape_1)

    // 绘制路径
    var checkedPath: Path? = Path()
    var uncheckedPath: Drawable? = createCustomDrawable("M50,50 m-45,0a45,45 0,1 1,90 0a45,45 0,1 1,-90 0",Color.RED)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        paint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }


        addCheckedElement(listOf(1, 2, 3, 4, 56, 6, 7, 8))
    }

    fun getRow(): Int {
        return if (isDisplayRowsAndColumns) numRows else numRows - 1
    }

    fun getColumn(): Int {
        return if (isDisplayRowsAndColumns) numColumns else numColumns - 1
    }

    fun addCheckedElement(value: Int) {
        checkedList.value?.add(value)
    }

    fun addCheckedElement(value: Collection<Int>) {
        for (i in value) {
            checkedList.value?.add(i)
        }
    }

    /**
     * 重新绘制
     */
    fun redraw() {
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("CircleDrawingView", "onDraw: 页面更新")
        super.onDraw(canvas)

        // 元素宽高
        val originalWidth = checked?.intrinsicWidth ?: 0
        val originalHeight = checked?.intrinsicHeight ?: 0

        // 计算缩放比例
        val scaleX: Float = ((width.toFloat() / numColumns) / originalWidth / 1.1).toFloat()
        val scaleY: Float = ((height.toFloat() / numRows) / originalHeight / 1.1).toFloat()

        // 计算每个形状的尺寸
        val cellWidthSize = calculateWidthCellSize()
        val cellHeightSize = calculateHeightCellSize()

        // 行标号文字偏移量 X Y【A,B,C,D...】
        val rowDistanceOffsetX: Float = -cellWidthSize / 2
        val rowDistanceOffsetY: Float = (cellHeightSize * .5).toFloat()

        // 列文字偏移量 X Y【1,2,3,4,5...】
        val columnDistanceOffsetX: Float = -cellWidthSize / 2
        val columnDistanceOffsetY: Float = (cellHeightSize * .5).toFloat()

        // 文字大小
        val cellMin = min(cellHeightSize, cellWidthSize)
        paint.textSize = if (cellMin < minTextSize) minTextSize else cellMin

        // 循环列表
        var eleIndex = 0

        // 开始对元素内容进行渲染
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

                // 元素所在位置
                val shapeCenterX =
                    col * width.toFloat() / numColumns + (width.toFloat() / numColumns) / 2
                val shapeCenterY =
                    row * height.toFloat() / numRows + (height.toFloat() / numRows) / 2

                // 设置缩放变换
                canvas.save()
                canvas.translate(shapeCenterX, shapeCenterY)
                canvas.scale(scaleX, scaleY)

                // 绘制行号
                if (isDisplayRowsAndColumns && col == 0) {
                    // 数字转大写字幕
                    val str = (row + 64).toChar()
                    val text = if (row == 0) "" else "$str"
                    canvas.drawText(
                        text,
                        rowDistanceOffsetX,
                        rowDistanceOffsetY,
                        paint
                    )
                }
                // 绘制列号
                else if (isDisplayRowsAndColumns && row == 0) {
                    val text = if (col < 10) " $col" else "$col"
                    canvas.drawText(
                        text,
                        columnDistanceOffsetX,
                        columnDistanceOffsetY,
                        paint
                    )
                }
                // 非行首和列头位置绘制填充元素
                else {
                    eleIndex++
                    if (unchecked == null || checked == null) {
                        Log.d("CircleDrawingView", "onDraw: 渲染对象：$unchecked")
                        canvas.drawText(
                            "图形为空，请联系管理员",
                            columnDistanceOffsetX,
                            columnDistanceOffsetY,
                            paint
                        )
                        canvas.restore()

                        break
                    }
                    // 是否选中
                    if (checkedList.value?.contains(eleIndex) == true) {

                        checked.let {
                            rect.set(
                                -originalWidth / 2, -originalHeight / 2,
                                originalWidth / 2, originalHeight / 2
                            )
                            it.bounds = rect
                            it.draw(canvas)
                        }
                    } else {
                        uncheckedPath?.let {
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
    fun updateRowOrColumn(row: Int = numRows, column: Int = numColumns) {
        Log.d("CircleDrawingView", "updateRowOrColumn() called with: row = $row, column = $column")
        var _row = row
        var _column = column

        if (_row < 1 || _column < 1) {
            _row = 0
            _column = 0
            // 重置选中元素对象
            checkedList.value = mutableSetOf()
        }
        // 真实行和列（根据行号和列号是否展示决定设定内容）
        val authenticX = if (isDisplayRowsAndColumns) _row + 1 else _row
        val authenticY = if (isDisplayRowsAndColumns) _column + 1 else _column


        if (authenticX == numRows || authenticY == numColumns) return
        numRows = authenticX
        numColumns = authenticY
        invalidate()
    }


    /**
     * 创建 Drawable 形状
     */
    fun createCustomDrawable(pathData: String, color: Int): Drawable {
        val path = PathParser.createPathFromPathData(pathData)
        val shape = PathShape(path, 100f, 100f) // 这里的 100f 是任意宽高，因为我们会自行缩放和平移
        val shapeDrawable = ShapeDrawable(shape)
        shapeDrawable.paint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 5f
            this.color = color
        }
        return shapeDrawable
    }

    /**
     * 正五边形【自定义图形】
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

    /**
     * 触摸事件
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("CircleDrawingView", "onTouchEvent: 测试")
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
    }

    /**
     * 计算元素宽度大小
     */
    private fun calculateWidthCellSize(): Float {
        // 计算每个形状的尺寸，使其适应页面宽度
        val availableWidth = width.toFloat()
        val maxColumns = (availableWidth / (minCellSize + cellSpacing)).toInt()
        val columns = if (numColumns < maxColumns) numColumns else maxColumns
        return (availableWidth - (columns - 1) * cellSpacing) / columns
    }

    /**
     * 计算元素高度大小
     */
    private fun calculateHeightCellSize(): Float {
        // 计算每个形状的尺寸，使其适应页面高度
        val availableHeight = height.toFloat()
        val maxRows = (availableHeight / (minCellSize + cellSpacing)).toInt()
        val rows = if (numColumns < maxRows) numColumns else maxRows
        return (availableHeight - (rows - 1) * cellSpacing) / rows
    }
}